package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IServiceBannerService;
import cn.dtransfer.admin.domain.ServiceBanner;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 企业服务-banner管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@RestController
@RequestMapping("/admin/sBanner")
public class ServiceBannerController extends BaseController {

    @Autowired
    private IServiceBannerService serviceBannerService;

    /**
     * 查询企业服务-banner管理
     */
    @RequiresPermissions("admin:ServiceBanner:edit")
    @GetMapping("get/{id}")
    public ServiceBanner get(@PathVariable("id") Long id) {
        return serviceBannerService.selectServiceBannerById(id);
    }

    /**
     * 查询企业服务-banner管理列表
     */
    @RequiresPermissions("admin:ServiceBanner:list")
    @GetMapping("list")
    public R list(ServiceBanner serviceBanner) {
        startPage();
        return result(serviceBannerService.selectServiceBannerList(serviceBanner));
    }


    /**
     * 新增保存企业服务-banner管理
     */
    @RequiresPermissions("admin:ServiceBanner:add")
    @PostMapping("save")
    public R addSave(@RequestBody ServiceBanner serviceBanner) {
        ValidatorUtils.validateEntity(serviceBanner);
        return toAjax(serviceBannerService.insertServiceBanner(serviceBanner));
    }

    /**
     * 修改保存企业服务-banner管理
     */
    @RequiresPermissions("admin:ServiceBanner:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ServiceBanner serviceBanner) {
        return toAjax(serviceBannerService.updateServiceBanner(serviceBanner));
    }

    /**
     * 删除企业服务-banner管理
     */
    @RequiresPermissions("admin:ServiceBanner:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(serviceBannerService.deleteServiceBannerByIds(ids));
    }


    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:ServiceBanner:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody ServiceBanner serviceBanner) {
        ServiceBanner newServiceBanner = serviceBannerService.selectServiceBannerById(serviceBanner.getId());
        if (newServiceBanner == null) {
            return R.error("banner不存在!");
        }
        serviceBanner.setMarketableTime(DateUtils.getNowDate());
        return toAjax(serviceBannerService.updateServiceBanner(serviceBanner));
    }

    /**
     * 置顶，取消置顶
     */
    @RequiresPermissions("admin:ServiceBanner:edit")
    @PostMapping("changeTop")
    public R changeTop(@RequestBody ServiceBanner serviceBanner) {
        ServiceBanner newServiceBanner = serviceBannerService.selectServiceBannerById(serviceBanner.getId());
        if (newServiceBanner == null) {
            return R.error("banner不存在!");
        }
        return toAjax(serviceBannerService.updateServiceBanner(serviceBanner));
    }

}
