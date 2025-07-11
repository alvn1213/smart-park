package cn.dtransfer.controller.mobile.member;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.admin.vo.ParkVO;
import cn.dtransfer.admin.domain.Repair;
import cn.dtransfer.admin.domain.RepairLog;
import cn.dtransfer.admin.vo.RepairVO;
import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.common.utils.bean.BeanUtils;
import cn.dtransfer.service.IRepairLogService;
import cn.dtransfer.service.IRepairService;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.domain.vo.UserVO;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 工单管理
 *
 * @author dtransfer
 */
@RestController
@RequestMapping("/repair")
public class RepairAPIController extends BaseController {


    @Autowired
    private IRepairService repairService;
    @Autowired
    private IRepairLogService repairLogService;
    @Autowired
    private IParkService parkService;

    /**
     * 查询工单管理
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("get/{id}")
    public R get(@PathVariable("id") Long id) {
        RepairVO repairVO = new RepairVO();
        Repair repair = repairService.selectRepairById(id);
        if (repair != null) {
            BeanUtils.copyBeanProp(repairVO, repair);
            repairVO.setStatus(repair.getStatus().getValue());
            repairVO.setStatusName(repair.getStatus().getName());

            ParkVO parkVO = new ParkVO();
            if(StringUtils.isNotNull(repair.getPark())) {
                BeanUtils.copyBeanProp(parkVO, repair.getPark());
                repairVO.setParkVO(parkVO);
            }

            UserVO userVO = new UserVO();
            if(StringUtils.isNotNull(repair.getUser())) {
                BeanUtils.copyBeanProp(userVO, repair.getUser());
                repairVO.setUserVO(userVO);
            }

            RepairLog repairLog = new RepairLog();
            repairLog.setRepairId(id);
            List<RepairLog> repairLogs = repairLogService.selectRepairLogList(repairLog);
            repairVO.setRepairLogs(repairLogs);
        }
        return R.data(repairVO);
    }

    /**
     * 查询工单管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Repair.Status status, @LoginUser User user, Long parkId) {
        Repair repair = new Repair();
        repair.setStatus(status);
        repair.setCreateBy(user.getUsername());
        repair.setParkId(parkId);
        startPage();
        List<Repair> repairList = repairService.selectMyRepairList(repair);
        List<Map<String, Object>> repairVOList = Lists.newArrayList();
        repairList.stream().forEach(item ->{
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", item.getId());
            map.put("sn", item.getSn());
            map.put("repairTime", item.getRepairTime());
            map.put("area", item.getArea());
            map.put("content", item.getContent());
            map.put("status",item.getStatus());
            repairVOList.add(map);
        });
        return result(repairVOList);
    }


    /**
     * 添加
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("/add")
    public R add(@LoginUser User user) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", user.getId());
        map.put("mobile", user.getMobile());
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickname());
        map.put("area", "");
        map.put("content", "");
        return R.data(map);
    }

    /**
     * 新增保存工单管理
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("save")
    public R addSave(@RequestBody Repair repair, @LoginUser User user, Long parkId) {
        if (parkId == null) {
            return R.error("请选择园区！!");
        }
        Park park = parkService.selectParkById(parkId);
        ValidatorUtils.validateEntity(repair);
        repair.setParkId(parkId);
        repair.setStatus(Repair.Status.PENDING_ASSIGN);
        repair.setRepairTime(DateUtils.getNowDate());
        repair.setCreateBy(user.getUsername());
        if(ObjectUtil.isNotEmpty(park)) {
            repair.setTenantId(park.getTenantId());
        }
        return toAjax(repairService.insertRepair(repair));
    }


    /**
     * 评价工单
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("review")
    public R review(@RequestBody Repair repair) {
        Repair pRepair = repairService.selectRepairById(repair.getId());
        if (repair == null || !Repair.Status.COMPLETED.equals(pRepair.getStatus())) {
            return R.error("报修为空或状态已完成");
        }
        repair.setStatus(Repair.Status.SCORE);
        repair.setUpdateBy(getLoginName());
        return toAjax(repairService.updateRepair(repair));
    }


    /**
     * 评价工单
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("cancel")
    public R cancel(Long repairId) {
        Repair repair = repairService.selectRepairById(repairId);
        if (repair == null || !Repair.Status.PENDING_PROCESS.equals(repair.getStatus())) {
            return R.error("报修为空或状态不是待处理");
        }
        repair.setStatus(Repair.Status.CANCELED);
        repair.setUpdateBy(getLoginName());
        return toAjax(repairService.updateRepair(repair));
    }

}
