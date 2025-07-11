package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IServiceBannerService;
import cn.dtransfer.service.IServiceMenuService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.ServiceBanner;
import cn.dtransfer.admin.domain.ServiceMenu;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 企业服务-菜单管理 (app 端)
 *
 * @author dtransfer
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/menu")
public class ServiceMenuAPIController extends BaseController {

    @Autowired
    private IServiceMenuService serviceMenuService;

    @Autowired
    private IServiceBannerService serviceBannerService;

    /**
     * 查询企业服务-菜单管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(ServiceMenu serviceMenu)
    {
        if (serviceMenu.getParkId() == null) {
            return R.error("请选择园区！!");
        }
        serviceMenu.setParentId(serviceMenu.getParentId() == null ? 0L : serviceMenu.getParentId());
        serviceMenu.setIsMarketable(true);
        List<ServiceMenu> serviceMenus = serviceMenuService.selectServiceMenuList(serviceMenu);
        List<Map> maps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(serviceMenus)) {
            for (ServiceMenu item : serviceMenus) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", item.getId());
                map.put("menuName", item.getMenuName());
                map.put("menuImg", item.getMenuImg());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

    /**
     * 查询企业服务banner的app列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("bannerList")
    public R bannerList(ServiceBanner serviceBanner)
    {
        if (serviceBanner.getParkId() == null) {
            return R.error("请选择园区！!");
        }
        List<Map<String, Object>> maps = Lists.newArrayList();
        serviceBanner.setIsMarketable(true);
        List<ServiceBanner> serviceBanners = serviceBannerService.selectServiceBannerList(serviceBanner);
        if (CollectionUtil.isNotEmpty(serviceBanners)) {
            for (ServiceBanner item : serviceBanners) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", item.getId());
                map.put("bannerName", item.getBannerName());
                map.put("bannerImg", item.getBannerImg());
                map.put("bannerDesc", item.getBannerDesc());
                map.put("marketableTime", item.getMarketableTime());
                map.put("uri", item.getUri());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

}
