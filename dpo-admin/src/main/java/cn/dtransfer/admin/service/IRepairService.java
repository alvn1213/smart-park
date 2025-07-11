package cn.dtransfer.admin.service;

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
     * 查询工单管理列表
     *
     * @param repair 工单管理
     * @return 工单管理集合
     */
    List<Repair> selectRepairList(Repair repair);

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
     * 批量删除工单管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRepairByIds(String ids);

    /**
     * 删除工单管理信息
     *
     * @param id 工单管理ID
     * @return 结果
     */
    int deleteRepairById(Long id);


    /**
     * 查询我的工单管理列表
     *
     * @param repair 创建用户
     * @return 工单管理集合
     */
    List<Repair> selectMyRepairList(Repair repair);
}
