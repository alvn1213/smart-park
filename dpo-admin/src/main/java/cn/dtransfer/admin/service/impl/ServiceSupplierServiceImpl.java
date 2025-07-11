package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IServiceSupplierService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ServiceSupplier;
import cn.dtransfer.admin.mapper.ServiceSupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 供应商管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Service
public class ServiceSupplierServiceImpl extends ServiceImpl<ServiceSupplierMapper, ServiceSupplier> implements IServiceSupplierService {
    @Autowired
    private ServiceSupplierMapper serviceSupplierMapper;

    /**
     * 查询供应商管理
     *
     * @param id 供应商管理ID
     * @return 供应商管理
     */
    @Override
    public ServiceSupplier selectServiceSupplierById(Long id) {
        return serviceSupplierMapper.selectServiceSupplierById(id);
    }

    /**
     * 查询供应商管理列表
     *
     * @param serviceSupplier 供应商管理
     * @return 供应商管理
     */
    @Override
    public List<ServiceSupplier> selectServiceSupplierList(ServiceSupplier serviceSupplier) {
        QueryWrapper<ServiceSupplier> queryWrapper = new QueryWrapper();
        if (serviceSupplier.getSupplierName() != null) {
            queryWrapper.like("supplier_name", serviceSupplier.getSupplierName());
        }
        queryWrapper.orderByDesc("create_time");
        return serviceSupplierMapper.selectList(queryWrapper);
    }

    /**
     * 新增供应商管理
     *
     * @param serviceSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int insertServiceSupplier(ServiceSupplier serviceSupplier) {
        return serviceSupplierMapper.insert(serviceSupplier);
    }

    /**
     * 修改供应商管理
     *
     * @param serviceSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int updateServiceSupplier(ServiceSupplier serviceSupplier) {
        return serviceSupplierMapper.updateById(serviceSupplier);
    }

    /**
     * 删除供应商管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceSupplierByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return serviceSupplierMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除供应商管理信息
     *
     * @param id 供应商管理ID
     * @return 结果
     */
    @Override
    public int deleteServiceSupplierById(Long id) {
        return serviceSupplierMapper.deleteById(id);
    }
}
