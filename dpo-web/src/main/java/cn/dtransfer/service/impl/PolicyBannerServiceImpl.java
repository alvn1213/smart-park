package cn.dtransfer.service.impl;

import cn.dtransfer.service.IPolicyBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.PolicyBanner;
import cn.dtransfer.admin.mapper.PolicyBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 政策bannerService业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service("policyBannerAppService")
public class PolicyBannerServiceImpl extends ServiceImpl<PolicyBannerMapper, PolicyBanner> implements IPolicyBannerService {
    @Autowired
    private PolicyBannerMapper policyBannerMapper;

    /**
     * 查询政策banner
     *
     * @param id 政策bannerID
     * @return 政策banner
     */
    @Override
    public PolicyBanner selectPolicyBannerById(Long id) {
        return policyBannerMapper.selectById(id);
    }

    /**
     * 查询政策banner列表
     *
     * @param policyBanner 政策banner
     * @return 政策banner
     */
    @Override
    public List<PolicyBanner> selectPolicyBannerList(PolicyBanner policyBanner) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_marketable", true);
        queryWrapper.eq("park_id", policyBanner.getParkId());
        return policyBannerMapper.selectList(queryWrapper);
    }


    /**
     * 修改政策banner
     *
     * @param policyBanner 政策banner
     * @return 结果
     */
    @Override
    public int updatePolicyBanner(PolicyBanner policyBanner) {
        return policyBannerMapper.updateById(policyBanner);
    }



}
