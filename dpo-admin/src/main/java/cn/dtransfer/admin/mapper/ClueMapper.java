package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Clue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 线索管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Mapper
public interface ClueMapper extends BaseMapper<Clue> {
    /**
     * 查询线索管理
     *
     * @param id 线索管理ID
     * @return 线索管理
     */
    Clue selectClueById(Long id);

    /**
     * 查询线索管理列表
     *
     * @param clue 线索管理
     * @return 线索管理集合
     */
    List<Clue> selectClueList(Clue clue);

}
