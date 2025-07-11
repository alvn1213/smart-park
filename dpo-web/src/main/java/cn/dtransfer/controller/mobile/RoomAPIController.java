package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IApplyRoomService;
import cn.dtransfer.service.IClueService;
import cn.dtransfer.service.IRoomService;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.ApplyRoom;
import cn.dtransfer.admin.domain.Room;
import cn.dtransfer.admin.domain.Clue;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;


/**
 * 租办公室
 *
 * @author dtransfer
 */

@RestController
@RequestMapping("/room")
public class RoomAPIController extends BaseController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IApplyRoomService applyRoomService;

    @Autowired
    private IClueService clueService;

    /**
     * 出租房源列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Long parkId) {
        List<Room> roomList = roomService.selectNotRentRooms(parkId);
        List<Map> mapList = Lists.newArrayList();
        for (Room item : roomList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("area", item.getArea());
            map.put("rent", item.getRent());
            map.put("managementFee", item.getManagementFee());
            map.put("smallPic", item.getSmallPic());
//            map.put("maximum", item.getMaximum());
//            map.put("minimum", item.getMinimum());
            mapList.add(map);
        }
        return result(mapList);
    }

    /**
     * 查看房间详情
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("detail")
    public R detail(Long roomId) {
        Room room = roomService.selectRoomById(roomId);
        if (room == null) {
            return R.error("房间不存在!");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", room.getId());
        map.put("name", room.getName());
        map.put("area", room.getArea());
        map.put("rent", room.getRent());
        map.put("managementFee", room.getManagementFee());
        map.put("smallPic", room.getSmallPic());
        map.put("bannerImages", room.getBannerImages());
//        map.put("maximum", room.getMaximum());
//        map.put("minimum", room.getMinimum());
        map.put("briefIntro", room.getBriefIntro());
        map.put("floorHeight", room.getFloorHeight());
        map.put("canBeDivided", room.getCanBeDivided());
        map.put("commonArea", room.getCommonArea());
        map.put("rentType", room.getRentType());
        map.put("managementFeeType", room.getManagementFeeType());
        map.put("decorationType", room.getDecorationType().getName());
        map.put("availableFrom", room.getAvailableFrom());
        map.put("parkName", room.getPark().getName());
        return R.data(map);
    }

    /**
     * 申请租房信息
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("apply")
    public R apply(Long roomId) {
        Room room = roomService.selectRoomById(roomId);
        if (room == null) {
            return R.error("房间不存在!");
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", room.getId());
        map.put("name", room.getName());
        map.put("area", room.getArea());
        map.put("rent", room.getRent());
        map.put("rentType", room.getRentType());
        map.put("customerName", getLoginName());
        map.put("smallPic", room.getSmallPic());
        map.put("managementFee", room.getManagementFee());
        map.put("managementFeeType", room.getManagementFeeType());
        map.put("decorationType", room.getDecorationType().getName());
        map.put("bannerImages", room.getBannerImages());
        map.put("parkName", room.getPark().getName());
        return R.data(map);
    }

    /**
     * 提交申请（租房信息）
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("submit")
    public R submit(Long roomId, String contacts, String phone) {
        Room room = roomService.selectRoomById(roomId);
        if (room == null) {
            return R.error("房间不存在!");
        }
        ApplyRoom applyRoom = new ApplyRoom();
        if (roomId != null) {
            ApplyRoom applyRoomList = applyRoomService.selectApplyRoomId(roomId, getCurrentUserId());
            if (applyRoomList != null) {
                return R.error("请勿重复申请!");
            }
            // 申请房间关联
            applyRoom.setRoomId(roomId);
            applyRoom.setUserId(getCurrentUserId());

            Clue clue = new Clue();
            clue.setChannelCategory("1");
            clue.setSource("6");
            clue.setCustomerType("1");
            clue.setChannelName("微信小程序申请");
            clue.setClueName(room.getName());
            clue.setCustomerName(contacts);
            clue.setContacts(contacts);
            clue.setPhone(phone);
            clue.setParkId(room.getParkId());
            clue.setTenantId(room.getTenantId());
            clue.setApplyRoom(applyRoom);
            return toAjax(clueService.insertClue(clue));
        } else {
            return R.error("请选择房间!");
        }
    }

}
