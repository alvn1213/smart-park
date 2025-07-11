package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.RepairLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 报修记录Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Mapper
public interface RepairLogMapper extends BaseMapper<RepairLog> {

    /**
     * 查询报修记录列表
     *
     * @param repairLog 报修记录
     * @return 报修记录集合
     */
    List<RepairLog> selectRepairLogList(RepairLog repairLog);
}
