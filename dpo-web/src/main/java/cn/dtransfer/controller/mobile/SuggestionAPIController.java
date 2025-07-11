package cn.dtransfer.controller.mobile;

import cn.dtransfer.admin.domain.Suggestion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.admin.service.ISuggestionService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 投诉建议 提供者
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/suggestion")
public class SuggestionAPIController extends BaseController {

    @Autowired
    private ISuggestionService suggestionService;

    @Autowired
    private IParkService parkService;

    /**
     * 查询投诉建议列表(详情)
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Suggestion suggestion) {
        if (suggestion.getParkId() == null) {
            return R.error("请选择园区！!");
        }
        suggestion.setCreateUserId(getCurrentUserId());
        List<Suggestion> suggestionList = suggestionService.selectSuggestionList(suggestion);
        List<Map> maps = Lists.newArrayList();
        for (Suggestion item : suggestionList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", item.getId());
            map.put("sn", item.getSn());
            map.put("status", item.getStatus());
            map.put("createTime", item.getCreateTime());
            map.put("content", item.getContent());
            map.put("images", item.getImages());
            maps.add(map);
        }
        return R.data(maps);
    }

    /**
     * 新增保存投诉建议
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("save")
    public R addSave(@RequestBody Suggestion suggestion) {
        if (suggestion.getParkId() == null) {
            return R.error("请选择园区!");
        }
        suggestion.setCreateUserId(getCurrentUserId());
        suggestion.setTenantId(parkService.selectParkById(suggestion.getParkId()).getTenantId());
        return toAjax(suggestionService.insertSuggestion(suggestion));
    }


}
