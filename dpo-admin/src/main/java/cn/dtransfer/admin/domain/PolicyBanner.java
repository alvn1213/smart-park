package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 政策banner对象 dpo_policy_banner
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_policy_banner")
public class PolicyBanner extends BaseEntity<PolicyBanner> {
    private static final long serialVersionUID = 1L;

    /**
     * banner名称
     */
    private String name;

    /**
     * banner简介（可选）
     */
    private String bannerDesc;

    /**
     * banner图路径
     */
    private String bannerImg;

    /**
     * 点击次数
     */
    private Long hits;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    private Date marketableTime;

    /**
     * 落地页URI
     */
    private String uri;
}
