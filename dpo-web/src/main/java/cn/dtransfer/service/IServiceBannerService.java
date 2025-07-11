package cn.dtransfer.service;

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
     * 查询企业服务-banner管理列表
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 企业服务-banner管理集合
     */
    List<ServiceBanner> selectServiceBannerList(ServiceBanner serviceBanner);


}
