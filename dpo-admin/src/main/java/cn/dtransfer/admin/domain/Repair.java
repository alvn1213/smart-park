package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
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
 * 工单管理对象 dpo_repair
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_repair")
public class Repair extends BaseEntity<Repair> {
    private static final long serialVersionUID = 1L;

    /**
     * 报修单号
     */
    private String sn;

    /**
     * 报修人
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 报修区域
     */
    private String area;

    /**
     * 状态:待分配,待评估,处理中,已处理, 已处理,待评价,已评价
     */
    private Status status;

    public enum Status implements IEnum<Integer> {
        /**
         * 待分配
         */
        PENDING_ASSIGN("待分配", 0),

        /**
         * 待处理
         */
        PENDING_PROCESS("待处理", 1),

        /**
         * 已完成
         */
        COMPLETED("已完成", 2),

        /**
         * 已评价
         */
        SCORE("已评价", 3),

        /**
         * 已取消
         */
        CANCELED("已取消", 4);

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
     * 描述
     */
    private String content;

    /**
     * 报修时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairTime;

    /**
     * 报修图片
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> repairImages = new ArrayList<>();

    /**
     * 维修费
     */
    private Long repairFee;

    /**
     * 评价
     */
    private Long repairScore;

    /**
     * 维修材料
     */
    private String repairMateriel;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 意见建议
     */
    private String remark;

    /**
     * 评价工人
     */
    private Long workerScore;

    /**
     * 维修工人
     */
    private Long workerId;

    /**
     * 公司ID
     */
    private Long customerId;

    /**
     * 公司
     */
    @TableField(exist = false)
    private Customer customer;

    /**
     * 园区
     */
    @TableField(exist = false)
    private Park park;

    /**
     * 用户
     */
    @TableField(exist = false)
    private User user;


}
