package cn.dtransfer.service.impl;

import cn.dtransfer.service.IApplyRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplyRoom;
import cn.dtransfer.admin.mapper.ApplyRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 申请房间关联Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Service("applyRoomAppService")
public class ApplyRoomServiceImpl extends ServiceImpl<ApplyRoomMapper, ApplyRoom> implements IApplyRoomService {

    @Autowired
    private ApplyRoomMapper applyRoomMapper;


    /**
     * 根据房间id查询
     *
     * @return
     */
    public ApplyRoom selectApplyRoomId(Long roomId,Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.eq("user_id", userId);
        return applyRoomMapper.selectOne(queryWrapper);
    }
}
