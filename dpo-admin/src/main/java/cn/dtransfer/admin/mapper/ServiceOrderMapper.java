package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ServiceOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务订单Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Mapper
public interface ServiceOrderMapper extends BaseMapper<ServiceOrder> {
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
     * @param
     * @return 服务订单集合
     */
    List<ServiceOrder> selectServiceOrderList(ServiceOrder serviceOrder);

    /**
     * 查询服务订单列表
     *
     * @param
     * @return 服务订单集合
     */
    List<ServiceOrder> selectMyServiceOrderAppList(ServiceOrder serviceOrder);

}
