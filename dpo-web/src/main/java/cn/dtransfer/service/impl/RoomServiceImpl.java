package cn.dtransfer.service.impl;

import cn.dtransfer.service.IRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Service("roomAppService")
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
    @Autowired
    private RoomMapper roomMapper;



    /**
     * 查询房间管理
     *
     * @param id 房间管理ID
     * @return 房间管理
     */
    @Override
    public Room selectRoomById(Long id) {
        return roomMapper.selectByRoomId(id);
    }

    /**
     * 查询未出租的房间
     * @return
     */
    @Override
    public List<Room> selectNotRentRooms(Long parkId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", Room.Status.NO.getValue());
        queryWrapper.eq("is_marketable", true);
        queryWrapper.eq("park_id", parkId);
        return roomMapper.selectList(queryWrapper);
    }


}
