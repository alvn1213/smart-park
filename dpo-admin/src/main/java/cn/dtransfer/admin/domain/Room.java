package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 房间管理对象 dpo_room
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Data
@TableName(value = "dpo_room", autoResultMap = true)
public class Room extends BaseEntity<Room> {
    private static final long serialVersionUID = 1L;

    /**
     * 楼宇id
     */
    private Long buildingId;

    /**
     * 楼层id
     */
    private Long buildingDetailId;

    /**
     * 房间名称
     */
    @NotBlank(message = "房间名称不能为空")
    private String name;

    /**
     * 租赁状态
     */
    private Status status;

    /**
     * 租赁状态
     */
    public enum Status implements IEnum<Integer> {

        /**
         * 未租
         */
        NO("未租", 0),

        /**
         * 已租
         */
        YES("已租", 1);

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
     * 更新租赁状态时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateStatusTime;

    /**
     * 租金
     */
    @NotNull(message = "租金不能为空")
    private BigDecimal rent;

    /**
     * 租金类型:元/㎡/天,元/㎡/月,元/天,元/月
     */
    @NotBlank(message = "租金类型不能为空")
    private String rentType;

    /**
     * 物业费
     */
    private BigDecimal managementFee;

    /**
     * 物业费类型:元/㎡/天,元/㎡/月,元/天,元/月
     */
    private String managementFeeType;

    /**
     * 面积(平方米)
     */
    @NotNull(message = "面积不能为空")
    private BigDecimal area;

    /**
     * 收租面积(平方米)
     */
    private BigDecimal rentArea;

    /**
     * 公摊面积(平方米)
     */
    private BigDecimal commonArea;

    /**
     * 可分割
     */
    private Boolean canBeDivided;

    /**
     * 房型:loft,平层,商业,其他
     */
    private Layout layout;

    /**
     * 房型
     */
    public enum Layout implements IEnum<Integer> {

        /**
         * loft
         */
        LOFT("loft", 0),

        /**
         * 商业配套
         */
        BUSINESS("商业配套", 1),

        /**
         * 办公场所
         */
        OFFICE("办公场所", 2),

        /**
         * 其他
         */
        OTHER("其他", 3);

        private String name;
        private int    value;

        Layout(String name, int value) {
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

        public static Layout parse(Integer value) {
            for (Layout layout : values()) {
                if (layout.getValue().equals(value)) {
                    return layout;
                }
            }
            return null;
        }

    }

    /**
     * 层高(米)
     */
    private BigDecimal floorHeight;

    /**
     * 可入住日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date availableFrom;


    /**
     * 装修类型:简装,精装,毛坯房
     */
    private DecorationType decorationType;


    /**
     * 装修类型
     */
    public enum DecorationType implements IEnum<Integer> {

        /**
         * 简装
         */
        SIMPLE("简装", 0),

        /**
         * 精装
         */
        SPECIAL("精装", 1),

        /**
         * 毛坯房
         */
        ROUGH("毛坯房", 2);

        private String name;
        private int    value;

        DecorationType(String name, int value) {
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

        public static DecorationType parse(Integer value) {
            for (DecorationType decorationType : values()) {
                if (decorationType.getValue().equals(value)) {
                    return decorationType;
                }
            }
            return null;
        }
    }

    /**
     * 奖金
     */
    private BigDecimal bonus;

    /**
     * 上传园区banner图
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> bannerImages = new ArrayList<>();

    /**
     * 上传房间小图
     */
    private String smallPic;

    /**
     * 房间简介（选填）
     */
    private String briefIntro;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date marketableTime;

    /**
     * 关联园区
     */
    @TableField(exist = false)
    private Park park;

    /**
     * 关联楼宇
     */
    @TableField(exist = false)
    private Building building;

    /**
     * 关联楼层
     */
    @TableField(exist = false)
    private BuildingDetail buildingDetail;

    @TableField(exist = false)
    private String buildingName;


}
