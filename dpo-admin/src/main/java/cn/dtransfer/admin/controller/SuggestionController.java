package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.ISuggestionService;
import cn.dtransfer.admin.domain.Suggestion;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 投诉建议 提供者
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/admin/suggestion")
public class SuggestionController extends BaseController {

    @Autowired
    private ISuggestionService suggestionService;

    /**
     * 查询投诉建议
     */
    @RequiresPermissions("admin:suggestion:edit")
    @GetMapping("get/{id}")
    public Suggestion get(@PathVariable("id") Long id) {
        return suggestionService.selectSuggestionById(id);
    }

    /**
     * 查询投诉建议列表
     */
    @RequiresPermissions("admin:suggestion:list")
    @GetMapping("list")
    public R list(Suggestion suggestion) {
        startPage();
        return result(suggestionService.selectSuggestionList(suggestion));
    }

    /**
     * 分派对接人
     */
    @RequiresPermissions("admin:suggestion:edit")
    @PostMapping("/review")
    public R review(@RequestBody Suggestion suggestion) {
        Suggestion newSuggestion = suggestionService.selectSuggestionById(suggestion.getId());
        if (newSuggestion == null) {
            return R.error("分派失败");
        }
        suggestion.setStatus(2);
        suggestion.setProcessingTime(DateUtils.getNowDate());
        return toAjax(suggestionService.updateSuggestion(suggestion));
    }

    /**
     * 完成
     */
    @RequiresPermissions("admin:suggestion:edit")
    @PostMapping("/complete")
    public R complete(@RequestBody Suggestion suggestion) {
        Suggestion newSuggestion = suggestionService.selectSuggestionById(suggestion.getId());
        if (newSuggestion == null) {
            return R.error("信息不存在");
        }
        suggestion.setStatus(3);
        return toAjax(suggestionService.updateSuggestion(suggestion));
    }

    /**
     * 新增保存投诉建议
     */
    @RequiresPermissions("admin:suggestion:add")
    @PostMapping("save")
    public R addSave(@RequestBody Suggestion suggestion) {
        return toAjax(suggestionService.insertSuggestion(suggestion));
    }

    /**
     * 修改保存投诉建议
     */
    @RequiresPermissions("admin:suggestion:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Suggestion suggestion) {
        return toAjax(suggestionService.updateSuggestion(suggestion));
    }

    /**
     * 删除投诉建议
     */
    @RequiresPermissions("admin:suggestion:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(suggestionService.deleteSuggestionByIds(ids));
    }

}
