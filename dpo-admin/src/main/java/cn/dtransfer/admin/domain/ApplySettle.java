package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 入驻申请对象 dpo_apply_settle
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Data
@TableName("dpo_apply_settle")
public class ApplySettle extends BaseEntity<ApplySettle> {
    private static final long serialVersionUID = 1L;

    /** 入驻企业名称 */
    @NotBlank(message = "入驻企业名称不能为空")
    private String name;

    /** 姓名 */
    @NotBlank(message = "姓名不能为空")
    private String userName;

    /** 法人代表 */
    @NotBlank(message = "法人代表不能为空")
    private String operName;

    /** 公司原办公地址 */
    @NotNull(message = "公司原办公地址不能为空")
    private Long type;

    /** 所需办公面积 */
    @NotNull(message = "所需办公面积不能为空")
    private BigDecimal needArea;

    /** 首批入驻人数 */
    private Long persons;

    /** 申请入驻日期 */
    @NotNull(message = "申请入驻日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 联系电话 */
    @NotBlank(message = "联系电话不能为空")
    private String phone;


    /** 邮箱 */
    private String email;

    /** 审批状态 */
    private Status status;

    /**
     * 审批状态
     */
    public enum Status implements IEnum<Integer> {

        /**
         * 待审批
         */
        APPROVING("待审批", 0),

        /**
         * 审批通过
         */
        PASS("审批通过", 1),

        /**
         * 已拒绝
         */
        REFUSE("已拒绝", 2);


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
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 审批意见
     */
    private String remark;

    /**
     * 关联上传文件
     */
    @TableField(exist = false)
    private List<ApplySettleFile> applySettleFileList;


}
