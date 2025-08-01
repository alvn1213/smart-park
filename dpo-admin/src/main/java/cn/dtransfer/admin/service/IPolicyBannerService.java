package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.PolicyBanner;

import java.util.List;

/**
 * 政策bannerService接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IPolicyBannerService extends IService<PolicyBanner> {
    /**
     * 查询政策banner
     *
     * @param id 政策bannerID
     * @return 政策banner
     */
    PolicyBanner selectPolicyBannerById(Long id);

    /**
     * 查询政策banner列表
     *
     * @param policyBanner 政策banner
     * @return 政策banner集合
     */
    List<PolicyBanner> selectPolicyBannerList(PolicyBanner policyBanner);

    /**
     * 新增政策banner
     *
     * @param policyBanner 政策banner
     * @return 结果
     */
    int insertPolicyBanner(PolicyBanner policyBanner);

    /**
     * 修改政策banner
     *
     * @param policyBanner 政策banner
     * @return 结果
     */
    int updatePolicyBanner(PolicyBanner policyBanner);

    /**
     * 批量删除政策banner
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePolicyBannerByIds(String ids);

    /**
     * 删除政策banner信息
     *
     * @param id 政策bannerID
     * @return 结果
     */
    int deletePolicyBannerById(Long id);

}
