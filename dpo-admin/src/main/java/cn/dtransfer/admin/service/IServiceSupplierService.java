package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ServiceSupplier;

import java.util.List;

/**
 * 供应商管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
public interface IServiceSupplierService extends IService<ServiceSupplier> {
    /**
     * 查询供应商管理
     *
     * @param id 供应商管理ID
     * @return 供应商管理
     */
    ServiceSupplier selectServiceSupplierById(Long id);

    /**
     * 查询供应商管理列表
     *
     * @param serviceSupplier 供应商管理
     * @return 供应商管理集合
     */
    List<ServiceSupplier> selectServiceSupplierList(ServiceSupplier serviceSupplier);

    /**
     * 新增供应商管理
     *
     * @param serviceSupplier 供应商管理
     * @return 结果
     */
    int insertServiceSupplier(ServiceSupplier serviceSupplier);

    /**
     * 修改供应商管理
     *
     * @param serviceSupplier 供应商管理
     * @return 结果
     */
    int updateServiceSupplier(ServiceSupplier serviceSupplier);

    /**
     * 批量删除供应商管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteServiceSupplierByIds(String ids);

    /**
     * 删除供应商管理信息
     *
     * @param id 供应商管理ID
     * @return 结果
     */
    int deleteServiceSupplierById(Long id);
}
