package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IPolicyBannerService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
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
        if (policyBanner.getIsMarketable() != null) {
            queryWrapper.eq("is_marketable", policyBanner.getIsMarketable());
        }
        return policyBannerMapper.selectList(queryWrapper);
    }

    /**
     * 新增政策banner
     *
     * @param policyBanner 政策banner
     * @return 结果
     */
    @Override
    public int insertPolicyBanner(PolicyBanner policyBanner) {
        policyBanner.setIsMarketable(false);
        return policyBannerMapper.insert(policyBanner);
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

    /**
     * 删除政策banner对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePolicyBannerByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return policyBannerMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除政策banner信息
     *
     * @param id 政策bannerID
     * @return 结果
     */
    @Override
    public int deletePolicyBannerById(Long id) {
        return policyBannerMapper.deleteById(id);
    }


}
