package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IServiceBannerService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
public class ServiceBannerServiceImpl extends ServiceImpl<ServiceBannerMapper, ServiceBanner> implements IServiceBannerService {
    @Autowired
    private ServiceBannerMapper serviceBannerMapper;

    /**
     * 查询企业服务-banner管理
     *
     * @param id 企业服务-banner管理ID
     * @return 企业服务-banner管理
     */
    @Override
    public ServiceBanner selectServiceBannerById(Long id) {
        return serviceBannerMapper.selectById(id);
    }

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
        return serviceBannerMapper.selectList(queryWrapper);
    }

    /**
     * 新增企业服务-banner管理
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 结果
     */
    @Override
    public int insertServiceBanner(ServiceBanner serviceBanner) {
        return serviceBannerMapper.insert(serviceBanner);
    }

    /**
     * 修改企业服务-banner管理
     *
     * @param serviceBanner 企业服务-banner管理
     * @return 结果
     */
    @Override
    public int updateServiceBanner(ServiceBanner serviceBanner) {
        return serviceBannerMapper.updateById(serviceBanner);
    }

    /**
     * 删除企业服务-banner管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceBannerByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return serviceBannerMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除企业服务-banner管理信息
     *
     * @param id 企业服务-banner管理ID
     * @return 结果
     */
    @Override
    public int deleteServiceBannerById(Long id) {
        return serviceBannerMapper.deleteById(id);
    }

}
