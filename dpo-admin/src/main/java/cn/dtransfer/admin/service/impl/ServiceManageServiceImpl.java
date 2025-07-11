package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IServiceManageService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
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

    /**
     * 新增服务管理
     *
     * @param serviceManage 服务管理
     * @return 结果
     */
    @Override
    public int insertServiceManage(ServiceManage serviceManage) {
        return serviceManageMapper.insert(serviceManage);
    }

    /**
     * 修改服务管理
     *
     * @param serviceManage 服务管理
     * @return 结果
     */
    @Override
    public int updateServiceManage(ServiceManage serviceManage) {
        return serviceManageMapper.updateById(serviceManage);
    }

    /**
     * 删除服务管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceManageByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return serviceManageMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除服务管理信息
     *
     * @param id 服务管理ID
     * @return 结果
     */
    @Override
    public int deleteServiceManageById(Long id) {
        return serviceManageMapper.deleteById(id);
    }
}
