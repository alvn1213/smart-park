package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.system.domain.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 投诉建议对象 dpo_suggestion
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Data
@TableName("dpo_suggestion")
public class Suggestion extends BaseEntity<Suggestion> {
    private static final long serialVersionUID = 1L;

    /**
     * 问题意见描述
     */
    private String content;

    /**
     * 投诉单号
     */
    private String sn;

    /**
     * 图片(限三张)
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> images = new ArrayList<>();

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date processingTime;

    /**
     * 状态:指派中 处理中 已经处理
     */
    private int status;

    /**
     * 是否匿名:0-否,1-是
     */
    private Boolean isAnonymous;

    /**
     * 指派人员id
     */
    private Long userId;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;

    /**
     * 园区
     */
    @TableField(exist = false)
    private Park park;

}
