package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Repair;

import java.util.List;

/**
 * 工单管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IRepairService extends IService<Repair> {
    /**
     * 查询工单管理
     *
     * @param id 工单管理ID
     * @return 工单管理
     */
    Repair selectRepairById(Long id);


    /**
     * 新增工单管理
     *
     * @param repair 工单管理
     * @return 结果
     */
    int insertRepair(Repair repair);

    /**
     * 修改工单管理
     *
     * @param repair 工单管理
     * @return 结果
     */
    int updateRepair(Repair repair);


    /**
     * 查询我的工单管理列表
     *
     * @param repair 创建用户
     * @return 工单管理集合
     */
    List<Repair> selectMyRepairList(Repair repair);
}
