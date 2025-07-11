package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ServiceManage;

import java.util.List;

/**
 * 服务管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
public interface IServiceManageService extends IService<ServiceManage> {
    /**
     * 查询服务管理
     *
     * @param id 服务管理ID
     * @return 服务管理
     */
    ServiceManage selectServiceManageById(Long id);

    /**
     * 查询服务管理列表
     *
     * @param serviceManage 服务管理
     * @return 服务管理集合
     */
    List<ServiceManage> selectServiceManageList(ServiceManage serviceManage);

}
