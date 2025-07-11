package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.vo.RoomMapVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 房间管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    /**
     * 查询房间管理列表
     *
     * @return 房间管理集合
     */
    List<Room> selectRoomList(Room room);

    /**
     * 查询园区房间平面数据列表
     *
     * @return 房间管理集合
     */
    List<RoomMapVO> selectParkMapVOList(Room room);


    Room selectByRoomId(Long id);
}
