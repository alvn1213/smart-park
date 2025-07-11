package cn.dtransfer.controller.mobile;

import cn.dtransfer.admin.domain.ApplyPark;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.service.IApplyParkService;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 入园申请 提供者
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/applyPark")
public class ApplyParkAPIController extends BaseController {

    @Autowired
    private IApplyParkService applyParkService;

    @Autowired
    private IParkService parkService;


    @RequiresPermissions("member:center:view")
    @GetMapping("get/{id}")
    public R get(@PathVariable("id") Long id) {
        return R.data(applyParkService.selectApplyParkById(id));
    }

    /**
     * 查询入园申请
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("getByCurrentUser")
    public R getByCurrentUser() {
        return R.data(applyParkService.selectApplyParkByCurrentUser(getCurrentUserId()));
    }


    /**
     * 新增保存入园申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("save")
    public R addSave(@RequestBody ApplyPark applyPark) {
        ValidatorUtils.validateEntity(applyPark);
        if (applyPark.getParkId() == null) {
            return R.error("请选择园区!");
        }
        applyPark.setStatus(ApplySettle.Status.APPROVING);
        applyPark.setCreateUserId(getCurrentUserId());
        applyPark.setTenantId(parkService.selectParkById(applyPark.getParkId()).getTenantId());
        return toAjax(applyParkService.insertApplyPark(applyPark));
    }

    /**
     * 修改保存入园申请
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("update")
    public R editSave(@RequestBody ApplyPark applyPark) {
        return toAjax(applyParkService.updateApplyPark(applyPark));
    }



}
