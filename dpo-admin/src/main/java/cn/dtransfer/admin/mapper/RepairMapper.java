package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工单管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Mapper
public interface RepairMapper extends BaseMapper<Repair> {
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
     * 查询我的工单管理列表
     *
     * @param repair 工单管理
     * @return 工单管理集合
     */
    List<Repair> selectMyRepairList(Repair repair);

}
