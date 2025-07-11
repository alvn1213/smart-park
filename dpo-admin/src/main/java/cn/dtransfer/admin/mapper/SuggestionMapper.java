package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Suggestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 投诉建议Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Mapper
public interface SuggestionMapper extends BaseMapper<Suggestion> {
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

}
