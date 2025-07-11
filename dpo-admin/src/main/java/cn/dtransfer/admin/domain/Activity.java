package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 活动管理对象 dpo_activity
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_activity")
public class Activity extends BaseEntity<Activity> {
    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 报名开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date signBegin;

    /**
     * 报名结止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date signEnd;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date actBegin;

    /**
     * 活动结止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date actEnd;

    /**
     * 活动地点
     */
    private String location;

    /**
     * 上传活动头图
     */
    private String headImg;

    /**
     * 上传活动小图
     */
    private String smallImg;

    /**
     * 上传详情
     */
    private String content;

    /**
     * 联系电话
     */
    private String contract;

    /**
     * 活动金额
     */
    private Long price;

    /**
     * 限制人数
     */
    private Long fullNum;

    /**
     * 状态
     */
    private Status status;

    /**
     * 状态
     */
    public enum Status implements IEnum<Integer> {

        /**
         * 报名未开始
         */
        SIGN_NOT_START("报名未开始", 0),

        /**
         * 报名中
         */
        SIGNING("报名中", 1),

        /**
         * 活动未开始
         */
        ACT_NOT_START("活动未开始", 2),


        /**
         * 已满额
         */
        SIGN_END("已满额", 3),

        /**
         * 活动中
         */
        ACTING("活动中", 4),

        /**
         * 活动结束
         */
        ACT_END("活动结束", 5);

        private String name;
        private int    value;

        Status(String name, int value) {
            this.name  = name;
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static Status parse(Integer value) {
            for (Status status : values()) {
                if (status.getValue().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date marketableTime;

    /**
     * 关联
     */
    @TableField(exist = false)
    List<ActivityDetail> activityDetailList;

}
