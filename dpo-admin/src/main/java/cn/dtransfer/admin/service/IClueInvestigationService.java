package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ClueInvestigation;

import java.util.List;

/**
 * 线索跟进Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IClueInvestigationService extends IService<ClueInvestigation> {
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

    /**
     * 新增线索跟进
     *
     * @param clueInvestigation 线索跟进
     * @return 结果
     */
    int insertClueInvestigation(ClueInvestigation clueInvestigation);

    /**
     * 修改线索跟进
     *
     * @param clueInvestigation 线索跟进
     * @return 结果
     */
    int updateClueInvestigation(ClueInvestigation clueInvestigation);

    /**
     * 批量删除线索跟进
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteClueInvestigationByIds(String ids);

    /**
     * 删除线索跟进信息
     *
     * @param id 线索跟进ID
     * @return 结果
     */
    int deleteClueInvestigationById(Long id);
}
