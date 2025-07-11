package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户管理对象 dpo_customer
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Data
@TableName("dpo_customer")
public class Customer extends BaseEntity<Customer> {
    private static final long serialVersionUID = 1L;


    /**
     * 最大Banner图片数量
     */
    public static final int MAX_BANNER_IMAGE_SIZE = 6;

    /**
     * 客户名称
     */
    @NotBlank(message = "客户名称不能为空")
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 企业头像
     */
    private String headImg;

    /**
     * 照片墙
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> bannerImages = new ArrayList<>();

    /**
     * 客户类型
     */
    private Type type;


    /**
     * 客户类型
     */
    public enum Type implements IEnum<Integer> {

        /**
         * 公司
         */
        COMPANY("公司", 0),

        /**
         * 个人
         */
        PERSON("个人", 1);

        private String name;
        private int    value;

        Type(String name, int value) {
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

        public static Type parse(Integer value) {
            for (Type type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * 客户状态
     */
    private CustomerStatus customerStatus;


    /**
     * 客户类型
     */
    public enum CustomerStatus implements IEnum<Integer> {

        /**
         * 潜在客户
         */
        POTENTIAL("潜在客户", 0),

        /**
         * 意向客户
         */
        PURPOSE("意向客户", 1),

        /**
         * 成交客户
         */
        DEAL("成交客户", 2),


        /**
         * 流失客户
         */
        LOSE("流失客户", 3);

        private String name;
        private int    value;

        CustomerStatus(String name, int value) {
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

        public static CustomerStatus parse(Integer value) {
            for (CustomerStatus customerStatus : values()) {
                if (customerStatus.getValue().equals(value)) {
                    return customerStatus;
                }
            }
            return null;
        }
    }

    /**
     * 所属行业
     */
    private String sector;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 过程管理
     */
    private Process process;

    /**
     * 过程管理
     */
    public enum Process implements IEnum<Integer> {


        /**
         * 初次拜访
         */
        FIRST_VISIT("初次拜访", 0),

        /**
         * 需求引导分析
         */
        NEED_GUID("需求引导分析", 1),

        /**
         * 竞争阶段
         */
        COMPETE("竞争阶段", 2),

        /**
         * 预定阶段
         */
        PREDETERMINE("预定阶段", 3),

        /**
         * 合同阶段
         */
        CONTRACT("合同阶段", 4);

        private String name;
        private int    value;

        Process(String name, int value) {
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

        public static Process parse(Integer value) {
            for (Process process : values()) {
                if (process.getValue().equals(value)) {
                    return process;
                }
            }
            return null;
        }
    }

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 统一信用代码
     */
    private String creditNo;

    /**
     * 通讯地址
     */
    private String mailAddress;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 渠道分类
     */
    private ChannelType channelType;

    /**
     * 渠道分类
     */
    public enum ChannelType implements IEnum<Integer> {


        /**
         * 中介公司
         */
        AGENT("中介公司", 0),

        /**
         * 广告
         */
        AD("广告", 1),

        /**
         * 自开发渠道
         */
        MY_CHANNEL("自开发渠道", 2),


        /**
         * 合作推荐
         */
        COOPERATE("合作推荐", 3);

        private String name;
        private int    value;

        ChannelType(String name, int value) {
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

        public static ChannelType parse(Integer value) {
            for (ChannelType channelType : values()) {
                if (channelType.getValue().equals(value)) {
                    return channelType;
                }
            }
            return null;
        }
    }

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 是否黑名单(0-否，1-是)
     */
    private Boolean isBlacklist;

    /**
     * 客户备注
     */
    private String remark;

    /**
     * 工商注册号
     */
    private String regNo;

    /**
     * 经营状态
     */
    private String status;

    /**
     * 组织机构代码
     */
    private String orgNo;

    /**
     * 法人名
     */
    private String operName;

    /**
     * 公司类型(企业性质)
     */
    private String econKind;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 登记机关
     */
    @NotBlank(message = "登记机关不能为空")
    private String belongOrg;

    /**
     * 注册资金
     */
    private String registCapi;

    /**
     * 注销日期
     */
    private String endDate;

    /**
     * 注册地址
     */
    @NotBlank(message = "注册地址不能为空")
    private String address;

    /**
     * 经营范围
     */
    private String scope;

    /**
     * 关联园区
     */
    private Park park;

}
