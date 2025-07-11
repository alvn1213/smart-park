package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 服务管理对象 dpo_service_manage
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Data
@TableName("dpo_service_manage")
public class ServiceManage extends BaseEntity<ServiceManage> {
    private static final long serialVersionUID = 1L;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务简介（可选）
     */
    private String serviceDesc;

    /**
     * 供应商id
     */
    private Long supplierId;

    /**
     * 供应商名称 （搜索条件）
     */
    @Transient
    private String supplierName;

    /**
     * 菜单选择
     */
    private Long menuId;

    /**
     * 价格(元)
     */
    private Long price;

    /**
     * 价格单位
     */
    private String priceUnit;

    /**
     * 咨询电话（可选）
     */
    private String contract;

    /**
     * 咨询电话服务时段(开始)
     */
    @JsonFormat(pattern = "HH:mm")
    private Date contractTimeStart;

    /**
     * 咨询电话服务时段(结束)
     */
    @JsonFormat(pattern = "HH:mm")
    private Date contractTimeEnd;

    /**
     * 是否仅限工作日咨询
     */
    private Boolean isWorkDay;

    /**
     * 上传详情
     */
    private String serviceContent;

    /**
     * 服务协议(可选)
     */
    private Boolean agreementStatus;

    /**
     * 服务协议名称
     */
    private String agreementName;

    /**
     * 服务协议内容
     */
    private String agreementContent;

    /**
     * 一级菜单
     */
    private String levelOneMenu;

    /**
     * 二级菜单
     */
    private String levelTwoMenu;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date marketableTime;

    /**
     * 关联供应商
     */
    private ServiceSupplier serviceSupplier;

    /**
     * 关联菜单
     */
    private ServiceMenu serviceMenu;


}
