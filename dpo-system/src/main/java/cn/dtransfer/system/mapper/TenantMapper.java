package cn.dtransfer.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.dtransfer.system.domain.Tenant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-02
 */
@Mapper
public interface TenantMapper extends BaseMapper<Tenant> {
    /**
     * 查询租户管理
     *
     * @param id 租户管理ID
     * @return 租户管理
     */
    Tenant selectTenantById(Long id);

    /**
     * 查询租户管理列表
     *
     * @param tenant 租户管理
     * @return 租户管理集合
     */
    List<Tenant> selectTenantList(Tenant tenant);

}
