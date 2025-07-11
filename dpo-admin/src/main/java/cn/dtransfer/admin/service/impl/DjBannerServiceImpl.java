package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IDjBannerService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
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
        return djBannerMapper.selectList(queryWrapper);
    }

    /**
     * 新增党建banner
     *
     * @param djBanner 党建banner
     * @return 结果
     */
    @Override
    public int insertDjBanner(DjBanner djBanner) {
        djBanner.setIsMarketable(false);
        return djBannerMapper.insert(djBanner);
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

    /**
     * 删除党建banner对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDjBannerByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return djBannerMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除党建banner信息
     *
     * @param id 党建bannerID
     * @return 结果
     */
    @Override
    public int deleteDjBannerById(Long id) {
        return djBannerMapper.deleteById(id);
    }
}
