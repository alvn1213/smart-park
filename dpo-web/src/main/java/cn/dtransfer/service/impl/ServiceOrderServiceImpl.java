package cn.dtransfer.service.impl;

import cn.dtransfer.service.IServiceOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ServiceOrder;
import cn.dtransfer.admin.mapper.ServiceOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务订单Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Service("serviceOrderAppService")
public class ServiceOrderServiceImpl extends ServiceImpl<ServiceOrderMapper, ServiceOrder> implements IServiceOrderService {
    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    /**
     * 新增服务订单
     *
     * @param serviceOrder 服务订单
     * @return 结果
     */
    @Override
    public int insertServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderMapper.insert(serviceOrder);
    }


    @Override
    public List<ServiceOrder> selectMyServiceOrderAppList(ServiceOrder serviceOrder) {
        return serviceOrderMapper.selectMyServiceOrderAppList(serviceOrder);
    }


}
