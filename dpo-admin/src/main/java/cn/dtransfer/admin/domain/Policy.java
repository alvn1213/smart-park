package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 政策管理对象 dpo_policy
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_policy")
public class Policy extends BaseEntity<Policy> {
    private static final long serialVersionUID = 1L;

    /**
     * 政策名称
     */
    private String name;

    /**
     * 上传详情
     */
    private String content;

    /**
     * 是否上架
     */
    private Boolean isMarketable;

    /**
     * 上架时间
     */
    private Date marketableTime;

}
