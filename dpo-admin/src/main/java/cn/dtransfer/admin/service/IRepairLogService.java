package cn.dtransfer.admin.service;

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
     * 查询报修记录
     *
     * @param id 报修记录ID
     * @return 报修记录
     */
    RepairLog selectRepairLogById(Long id);

    /**
     * 查询报修记录列表
     *
     * @param repairLog 报修记录
     * @return 报修记录集合
     */
    List<RepairLog> selectRepairLogList(RepairLog repairLog);

    /**
     * 新增报修记录
     *
     * @param repairLog 报修记录
     * @return 结果
     */
    int insertRepairLog(RepairLog repairLog);

    /**
     * 修改报修记录
     *
     * @param repairLog 报修记录
     * @return 结果
     */
    int updateRepairLog(RepairLog repairLog);

    /**
     * 批量删除报修记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRepairLogByIds(String ids);

    /**
     * 删除报修记录信息
     *
     * @param id 报修记录ID
     * @return 结果
     */
    int deleteRepairLogById(Long id);
}
