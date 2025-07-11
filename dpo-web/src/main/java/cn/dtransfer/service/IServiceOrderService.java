package cn.dtransfer.service;

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
     * 新增服务订单
     *
     * @param serviceOrder 服务订单
     * @return 结果
     */
    int insertServiceOrder(ServiceOrder serviceOrder);


    /**
     * 查询服务订单列表
     *
     * @param serviceOrder 服务订单
     * @return 服务订单集合
     */
    List<ServiceOrder> selectMyServiceOrderAppList(ServiceOrder serviceOrder);
}
