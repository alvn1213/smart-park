package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Suggestion;

import java.util.List;

/**
 * 投诉建议Service接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
public interface ISuggestionService extends IService<Suggestion> {
    /**
     * 查询投诉建议
     *
     * @param id 投诉建议ID
     * @return 投诉建议
     */
    Suggestion selectSuggestionById(Long id);

    /**
     * 查询投诉建议列表
     *
     * @param suggestion 投诉建议
     * @return 投诉建议集合
     */
    List<Suggestion> selectSuggestionList(Suggestion suggestion);

    /**
     * 新增投诉建议
     *
     * @param suggestion 投诉建议
     * @return 结果
     */
    int insertSuggestion(Suggestion suggestion);

    /**
     * 修改投诉建议
     *
     * @param suggestion 投诉建议
     * @return 结果
     */
    int updateSuggestion(Suggestion suggestion);

    /**
     * 批量删除投诉建议
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSuggestionByIds(String ids);

    /**
     * 删除投诉建议信息
     *
     * @param id 投诉建议ID
     * @return 结果
     */
    int deleteSuggestionById(Long id);
}
