package cn.dtransfer.system.controller;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.Notice;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.INoticeService;
import cn.dtransfer.system.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 通知公告 提供者
 *
 */
@RestController
@RequestMapping("system/notice")
public class NoticeController extends BaseController {

    @Autowired
    private INoticeService noticeService;

    /**
     * 查询通知公告
     */
    @RequiresPermissions("system:notice:query")
    @GetMapping("get/{noticeId}")
    public Notice get(@PathVariable("noticeId") Long noticeId) {
        return noticeService.selectNoticeById(noticeId);

    }

    /**
     * 查询通知公告列表
     */
    @RequiresPermissions("system:notice:list")
    @GetMapping("list")
    public R list(Notice notice) {
        startPage();
        return result(noticeService.selectNoticeList(notice));
    }


    /**
     * 新增保存通知公告
     */
    @RequiresPermissions("system:notice:add")
    @OperLog(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("save")
    public R addSave(@RequestBody Notice notice) {
        notice.setCreateBy(getLoginName());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改保存通知公告
     */
    @RequiresPermissions("system:notice:edit")
    @OperLog(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody Notice notice) {
        notice.setUpdateBy(getLoginName());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @RequiresPermissions("system:notice:remove")
    @OperLog(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }

}
