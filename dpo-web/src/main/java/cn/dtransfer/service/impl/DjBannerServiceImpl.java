package cn.dtransfer.service.impl;

import cn.dtransfer.service.IDjBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.DjBanner;
import cn.dtransfer.admin.mapper.DjBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 党建bannerService业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service("djBannerAppService")
public class DjBannerServiceImpl extends ServiceImpl<DjBannerMapper, DjBanner> implements IDjBannerService {
    @Autowired
    private DjBannerMapper djBannerMapper;

    /**
     * 查询党建banner
     *
     * @param id 党建bannerID
     * @return 党建banner
     */
    @Override
    public DjBanner selectDjBannerById(Long id) {
        return djBannerMapper.selectById(id);
    }

    /**
     * 查询党建banner列表
     *
     * @param djBanner 党建banner
     * @return 党建banner
     */
    @Override
    public List<DjBanner> selectDjBannerList(DjBanner djBanner) {
        QueryWrapper<DjBanner> queryWrapper = new QueryWrapper();
        queryWrapper.eq("park_id", djBanner.getParkId());
        queryWrapper.eq("is_marketable", true);
        return djBannerMapper.selectList(queryWrapper);
    }


    /**
     * 修改党建banner
     *
     * @param djBanner 党建banner
     * @return 结果
     */
    @Override
    public int updateDjBanner(DjBanner djBanner) {
        return djBannerMapper.updateById(djBanner);
    }


}
