package cn.dtransfer.admin.domain;

import cn.dtransfer.admin.handler.ListIntHandler;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.BannerImageHandler;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 园区管理对象 dpo_parks
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName(value = "dpo_park", autoResultMap = true)
public class Park {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 版本
     */
    @Version
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableField(fill = FieldFill.INSERT)
//    @TableLogic
    private Integer deleteFlag;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 租户Id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

    /**
     * 园区名称
     */
    @NotBlank(message = "园区名称不能为空")
    @Length(max = 30, message = "名称不能超过30个字符")
    private String name;

    /**
     * 园区总面积
     */
    private Long area;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 园区总房间数
     */
    private Long roomNumbers;

    /**
     * 上传园区小图
     */
    private String smallPic;

    /**
     * 上传园区banner图
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> bannerImages = new ArrayList<>();

    /**
     * 园区简介（可选）
     */
    @Length(max = 500, message = "名称不能超过500个字符")
    private String briefIntro;

    /**
     * 租金范围(起)
     */
    private Long lowRent;

    /**
     * 租金范围(始)
     */
    private Long highRent;

    /**
     * 租金类型
     */
    private String rentType;

    /**
     * 城市Id 省，市，区
     */
    @TableField(typeHandler = ListIntHandler.class)
    private List<Integer> city = new ArrayList<>();

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地址
     */
    private String address;

    /**
     * 配套服务（设施）
     */
    private String facilities;

    /**
     * 交通
     */
    @Length(max = 50, message = "名称不能超过50个字符")
    private String traffic;

    /**
     * 停车费（元/小时）
     */
    private Long parkingFee;

    /**
     * 园区图文介绍
     */
    private String introduction;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    private Date marketableTime;

    @TableField(exist = false)
    private Double distance;

}
