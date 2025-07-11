package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IClueInvestigationService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ClueInvestigation;
import cn.dtransfer.admin.mapper.ClueInvestigationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 线索跟进Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service
public class ClueInvestigationServiceImpl extends ServiceImpl<ClueInvestigationMapper, ClueInvestigation> implements IClueInvestigationService {
    @Autowired
    private ClueInvestigationMapper clueInvestigationMapper;

    /**
     * 查询线索跟进
     *
     * @param id 线索跟进ID
     * @return 线索跟进
     */
    @Override
    public ClueInvestigation selectClueInvestigationById(Long id) {
        return clueInvestigationMapper.selectClueInvestigationById(id);
    }

    /**
     * 查询线索跟进列表
     *
     * @param clueInvestigation 线索跟进
     * @return 线索跟进
     */
    @Override
    public List<ClueInvestigation> selectClueInvestigationList(ClueInvestigation clueInvestigation) {
        return clueInvestigationMapper.selectClueInvestigationList(clueInvestigation);
    }

    /**
     * 新增线索跟进
     *
     * @param clueInvestigation 线索跟进
     * @return 结果
     */
    @Override
    public int insertClueInvestigation(ClueInvestigation clueInvestigation) {
        clueInvestigation.setId(null);
        clueInvestigation.setSource(null);
        return clueInvestigationMapper.insert(clueInvestigation);
    }

    /**
     * 修改线索跟进
     *
     * @param clueInvestigation 线索跟进
     * @return 结果
     */
    @Override
    public int updateClueInvestigation(ClueInvestigation clueInvestigation) {
        return clueInvestigationMapper.updateById(clueInvestigation);
    }

    /**
     * 删除线索跟进对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClueInvestigationByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return clueInvestigationMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除线索跟进信息
     *
     * @param id 线索跟进ID
     * @return 结果
     */
    @Override
    public int deleteClueInvestigationById(Long id) {
        return clueInvestigationMapper.deleteById(id);
    }
}
