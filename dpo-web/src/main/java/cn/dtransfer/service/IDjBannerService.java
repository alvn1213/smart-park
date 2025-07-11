package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.DjBanner;

import java.util.List;

/**
 * 党建bannerService接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IDjBannerService extends IService<DjBanner> {
    /**
     * 查询党建banner
     *
     * @param id 党建bannerID
     * @return 党建banner
     */
    DjBanner selectDjBannerById(Long id);

    /**
     * 查询党建banner列表
     *
     * @param djBanner 党建banner
     * @return 党建banner集合
     */
    List<DjBanner> selectDjBannerList(DjBanner djBanner);



    /**
     * 修改党建banner
     *
     * @param djBanner 党建banner
     * @return 结果
     */
    int updateDjBanner(DjBanner djBanner);

}
