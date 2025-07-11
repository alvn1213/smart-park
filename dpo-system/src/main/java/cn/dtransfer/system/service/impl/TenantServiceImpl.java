package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.mapper.TenantMapper;
import cn.dtransfer.system.service.ITenantService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.system.domain.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 租户管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-04-02
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {
    @Autowired
    private TenantMapper tenantMapper;


    /**
     * 查询租户管理
     *
     * @param id 租户管理ID
     * @return 租户管理
     */
    @Override
    public Tenant selectTenantById(Long id) {
        return tenantMapper.selectById(id);
    }

    /**
     * 查询租户管理列表
     *
     * @param tenant 租户管理
     * @return 租户管理
     */
    @Override
    public List<Tenant> selectTenantList(Tenant tenant) {
        return tenantMapper.selectTenantList(tenant);
    }

    /**
     * 新增租户管理
     *
     * @param tenant 租户管理
     * @return 结果
     */
    @Override
    public int insertTenant(Tenant tenant) {
        return tenantMapper.insert(tenant);
    }

    /**
     * 修改租户管理
     *
     * @param tenant 租户管理
     * @return 结果
     */
    @Override
    public int updateTenant(Tenant tenant) {
        return tenantMapper.updateById(tenant);
    }

    /**
     * 删除租户管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTenantByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return tenantMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除租户管理信息
     *
     * @param id 租户管理ID
     * @return 结果
     */
    @Override
    public int deleteTenantById(Long id) {
        return tenantMapper.deleteById(id);
    }
}
