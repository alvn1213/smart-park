package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IRoomService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.mapper.RoomMapper;
import cn.dtransfer.admin.vo.RoomMapVO;
import cn.dtransfer.system.service.ICurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ICurrentUserService currentUserService;

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
     * 查询房间管理列表
     *
     * @param room 房间管理
     * @return 房间管理
     */
    @Override
    public List<Room> selectRoomList(Room room) {
        return roomMapper.selectRoomList(room);
    }


    /**
     * 新增房间管理
     *
     * @param room 房间管理
     * @return 结果
     */
    @Override
    public int insertRoom(Room room) {
        return roomMapper.insert(room);
    }

    /**
     * 修改房间管理
     *
     * @param room 房间管理
     * @return 结果
     */
    @Override
    public int updateRoom(Room room) {
        return roomMapper.updateById(room);
    }

    /**
     * 删除房间管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRoomByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return roomMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除房间管理信息
     *
     * @param id 房间管理ID
     * @return 结果
     */
    @Override
    public int deleteRoomById(Long id) {
        return roomMapper.deleteById(id);
    }

    /**
     * 房态列表数据
     * @param room
     * @return
     */
    @Override
    public List<RoomMapVO> selectParkMapVOList(Room room) {
        return roomMapper.selectParkMapVOList(room);
    }

    /**
     * 查询未出租的房间
     * @return
     */
    @Override
    public List<Room> selectNotRentRooms(Long deptId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", Room.Status.NO.getValue());
        queryWrapper.eq("is_marketable", true);
        queryWrapper.eq("dept_id", deptId);
        return roomMapper.selectList(queryWrapper);
    }


}
