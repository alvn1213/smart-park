package cn.dtransfer.admin.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 党建管理对象 dpo_dj
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Data
@TableName("dpo_dj")
public class Dj extends BaseEntity<Dj> {
    private static final long serialVersionUID = 1L;

    /**
     * 党建名称
     */
    private String djName;

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
