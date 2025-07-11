package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 企业服务-banner管理对象 dpo_service_banner
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Data
@TableName("dpo_service_banner")
public class ServiceBanner extends BaseEntity<ServiceBanner> {
    private static final long serialVersionUID = 1L;

    /**
     * banner名称
     */
    private String bannerName;

    /**
     * banner简介（可选）
     */
    private String bannerDesc;

    /**
     * 上传banner图标
     */
    private String bannerImg;

    /**
     * 落地页URI
     */
    private String uri;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    private Date marketableTime;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 点击数
     */
    private Long hits;


}
