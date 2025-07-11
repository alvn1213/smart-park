package cn.dtransfer.generator.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.generator.domain.GenTable;
import cn.dtransfer.generator.domain.GenTableColumn;
import cn.dtransfer.generator.service.IGenTableColumnService;
import cn.dtransfer.generator.service.IGenTableService;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 */
@RestController
@RequestMapping("gen")
public class GenController extends BaseController {

    @Autowired
    private IGenTableService       genTableService;
    @Autowired
    private IGenTableColumnService genTableColumnService;

    /**
     * 修改代码生成业务
     */
    @GetMapping("/get/{tableId}")
    public R get(@PathVariable("tableId") Long tableId) {
        GenTable table = genTableService.selectGenTableById(tableId);
        return R.data(table);
    }

    /**
     * 导入表结构（保存）
     */
    @RequiresPermissions("tool:gen:list")
    @OperLog(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public R importTableSave(String tables) {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        String operName = getLoginName();
        genTableService.importGenTable(tableList, operName);
        return R.ok();
    }

    /**
     * 查询代码生成列表
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/list")
    public R genList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return result(list);
    }

    /**
     * 查询数据库列表
     */
    @RequiresPermissions("tool:gen:list")
    @GetMapping("/db/list")
    public R dataList(GenTable genTable) {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return result(list);
    }

    /**
     * 查询数据表字段列表
     */
    @RequiresPermissions("tool:gen:edit")
    @GetMapping("edit")
    public R edit(GenTableColumn genTableColumn) {
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(genTableColumn);
        GenTable table = genTableService.selectGenTableById(genTableColumn.getTableId());
        return R.data(table).put("rows", list).put("total", list.size());
    }

    /**
     * 修改保存代码生成业务
     */
    @RequiresPermissions("tool:gen:edit")
    @OperLog(title = "代码更新", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public R editSave(@RequestBody @Validated GenTable genTable) {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return R.ok();
    }

    /**
     * 删除代码
     */
    @RequiresPermissions("tool:gen:remove")
    @OperLog(title = "删除代码", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public R remove(String ids) {
        genTableService.deleteGenTableByIds(ids);
        return R.ok();
    }

    /**
     * 预览代码
     */
    @RequiresPermissions("tool:gen:preview")
    @GetMapping("/preview/{tableId}")
    public R preview(@PathVariable("tableId") Long tableId) {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return R.data(dataMap);
    }

    /**
     * 生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @OperLog(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.generatorCode(tableName);
        genCode(response, data);
    }

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @OperLog(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.generatorCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=his.zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

}
