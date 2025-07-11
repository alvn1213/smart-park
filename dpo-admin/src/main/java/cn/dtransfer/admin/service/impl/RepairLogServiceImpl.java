package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IRepairLogService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.RepairLog;
import cn.dtransfer.admin.mapper.RepairLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报修记录Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service
public class RepairLogServiceImpl extends ServiceImpl<RepairLogMapper, RepairLog> implements IRepairLogService {
    @Autowired
    private RepairLogMapper repairLogMapper;

    /**
     * 查询报修记录
     *
     * @param id 报修记录ID
     * @return 报修记录
     */
    @Override
    public RepairLog selectRepairLogById(Long id) {
        return repairLogMapper.selectById(id);
    }

    /**
     * 查询报修记录列表
     *
     * @param repairLog 报修记录
     * @return 报修记录
     */
    @Override
    public List<RepairLog> selectRepairLogList(RepairLog repairLog) {
        return repairLogMapper.selectRepairLogList(repairLog);
    }

    /**
     * 新增报修记录
     *
     * @param repairLog 报修记录
     * @return 结果
     */
    @Override
    public int insertRepairLog(RepairLog repairLog) {
        return repairLogMapper.insert(repairLog);
    }

    /**
     * 修改报修记录
     *
     * @param repairLog 报修记录
     * @return 结果
     */
    @Override
    public int updateRepairLog(RepairLog repairLog) {
        return repairLogMapper.updateById(repairLog);
    }

    /**
     * 删除报修记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRepairLogByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return repairLogMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除报修记录信息
     *
     * @param id 报修记录ID
     * @return 结果
     */
    @Override
    public int deleteRepairLogById(Long id) {
        return repairLogMapper.deleteById(id);
    }
}
