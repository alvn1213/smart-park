package cn.dtransfer.admin.service;

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

    /**
     * 新增服务管理
     *
     * @param serviceManage 服务管理
     * @return 结果
     */
    int insertServiceManage(ServiceManage serviceManage);

    /**
     * 修改服务管理
     *
     * @param serviceManage 服务管理
     * @return 结果
     */
    int updateServiceManage(ServiceManage serviceManage);

    /**
     * 批量删除服务管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteServiceManageByIds(String ids);

    /**
     * 删除服务管理信息
     *
     * @param id 服务管理ID
     * @return 结果
     */
    int deleteServiceManageById(Long id);
}
