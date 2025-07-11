package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplyRoom;

/**
 * 申请房间关联Service接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
public interface IApplyRoomService extends IService<ApplyRoom> {


    /**
     * 根据房间id查询
     *
     * @return
     */
    ApplyRoom selectApplyRoomId(Long roomId,Long userId);
}
