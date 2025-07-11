package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ServiceBanner;

import java.util.List;

/**
 * 企业服务-banner管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IServiceBannerService extends IService<ServiceBanner> {
    /**
     * 查询企业服务-banner管理
     *
     * @param id 企业服务-banner管理ID
     * @return 企业服务-banner管理
     */
    ServiceBanner selectServiceBannerById(Long id);

    /**
     * 查询企业服务-banner管理列表
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 企业服务-banner管理集合
     */
    List<ServiceBanner> selectServiceBannerList(ServiceBanner serviceBanner);

    /**
     * 新增企业服务-banner管理
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 结果
     */
    int insertServiceBanner(ServiceBanner serviceBanner);

    /**
     * 修改企业服务-banner管理
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 结果
     */
    int updateServiceBanner(ServiceBanner serviceBanner);

    /**
     * 批量删除企业服务-banner管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteServiceBannerByIds(String ids);

    /**
     * 删除企业服务-banner管理信息
     *
     * @param id 企业服务-banner管理ID
     * @return 结果
     */
    int deleteServiceBannerById(Long id);


}
