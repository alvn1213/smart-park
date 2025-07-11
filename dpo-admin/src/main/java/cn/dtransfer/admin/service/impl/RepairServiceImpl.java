package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IRepairLogService;
import cn.dtransfer.admin.service.IRepairService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Repair;
import cn.dtransfer.admin.domain.RepairLog;
import cn.dtransfer.admin.mapper.RepairMapper;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.system.domain.Sn;
import cn.dtransfer.system.service.ICurrentUserService;
import cn.dtransfer.system.service.ISnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 工单管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements IRepairService {
    @Autowired
    private RepairMapper repairMapper;

     @Autowired
     private ISnService snService;

    @Autowired
    private IRepairLogService repairLogService;

    @Autowired
    private ICurrentUserService currentUserService;

    /**
     * 查询工单管理
     *
     * @param id 工单管理ID
     * @return 工单管理
     */
    @Override
    public Repair selectRepairById(Long id) {
        return repairMapper.selectRepairById(id);
    }

    /**
     * 查询工单管理列表
     *
     * @param repair 工单管理
     * @return 工单管理
     */
    @Override
    public List<Repair> selectRepairList(Repair repair) {
        return repairMapper.selectRepairList(repair);
    }

    /**
     * 新增工单管理
     *
     * @param repair 工单管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRepair(Repair repair) {
        repair.setSn(snService.generate(Sn.Type.REPAIR));
        repair.setCreateTime(DateUtils.getNowDate());
        int result = repairMapper.insert(repair);
        // 增加报修日志
        RepairLog repairLog = new RepairLog();
        repairLog.setType(repair.getStatus());
        repairLog.setDetail(String.format("工单号：%s,已收到报修", repair.getSn()));
        repairLog.setRepairId(repair.getId());
        repairLog.setCreateBy(repair.getCreateBy());
        repairLog.setCreateTime(DateUtils.getNowDate());
        repairLog.setUpdateBy(repair.getCreateBy());
        repairLog.setUpdateTime(DateUtils.getNowDate());
        repairLog.setParkId(repair.getParkId());
        repairLog.setTenantId(repair.getTenantId());
        repairLogService.insertRepairLog(repairLog);
        return result;
    }

    /**
     * 修改工单管理
     *
     * @param repair 工单管理
     * @return 结果
     */
    @Override
    public int updateRepair(Repair repair) {
        // 增加报修日志
        RepairLog repairLog = new RepairLog();
        repairLog.setType(repair.getStatus());
        repairLog.setDetail(repair.getStatus().getName());
        repairLog.setRepairId(repair.getId());
        repairLog.setCreateBy(repair.getUpdateBy());
        repairLog.setCreateTime(DateUtils.getNowDate());
        repairLog.setUpdateBy(repair.getUpdateBy());
        repairLog.setUpdateTime(DateUtils.getNowDate());
        repairLogService.insertRepairLog(repairLog);
        return repairMapper.updateById(repair);
    }

    /**
     * 删除工单管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRepairByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return repairMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除工单管理信息
     *
     * @param id 工单管理ID
     * @return 结果
     */
    @Override
    public int deleteRepairById(Long id) {
        return repairMapper.deleteById(id);
    }


    /**
     * 查询工单管理列表
     *
     * @param repair 工单管理
     * @return 工单管理
     */
    @Override
    public List<Repair> selectMyRepairList(Repair repair) {
        return repairMapper.selectMyRepairList(repair);
    }

}
