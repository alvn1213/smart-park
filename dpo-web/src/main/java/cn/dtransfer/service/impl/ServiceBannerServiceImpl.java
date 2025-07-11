package cn.dtransfer.service.impl;

import cn.dtransfer.service.IServiceBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ServiceBanner;
import cn.dtransfer.admin.mapper.ServiceBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业服务-banner管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service("serviceBannerAppService")
public class ServiceBannerServiceImpl extends ServiceImpl<ServiceBannerMapper, ServiceBanner> implements IServiceBannerService {
    @Autowired
    private ServiceBannerMapper serviceBannerMapper;



    /**
     * 查询企业服务-banner管理列表
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 企业服务-banner管理
     */
    @Override
    public List<ServiceBanner> selectServiceBannerList(ServiceBanner serviceBanner) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (serviceBanner.getBannerName() != null) {
            queryWrapper.like("banner_name", serviceBanner.getBannerName());
        }
        queryWrapper.eq("park_id", serviceBanner.getParkId());
        return serviceBannerMapper.selectList(queryWrapper);
    }


}
