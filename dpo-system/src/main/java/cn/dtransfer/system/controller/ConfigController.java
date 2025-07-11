package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.Config;
import cn.dtransfer.system.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 参数配置 提供者
 *
 */
@RestController
@RequestMapping("system/config")
public class ConfigController extends BaseController {

    @Autowired
    private IConfigService configService;

    /**
     * 查询参数配置
     */
    @GetMapping("get/{configId}")
    public Config get(@PathVariable("configId") Long configId) {
        return configService.selectConfigById(configId);

    }

    /**
     * 查询参数配置列表
     */
    @RequiresPermissions("system:config:list")
    @GetMapping("list")
    public R list(Config config) {
        startPage();
        return result(configService.selectConfigList(config));
    }


    /**
     * 新增保存参数配置
     */
    @RequiresPermissions("system:config:add")
    @PostMapping("save")
    public R addSave(@RequestBody Config config) {
        config.setCreateBy(getLoginName());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改保存参数配置
     */
    @RequiresPermissions("system:config:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Config config) {
        config.setUpdateBy(getLoginName());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @RequiresPermissions("system:config:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(configService.deleteConfigByIds(ids));
    }

}
