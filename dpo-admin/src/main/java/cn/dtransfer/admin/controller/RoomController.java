package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IRoomService;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.vo.RoomMapVO;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;

/**
 * 房间管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@RestController
@RequestMapping("/admin/room")
public class RoomController extends BaseController {

    @Autowired
    private IRoomService roomService;


    /**
     * 查询房间管理
     */
    @RequiresPermissions("admin:room:view")
    @GetMapping("get/{id}")
    public Room get(@PathVariable("id") Long id) {
        return roomService.selectRoomById(id);
    }

    /**
     * 查询房间管理列表
     */
    @RequiresPermissions("admin:room:list")
    @GetMapping("list")
    public R list(Room room) {
        startPage();
        room.setDeleteFlag(0);
        return result(roomService.selectRoomList(room));
    }


    /**
     * 新增保存房间管理
     */
    @RequiresPermissions("admin:room:add")
    @PostMapping("save")
    public R addSave(@RequestBody Room room) {
        ValidatorUtils.validateEntity(room);
        return toAjax(roomService.insertRoom(room));
    }

    /**
     * 修改保存房间管理
     */
    @RequiresPermissions("admin:room:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Room room) {
        ValidatorUtils.validateEntity(room);
        return toAjax(roomService.updateRoom(room));
    }

    /**
     * 删除房间管理
     */
    @RequiresPermissions("admin:room:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(roomService.deleteRoomByIds(ids));
    }

    /**
     * 是否上架
     */
    @RequiresPermissions("admin:room:edit")
    @PostMapping("/changeMarketable")
    public R changeMarketable(@RequestBody Room room) {
        Room newRoom = roomService.selectRoomById(room.getId());
        if (newRoom == null) {
            return R.error("房间不存在!");
        }
        newRoom.setIsMarketable(room.getIsMarketable());
        newRoom.setMarketableTime(DateUtils.getNowDate());
        return toAjax(roomService.updateRoom(newRoom));
    }


    /**
     * 房态列表数据
     * @param roomForm
     * @return
     */
    @RequiresPermissions("admin:room:list")
    @GetMapping("map")
    public R map(Room roomForm){
        roomForm.setDeleteFlag(0);
        List<RoomMapVO> parkMapVOList = roomService.selectParkMapVOList(roomForm);
        return R.data(parkMapVOList);
    }

}
