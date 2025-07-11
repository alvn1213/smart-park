package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ApplyRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 申请房间关联Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Mapper
public interface ApplyRoomMapper extends BaseMapper<ApplyRoom> {
    /**
     * 查询申请房间关联
     *
     * @param id 申请房间关联ID
     * @return 申请房间关联
     */
    ApplyRoom selectApplyRoomById(Long id);

    /**
     * 查询申请房间关联列表
     *
     * @param applyRoom 申请房间关联
     * @return 申请房间关联集合
     */
    List<ApplyRoom> selectApplyRoomList(ApplyRoom applyRoom);


}
