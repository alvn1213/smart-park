package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.system.domain.User;
import lombok.Data;

import java.util.Date;

/**
 * 活动报名详情对象 dpo_activity_detail
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_activity_detail")
public class ActivityDetail extends BaseEntity<ActivityDetail> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 报名时间
     */
    private Date signDate;

    /**
     * 用户表id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long actId;

    /**
     * 关联用户对象
     */
    private User user;

    /**
     * 关联用户对象
     */
    @TableField(exist = false)
    private Activity activity;

}
