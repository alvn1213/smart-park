package cn.dtransfer.admin.vo;


import cn.dtransfer.admin.handler.RoomStatusEnumHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.dtransfer.admin.domain.Room;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 房间VO
 *
 * @author dtransfer
 */
@Data
public class RoomVO {

    /** 房间id */
    private Long roomId;

    /** 房间名称 */
    private String roomName;

    /** 租赁状态 */
    @TableField(typeHandler = RoomStatusEnumHandler.class)
    private Room.Status status;

    /** 面积 */
    private BigDecimal area;

    /** 租金 */
    private BigDecimal rent;

    /** 租金类型:元/㎡/天,元/㎡/月,元/天,元/月 */
    private String rentType;

    /** 空置多少天 */
    private Integer expireDate;

}
