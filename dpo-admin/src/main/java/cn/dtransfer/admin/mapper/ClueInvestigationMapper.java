package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ClueInvestigation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 线索跟进Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Mapper
public interface ClueInvestigationMapper extends BaseMapper<ClueInvestigation> {
    /**
     * 查询线索跟进
     *
     * @param id 线索跟进ID
     * @return 线索跟进
     */
    ClueInvestigation selectClueInvestigationById(Long id);

    /**
     * 查询线索跟进列表
     *
     * @param clueInvestigation 线索跟进
     * @return 线索跟进集合
     */
    List<ClueInvestigation> selectClueInvestigationList(ClueInvestigation clueInvestigation);


}
