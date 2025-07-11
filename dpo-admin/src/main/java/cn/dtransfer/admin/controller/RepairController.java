package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IRepairLogService;
import cn.dtransfer.admin.service.IRepairService;
import cn.dtransfer.admin.domain.Repair;
import cn.dtransfer.admin.domain.RepairLog;
import cn.dtransfer.admin.vo.ParkVO;
import cn.dtransfer.admin.vo.RepairVO;
import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.bean.BeanUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.domain.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 工单管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@RestController
@RequestMapping("/admin/repair")
public class RepairController extends BaseController {

    @Autowired
    private IRepairService repairService;

    @Autowired
    private IRepairLogService repairLogService;

    /**
     * 查询工单管理
     */
    @RequiresPermissions("admin:repair:edit")
    @GetMapping("get/{id}")
    public RepairVO get(@PathVariable("id") Long id) {
        RepairVO repairVO = new RepairVO();
        Repair repair = repairService.selectRepairById(id);
        if (repair != null) {
            BeanUtils.copyBeanProp(repairVO, repair);
            repairVO.setStatus(repair.getStatus().getValue());
            repairVO.setStatusName(repair.getStatus().getName());

            ParkVO parkVO = new ParkVO();
            BeanUtils.copyBeanProp(parkVO, repair.getPark());
            repairVO.setParkVO(parkVO);

            UserVO userVO = new UserVO();
            BeanUtils.copyBeanProp(userVO, repair.getUser());
            repairVO.setUserVO(userVO);

            RepairLog repairLog = new RepairLog();
            repairLog.setRepairId(id);
            List<RepairLog> repairLogs = repairLogService.selectRepairLogList(repairLog);
            repairVO.setRepairLogs(repairLogs);
        }
        return repairVO;
    }

    /**
     * 查询工单管理列表
     */
    @RequiresPermissions("admin:repair:list")
    @GetMapping("list")
    public R list(Repair repair, @LoginUser User user) {
        startPage();
        return result(repairService.selectRepairList(repair));
    }

    /**
     * 审核
     */
    @RequiresPermissions("admin:repair:edit")
    @PostMapping("/review")
    public R review(@RequestBody Repair repair) {
        Repair pRepair = repairService.selectRepairById(repair.getId());
        if (repair == null || !Repair.Status.PENDING_ASSIGN.equals(pRepair.getStatus())) {
            return R.error("报修为空或状态不是待分配");
        }
        repair.setStatus(Repair.Status.PENDING_PROCESS);
        return toAjax(repairService.updateRepair(repair));
    }

    /**
     * 完成
     */
    @RequiresPermissions("admin:repair:edit")
    @PostMapping("/complete")
    public R complete(@RequestBody Repair repair) {
        Repair pRepair = repairService.selectRepairById(repair.getId());
        if (repair == null || !Repair.Status.PENDING_PROCESS.equals(pRepair.getStatus())) {
            return R.error("报修为空或状态不是待处理");
        }
        repair.setStatus(Repair.Status.COMPLETED);
        return toAjax(repairService.updateRepair(repair));
    }

    /**
     * 删除工单管理
     */
    @RequiresPermissions("admin:repair:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(repairService.deleteRepairByIds(ids));
    }

}
