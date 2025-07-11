package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.DictType;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IDictTypeService;
import cn.dtransfer.system.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 字典类型 提供者
 *
 */
@RestController
@RequestMapping("system/dict/type")
public class DictTypeController extends BaseController {

    @Autowired
    private IDictTypeService dictTypeService;

    /**
     * 查询字典类型
     */
    @GetMapping("get/{dictId}")
    public DictType get(@PathVariable("dictId") Long dictId) {
        return dictTypeService.selectDictTypeById(dictId);
    }

    /**
     * 查询字典类型列表
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("list")
    public R list(DictType dictType) {
        startPage();
        return result(dictTypeService.selectDictTypeList(dictType));
    }


    /**
     * 新增保存字典类型
     */
    @RequiresPermissions("system:dict:add")
    @OperLog(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R addSave(@RequestBody DictType dictType) {
        dictType.setCreateBy(getLoginName());
        return toAjax(dictTypeService.insertDictType(dictType));
    }

    /**
     * 修改保存字典类型
     */
    @RequiresPermissions("system:dict:edit")
    @OperLog(title = "字典类型", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody DictType dictType) {
        dictType.setUpdateBy(getLoginName());
        return toAjax(dictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     *
     * @throws Exception
     */
    @RequiresPermissions("system:dict:remove")
    @OperLog(title = "字典类型", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        return toAjax(dictTypeService.deleteDictTypeByIds(ids));
    }

}
