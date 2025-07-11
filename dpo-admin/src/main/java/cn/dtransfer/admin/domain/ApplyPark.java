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
 * 入园申请对象 dpo_apply_park
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Data
@TableName("dpo_apply_park")
public class ApplyPark extends BaseEntity<ApplyPark> {
    private static final long serialVersionUID = 1L;

    /** 入园企业名称 */
    @NotBlank(message = "入园企业名称不能为空")
    private String name;

    @NotNull(message = "公司原办公地址不能为空")
    /** 公司原办公地址 */
    private Long type;

    /** 是否注册 */
    @NotNull(message = "是否注册不能为空")
    private Boolean isRegister;

    /** 法人代表 */
    @NotBlank(message = "法人代表不能为空")
    private String operName;

    /** 注册资本 */
    @NotBlank(message = "注册资本不能为空")
    private String registCapi;

    /** 公司类型(企业性质) */
    private String econKind;


    /** 审批状态 */
    @TableField(typeHandler = ApplySettleStatusEnumHandler.class)
    private ApplySettle.Status status;


    /** 国家 */
    private String nation;

    /** 姓名 */
    private String userName;

    /** 职务 */
    private String position;

    /** 联系电话 */
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    /** 联系QQ */
    private String qq;

    /** 邮箱 */
    private String email;

    /** 网址 */
    private String site;

    /** 公司人数 */
    private Long persons;

    /** 是否合法合规 */
    private Boolean isCompliance;

    /** 预估年产值 */
    private BigDecimal preYearValue;

    /** 期望入驻开始时间 */
    @NotNull(message = "期望入驻开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 期望入驻结束时间 */
    @NotNull(message = "期望入驻结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /** 申请办公面积 */
    @NotNull(message = "申请办公面积不能为空")
    private BigDecimal applyArea;

    /** 简介 */
    private String profile;

    /** 主要业务范围 */
    private String scope;

    /** 企业优势 */
    private String advantage;

    /** 申请入驻原因 */
    @NotBlank(message = "申请入驻原因不能为空")
    private String reason;

    /** 备注 */
    private String memo;

    /** 创建用户id */
    private Long createUserId;

    /** 审批意见 */
    private String remark;

    @TableField(exist = false)
    private List<ApplyParkFile> applyParkFileList;


}
