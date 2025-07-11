package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.RepairLog;

import java.util.List;

/**
 * 报修记录Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IRepairLogService extends IService<RepairLog> {

    /**
     * 查询报修记录列表
     *
     * @param repairLog 报修记录
     * @return 报修记录集合
     */
    List<RepairLog> selectRepairLogList(RepairLog repairLog);


}
