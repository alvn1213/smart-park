package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ServiceOrder;

import java.util.List;

/**
 * 服务订单Service接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
public interface IServiceOrderService extends IService<ServiceOrder> {
    /**
     * 查询服务订单
     *
     * @param id 服务订单ID
     * @return 服务订单
     */
    ServiceOrder selectServiceOrderById(Long id);

    /**
     * 查询服务订单列表
     *
     * @param serviceOrder 服务订单
     * @return 服务订单集合
     */
    List<ServiceOrder> selectServiceOrderList(ServiceOrder serviceOrder);

    /**
     * 新增服务订单
     *
     * @param serviceOrder 服务订单
     * @return 结果
     */
    int insertServiceOrder(ServiceOrder serviceOrder);

    /**
     * 修改服务订单
     *
     * @param serviceOrder 服务订单
     * @return 结果
     */
    int updateServiceOrder(ServiceOrder serviceOrder);

    /**
     * 批量删除服务订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteServiceOrderByIds(String ids);

    /**
     * 删除服务订单信息
     *
     * @param id 服务订单ID
     * @return 结果
     */
    int deleteServiceOrderById(Long id);

    /**
     * 查询服务订单列表
     *
     * @param serviceOrder 服务订单
     * @return 服务订单集合
     */
    List<ServiceOrder> selectMyServiceOrderAppList(ServiceOrder serviceOrder);
}
