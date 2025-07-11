package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IPolicyBannerService;
import cn.dtransfer.admin.domain.PolicyBanner;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;

/**
 * 政策banner 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/policyBanner")
public class PolicyBannerController extends BaseController {

    @Autowired
    private IPolicyBannerService policyBannerService;

    /**
     * 查询政策banner
     */
    @RequiresPermissions("admin:policyBanner:edit")
    @GetMapping("get/{id}")
    public PolicyBanner get(@PathVariable("id") Long id) {
        return policyBannerService.selectPolicyBannerById(id);
    }

    /**
     * 查询政策banner列表
     */
    @RequiresPermissions("admin:policyBanner:list")
    @GetMapping("list")
    public R list(PolicyBanner policyBanner) {
        startPage();
        return result(policyBannerService.selectPolicyBannerList(policyBanner));
    }

    /**
     * 新增保存政策banner
     */
    @RequiresPermissions("admin:policyBanner:add")
    @PostMapping("save")
    public R addSave(@RequestBody PolicyBanner policyBanner) {
        ValidatorUtils.validateEntity(policyBanner);
        return toAjax(policyBannerService.insertPolicyBanner(policyBanner));
    }

    /**
     * 修改保存政策banner
     */
    @RequiresPermissions("admin:policyBanner:edit")
    @PostMapping("update")
    public R editSave(@RequestBody PolicyBanner policyBanner) {
        return toAjax(policyBannerService.updatePolicyBanner(policyBanner));
    }

    /**
     * 删除政策banner
     */
    @RequiresPermissions("admin:policyBanner:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(policyBannerService.deletePolicyBannerByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:policyBanner:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody PolicyBanner policyBanner) {
        PolicyBanner newPolicyBanner = policyBannerService.selectPolicyBannerById(policyBanner.getId());
        if (newPolicyBanner == null) {
            return R.error("政策banner不存在!");
        }
        policyBanner.setMarketableTime(new Date());
        return toAjax(policyBannerService.updatePolicyBanner(policyBanner));
    }

}
