package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 服务订单对象 dpo_service_order
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Data
@TableName("dpo_service_order")
public class ServiceOrder extends BaseEntity<ServiceOrder> {
    private static final long serialVersionUID = 1L;

    /**
     * 申请人
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 申请人
     */
    private Long memberId;

    /**
     * 供应商Id
     */
    private Long supplierId;

    /**
     * 服务ID
     */
    private Long serviceId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 跟进备注
     */
    private String remarkList;

    /**
     * 供应商名称 （搜索条件）
     */
    @Transient
    private String supplierName;

    /**
     * 服务名称 （搜索条件）
     */
    @Transient
    private String serviceName;

    private ServiceManage serviceManage;

    private ServiceSupplier serviceSupplier;

}
