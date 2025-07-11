package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.admin.handler.BannerImageHandler;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 供应商管理对象 dpo_service_supplier
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Data
@TableName("dpo_service_supplier")
public class ServiceSupplier extends BaseEntity<ServiceSupplier> {
    private static final long serialVersionUID = 1L;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商简介（选填）
     */
    private String supplierDesc;

    /**
     * 上传供应商图标
     */
    private String supplierImg;

    /**
     * 供应商地址
     */
    private String supplierAddress;

    /**
     * 供应商行业（选填）
     */
    private String supplierIndustry;

    /**
     * 上传详情
     */
    private String supplierContent;

    /**
     * 上传供应商资质（选填）
     */
    @TableField(typeHandler = BannerImageHandler.class)
    private List<BannerImage> qualificationImg = new ArrayList<>();

    /**
     * 服务数
     */
    private Long serviceNum;

    /**
     * 申请次数
     */
    private Long signNum;

}
