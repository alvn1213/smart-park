package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 申请房间关联对象 dpo_apply_room
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Data
@TableName("dpo_apply_room")
public class ApplyRoom implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 申请用户id
     */
    private Long userId;

}
