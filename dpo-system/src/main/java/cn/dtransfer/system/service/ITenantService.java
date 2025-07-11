package cn.dtransfer.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.system.domain.Tenant;

import java.util.List;

/**
 * 租户管理Service接口
 *
 * @author dtransfer
 * @date 2024-04-02
 */
public interface ITenantService extends IService<Tenant> {
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

    /**
     * 新增租户管理
     *
     * @param tenant 租户管理
     * @return 结果
     */
    int insertTenant(Tenant tenant);

    /**
     * 修改租户管理
     *
     * @param tenant 租户管理
     * @return 结果
     */
    int updateTenant(Tenant tenant);

    /**
     * 批量删除租户管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTenantByIds(String ids);

    /**
     * 删除租户管理信息
     *
     * @param id 租户管理ID
     * @return 结果
     */
    int deleteTenantById(Long id);
}
