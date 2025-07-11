package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IPolicyService;
import cn.dtransfer.admin.domain.Policy;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;

/**
 * 政策管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/policy")
public class PolicyController extends BaseController {

    @Autowired
    private IPolicyService policyService;

    /**
     * 查询政策管理
     */
    @RequiresPermissions("admin:policy:edit")
    @GetMapping("get/{id}")
    public Policy get(@PathVariable("id") Long id) {
        return policyService.selectPolicyById(id);
    }

    /**
     * 查询政策管理列表
     */
    @RequiresPermissions("admin:policy:list")
    @GetMapping("list")
    public R list(Policy policy) {
        startPage();
        return result(policyService.selectPolicyList(policy));
    }

    /**
     * 新增保存政策管理
     */
    @RequiresPermissions("admin:policy:add")
    @PostMapping("save")
    public R addSave(@RequestBody Policy policy) {
        ValidatorUtils.validateEntity(policy);
        return toAjax(policyService.insertPolicy(policy));
    }

    /**
     * 修改保存政策管理
     */
    @RequiresPermissions("admin:policy:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Policy policy) {
        return toAjax(policyService.updatePolicy(policy));
    }

    /**
     * 删除政策管理
     */
    @RequiresPermissions("admin:policy:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(policyService.deletePolicyByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:policy:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody Policy policy) {
        Policy newPolicy = policyService.selectPolicyById(policy.getId());
        if (newPolicy == null) {
            return R.error("政策不存在!");
        }
        policy.setMarketableTime(new Date());
        return toAjax(policyService.updatePolicy(policy));
    }

}
