package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 党建banner对象 dpo_dj_banner
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_dj_banner")
public class DjBanner extends BaseEntity<DjBanner> {
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
     * $column.columnComment
     */
    private String url;

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
     * 是否置顶
     */
    private Long isTop;

}
