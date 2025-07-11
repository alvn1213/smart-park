package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Room;

import java.util.List;

/**
 * 房间管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
public interface IRoomService extends IService<Room> {
    /**
     * 查询房间管理
     *
     * @param id 房间管理ID
     * @return 房间管理
     */
    Room selectRoomById(Long id);


    /**
     * 查询未出租的房间
     * @return
     */
    List<Room> selectNotRentRooms(Long deptId);
}
