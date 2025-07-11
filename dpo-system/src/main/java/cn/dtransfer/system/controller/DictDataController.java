package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.DictData;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.IDictDataService;
import cn.dtransfer.system.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 字典数据 提供者
 *
 */
@RestController
@RequestMapping("system/dict/data")
public class DictDataController extends BaseController {

    @Autowired
    private IDictDataService dictDataService;

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    @RequiresPermissions(value = {"system:dict:type", "system:user:list"}, logical = Logical.OR)
    @GetMapping("type")
    public List<DictData> getType(String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @GetMapping("label")
    public String getLabel(String dictType, String dictValue) {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }


    /**
     * 查询字典数据列表
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("list")
    public R list(DictData dictData) {
        startPage();
        return result(dictDataService.selectDictDataList(dictData));
    }

    /**
     * 新增保存字典数据
     */
    @RequiresPermissions("system:dict:add")
    @OperLog(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R addSave(@RequestBody DictData dictData) {
        dictData.setCreateBy(getLoginName());
        return toAjax(dictDataService.insertDictData(dictData));
    }

    /**
     * 修改保存字典数据
     */
    @RequiresPermissions("system:dict:edit")
    @OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody DictData dictData) {
        dictData.setUpdateBy(getLoginName());
        return toAjax(dictDataService.updateDictData(dictData));
    }

    /**
     * 删除字典数据
     */
    @RequiresPermissions("system:dict:remove")
    @OperLog(title = "字典数据", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }

}
