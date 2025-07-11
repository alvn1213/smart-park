package cn.dtransfer.service.impl;

import cn.dtransfer.service.IRepairLogService;
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
@Service("repairLogAppService")
public class RepairLogServiceImpl extends ServiceImpl<RepairLogMapper, RepairLog> implements IRepairLogService {
    @Autowired
    private RepairLogMapper repairLogMapper;



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


}
