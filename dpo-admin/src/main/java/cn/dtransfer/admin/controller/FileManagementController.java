package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IFileManagementService;
import cn.dtransfer.admin.domain.FileManagement;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 文件管理 提供者
 *
 * @author dtransfer
 * @date 2024-05-24
 */
@RestController
@RequestMapping("/admin/fileManagement")
public class FileManagementController extends BaseController {

    @Autowired
    private IFileManagementService fileManagementService;

    /**
     * 查询文件管理
     */
    @GetMapping("get/{id}")
    public FileManagement get(@PathVariable("id") Long id) {
        return fileManagementService.selectFileManagementById(id);
    }

    /**
     * 查询文件管理列表
     */
    @RequiresPermissions("admin:fileManagement:list")
    @GetMapping("list")
    public R list(FileManagement fileManagement) {
        startPage();
        return result(fileManagementService.selectFileManagementList(fileManagement));
    }


    /**
     * 新增保存文件管理
     */
    @RequiresPermissions("admin:fileManagement:add")
    @PostMapping("save")
    public R addSave(@RequestBody FileManagement fileManagement) {
        return toAjax(fileManagementService.insertFileManagement(fileManagement));
    }

    /**
     * 修改保存文件管理
     */
    @RequiresPermissions("admin:fileManagement:edit")
    @PostMapping("update")
    public R editSave(@RequestBody FileManagement fileManagement) {
        return toAjax(fileManagementService.updateFileManagement(fileManagement));
    }

    /**
     * 删除文件管理
     */
    @RequiresPermissions("admin:fileManagement:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(fileManagementService.deleteFileManagementByIds(ids));
    }

}
