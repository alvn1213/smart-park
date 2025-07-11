package cn.dtransfer.system.domain;

import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 参数配置表 sys_config
 *
 */
@Data
@Table(name = "sys_config")
public class Config extends BaseEntity<BaseEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 缓存名称
     */
    public static final String CACHE_NAME = "config_key";

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    @Lob
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    /**
     * 备注
     */
    private String remark;

}
