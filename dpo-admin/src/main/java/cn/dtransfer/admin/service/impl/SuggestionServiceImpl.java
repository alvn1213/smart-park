package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ISuggestionService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Suggestion;
import cn.dtransfer.admin.mapper.SuggestionMapper;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 投诉建议Service业务层处理
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Service
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, Suggestion> implements ISuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;


    /**
     * 查询投诉建议
     *
     * @param id 投诉建议ID
     * @return 投诉建议
     */
    @Override
    public Suggestion selectSuggestionById(Long id) {
        Suggestion suggestion = suggestionMapper.selectSuggestionById(id);
        return suggestion;
    }

    /**
     * 查询投诉建议列表
     *
     * @param suggestion 投诉建议
     * @return 投诉建议
     */
    @Override
    public List<Suggestion> selectSuggestionList(Suggestion suggestion) {
        return suggestionMapper.selectSuggestionList(suggestion);
    }

    /**
     * 新增投诉建议
     *
     * @param suggestion 投诉建议
     * @return 结果
     */
    @Override
    public int insertSuggestion(Suggestion suggestion) {
        suggestion.setStatus(1);
        suggestion.setSn(RandomUtil.generate_sn(Constants.COMPLAINTS));
        suggestion.setIsAnonymous(false);
        return suggestionMapper.insert(suggestion);
    }

    /**
     * 修改投诉建议
     *
     * @param suggestion 投诉建议
     * @return 结果
     */
    @Override
    public int updateSuggestion(Suggestion suggestion) {
        return suggestionMapper.updateById(suggestion);
    }

    /**
     * 删除投诉建议对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSuggestionByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return suggestionMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除投诉建议信息
     *
     * @param id 投诉建议ID
     * @return 结果
     */
    @Override
    public int deleteSuggestionById(Long id) {
        return suggestionMapper.deleteById(id);
    }
}
