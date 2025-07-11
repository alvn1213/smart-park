package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Clue;

import java.util.List;

/**
 * 线索管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IClueService extends IService<Clue> {
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

    /**
     * 新增线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    int insertClue(Clue clue);

    /**
     * 修改线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    int updateClue(Clue clue);

    /**
     * 批量删除线索管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteClueByIds(String ids);

    /**
     * 删除线索管理信息
     *
     * @param id 线索管理ID
     * @return 结果
     */
    int deleteClueById(Long id);
}
