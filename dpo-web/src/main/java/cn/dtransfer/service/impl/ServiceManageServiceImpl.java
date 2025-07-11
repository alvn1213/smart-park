package cn.dtransfer.service.impl;

import cn.dtransfer.service.IServiceManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ServiceManage;
import cn.dtransfer.admin.mapper.ServiceManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Service("serviceManageAppService")
public class ServiceManageServiceImpl extends ServiceImpl<ServiceManageMapper, ServiceManage> implements IServiceManageService {
    @Autowired
    private ServiceManageMapper serviceManageMapper;

    /**
     * 查询服务管理
     *
     * @param id 服务管理ID
     * @return 服务管理
     */
    @Override
    public ServiceManage selectServiceManageById(Long id) {
        return serviceManageMapper.selectServiceManageById(id);
    }

    /**
     * 查询服务管理列表
     *
     * @param serviceManage 服务管理
     * @return 服务管理
     */
    @Override
    public List<ServiceManage> selectServiceManageList(ServiceManage serviceManage) {
        return serviceManageMapper.selectServiceManageList(serviceManage);
    }


}
