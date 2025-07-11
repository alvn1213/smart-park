package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.vo.RoomMapVO;

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
     * 查询房间管理列表
     *
     * @param room 房间管理
     * @return 房间管理集合
     */
    List<Room> selectRoomList(Room room);

    /**
     * 新增房间管理
     *
     * @param room 房间管理
     * @return 结果
     */
    int insertRoom(Room room);

    /**
     * 修改房间管理
     *
     * @param room 房间管理
     * @return 结果
     */
    int updateRoom(Room room);

    /**
     * 批量删除房间管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoomByIds(String ids);

    /**
     * 删除房间管理信息
     *
     * @param id 房间管理ID
     * @return 结果
     */
    int deleteRoomById(Long id);

    /**
     * 查询房间管理列表
     *
     * @param room 房间管理
     * @return 房间管理集合
     */
    List<RoomMapVO> selectParkMapVOList(Room room);


    /**
     * 查询未出租的房间
     * @return
     */
    List<Room> selectNotRentRooms(Long deptId);
}
