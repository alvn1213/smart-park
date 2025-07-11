package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IDjBannerService;
import cn.dtransfer.service.IDjService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import cn.dtransfer.admin.domain.Dj;
import cn.dtransfer.admin.domain.DjBanner;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 党建banner APP端
 *
 * @author dtransfer
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/dj")
public class DjAPIController extends BaseController {


    @Autowired
    private IDjBannerService djBannerService;

    @Autowired
    private IDjService djService;



    /**
     * 查询党建管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Dj dj)
    {
        if (dj.getParkId() == null) {
            return R.error("园区ID不勇为空！");
        }
        List<Dj> djs = djService.selectDjList(dj);
        List<Map> maps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(djs)) {
            for (Dj item : djs) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", item.getId());
                map.put("djName", item.getDjName());
                map.put("marketableTime", item.getMarketableTime());
                map.put("content", item.getContent());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

    /**
     * 查询党建banner的app列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("bannerList")
    public R bannerList(DjBanner djBanner)
    {
        djBanner.setIsMarketable(true);
        List<DjBanner> djBanners = djBannerService.selectDjBannerList(djBanner);
        List<Map<String, Object>> maps = Lists.newArrayList();
        if(CollectionUtil.isNotEmpty(djBanners)){
            for (DjBanner item : djBanners) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", item.getId());
                map.put("bannerName", item.getBannerName());
                map.put("bannerImg", item.getBannerImg());
                map.put("bannerDesc", item.getBannerDesc());
                map.put("marketableTime", item.getMarketableTime());
                map.put("uri", item.getUrl()+item.getId());
                maps.add(map);
            }
        }
        return R.data(maps);
    }

    /**
     *点击计算
     * @param id
     * @return
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("addHits")
    public R addHits(Long id){
        DjBanner djBanner = djBannerService.selectDjBannerById(id);
        if (djBanner != null) {
            djBanner.setHits((djBanner.getHits() == null ? 0 : djBanner.getHits()) + 1);
        }
        return toAjax(djBannerService.updateDjBanner(djBanner));
    }
}
