package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IServiceOrderService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
public class ServiceOrderServiceImpl extends ServiceImpl<ServiceOrderMapper, ServiceOrder> implements IServiceOrderService {
    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    /**
     * 查询服务订单
     *
     * @param id 服务订单ID
     * @return 服务订单
     */
    @Override
    public ServiceOrder selectServiceOrderById(Long id) {
        return serviceOrderMapper.selectServiceOrderById(id);
    }

    /**
     * 查询服务订单列表
     *
     * @param serviceOrder 服务订单
     * @return 服务订单
     */
    @Override
    public List<ServiceOrder> selectServiceOrderList(ServiceOrder serviceOrder) {
        return serviceOrderMapper.selectServiceOrderList(serviceOrder);
    }

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

    /**
     * 修改服务订单
     *
     * @param serviceOrder 服务订单
     * @return 结果
     */
    @Override
    public int updateServiceOrder(ServiceOrder serviceOrder) {
        return serviceOrderMapper.updateById(serviceOrder);
    }

    /**
     * 删除服务订单对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceOrderByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return serviceOrderMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除服务订单信息
     *
     * @param id 服务订单ID
     * @return 结果
     */
    @Override
    public int deleteServiceOrderById(Long id) {
        return serviceOrderMapper.deleteById(id);
    }


    @Override
    public List<ServiceOrder> selectMyServiceOrderAppList(ServiceOrder serviceOrder) {
        return serviceOrderMapper.selectMyServiceOrderAppList(serviceOrder);
    }



}
