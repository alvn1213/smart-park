package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.ApplySettleStatusEnumHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 注册迁入申请对象 dpo_apply_move_in
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Data
@TableName("dpo_apply_move_in")
public class ApplyMoveIn extends BaseEntity<ApplyMoveIn> {
    private static final long serialVersionUID = 1L;

    /** 申请业务 */
    @NotNull(message = "申请业务不能为空")
    private Long type;

    /** 申请人 */
    @NotBlank(message = "申请人不能为空")
    private String applyName;

    /** 承租物业地址 */
    @NotBlank(message = "承租物业地址不能为空")
    private String address;

    /** 租赁物业面积 */
    @NotNull(message = "租赁物业面积不能为空")
    private BigDecimal area;

    /** 姓名 */
    @NotBlank(message = "姓名不能为空")
    private String userName;

    /** 联系电话 */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /** 物业合同编号 */
    @NotBlank(message = "物业合同编号不能为空")
    private String contractNo;

    /** 审批状态 */
    @TableField(typeHandler = ApplySettleStatusEnumHandler.class)
    private ApplySettle.Status status;


    /** 租赁开始时间 */
    @NotNull(message = "租赁开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentStartTime;

    /** 租赁结束时间 */
    @NotNull(message = "租赁结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentEndTime;

    /** 拟成立/迁入企业名称 */
    @NotBlank(message = "拟成立/迁入企业名称不能为空")
    private String companyName;

    /** 与申请人关系：子公司/分公司/其他 */
    @NotNull(message = "与申请人关系不能为空")
    private Long relation;

    /** 申请人承诺 */
    @NotBlank(message = "申请人承诺不能为空")
    private String promise;

    /** 创建人id */
    private Long createUserId;

    /** 审批意见 */
    private String remark;

    /**
     * 关联迁入文件
     */
    @TableField(exist = false)
    private List<ApplyMoveInFile> applyMoveInFileList;


}
