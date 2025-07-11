package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IPolicyBannerService;
import cn.dtransfer.service.IPolicyService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Policy;
import cn.dtransfer.admin.domain.PolicyBanner;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 政策管理 (APP端)
 *
 * @author dtransfer
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/policy")
public class PolicyAPIController extends BaseController {

    @Autowired
    private IPolicyService policyService;

    @Autowired
    private IPolicyBannerService policyBannerService;

    /**
     * 查询政策管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Policy policy)
    {
        if (policy.getParkId() == null) {
            return R.error("请选择园区！！");
        }
        List<Policy> policies = policyService.selectPolicyList(policy);
        List<Map> maps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(policies)) {
            for (Policy item : policies) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", item.getId());
                map.put("name", item.getName());
                map.put("marketableTime", item.getMarketableTime());
                map.put("content", item.getContent());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

    /**
     * 查询政策banner的app列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("bannerList")
    public R bannerList(PolicyBanner policyBanner)
    {
        if (policyBanner.getParkId() == null) {
            return R.error("请选择园区！！");
        }
        List<PolicyBanner> policyBanners = policyBannerService.selectPolicyBannerList(policyBanner);
        List<Map<String, Object>> maps = Lists.newArrayList();
        if(CollectionUtil.isNotEmpty(policyBanners)){
            for (PolicyBanner item : policyBanners) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                map.put("bannerImg", item.getBannerImg());
                map.put("bannerDesc", item.getBannerDesc());
                map.put("marketableTime", item.getMarketableTime());
                map.put("uri", item.getUri()+item.getId());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

    /**
     * 点击计数
     * @param id
     * @return
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("addHits")
    public R addHits(Long id){
        PolicyBanner policyBanner = policyBannerService.selectPolicyBannerById(id);
        if (policyBanner != null) {
            policyBanner.setHits((policyBanner.getHits() == null ? 0 : policyBanner.getHits()) + 1);
        }
        return toAjax(policyBannerService.updatePolicyBanner(policyBanner));
    }
}
