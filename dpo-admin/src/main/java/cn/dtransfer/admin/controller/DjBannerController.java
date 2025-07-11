package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IDjBannerService;
import cn.dtransfer.admin.domain.DjBanner;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;

/**
 * 党建banner 提供者
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@RestController
@RequestMapping("/admin/djBanner")
public class DjBannerController extends BaseController {

    @Autowired
    private IDjBannerService djBannerService;

    /**
     * 查询党建banner
     */
    @RequiresPermissions("admin:djBanner:edit")
    @GetMapping("get/{id}")
    public DjBanner get(@PathVariable("id") Long id) {
        return djBannerService.selectDjBannerById(id);
    }

    /**
     * 查询党建banner列表
     */
    @RequiresPermissions("admin:djBanner:list")
    @GetMapping("list")
    public R list(DjBanner djBanner) {
        startPage();
        return result(djBannerService.selectDjBannerList(djBanner));
    }

    /**
     * 新增保存党建banner
     */
    @RequiresPermissions("admin:djBanner:add")
    @PostMapping("save")
    public R addSave(@RequestBody DjBanner djBanner) {
        ValidatorUtils.validateEntity(djBanner);
        return toAjax(djBannerService.insertDjBanner(djBanner));
    }

    /**
     * 修改保存党建banner
     */
    @RequiresPermissions("admin:djBanner:edit")
    @PostMapping("update")
    public R editSave(@RequestBody DjBanner djBanner) {
        return toAjax(djBannerService.updateDjBanner(djBanner));
    }

    /**
     * 删除党建banner
     */
    @RequiresPermissions("admin:djBanner:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(djBannerService.deleteDjBannerByIds(ids));
    }

    /**
     * 上架、下架
     */
    @RequiresPermissions("admin:djBanner:edit")
    @PostMapping("changeMarketable")
    public R changeMarketable(@RequestBody DjBanner djBanner) {
        DjBanner newDjBanner = djBannerService.selectDjBannerById(djBanner.getId());
        if (newDjBanner == null) {
            return R.error("党建banner不存在!");
        }
        djBanner.setMarketableTime(new Date());
        return toAjax(djBannerService.updateDjBanner(djBanner));
    }

}
