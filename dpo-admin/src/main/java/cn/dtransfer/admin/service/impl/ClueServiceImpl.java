package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IClueService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Clue;
import cn.dtransfer.admin.mapper.ApplyRoomMapper;
import cn.dtransfer.admin.mapper.ClueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 线索管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ApplyRoomMapper applyRoomMapper;


    /**
     * 查询线索管理
     *
     * @param id 线索管理ID
     * @return 线索管理
     */
    @Override
    public Clue selectClueById(Long id) {
        return clueMapper.selectClueById(id);
    }

    /**
     * 查询线索管理列表
     *
     * @param clue 线索管理
     * @return 线索管理
     */
    @Override
    public List<Clue> selectClueList(Clue clue) {
         return clueMapper.selectClueList(clue);
    }

    /**
     * 新增线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertClue(Clue clue) {
        clue.setAssignmentStatus("0");
        clue.setCustomerStatus("0");
        if(clue.getApplyRoom()!=null) {
            applyRoomMapper.insert(clue.getApplyRoom());
        }
        return clueMapper.insert(clue);
    }

    /**
     * 修改线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    @Override
    public int updateClue(Clue clue) {
        return clueMapper.updateById(clue);
    }

    /**
     * 删除线索管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteClueByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return clueMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除线索管理信息
     *
     * @param id 线索管理ID
     * @return 结果
     */
    @Override
    public int deleteClueById(Long id) {
        return clueMapper.deleteById(id);
    }
}
