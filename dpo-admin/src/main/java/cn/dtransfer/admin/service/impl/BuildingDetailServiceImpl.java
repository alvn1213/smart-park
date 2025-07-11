package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IBuildingDetailService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.BuildingDetail;
import cn.dtransfer.admin.mapper.BuildingDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼层管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Service
public class BuildingDetailServiceImpl extends ServiceImpl<BuildingDetailMapper, BuildingDetail> implements IBuildingDetailService {
    @Autowired
    private BuildingDetailMapper buildingDetailMapper;

    /**
     * 查询楼层管理
     *
     * @param id 楼层管理ID
     * @return 楼层管理
     */
    @Override
    public BuildingDetail selectBuildingDetailById(Long id) {
        return buildingDetailMapper.selectById(id);
    }

    /**
     * 查询楼层管理列表
     *
     * @param buildingDetail 楼层管理
     * @return 楼层管理
     */
    @Override
    public List<BuildingDetail> selectBuildingDetailList(BuildingDetail buildingDetail) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("building_id", buildingDetail.getBuildingId());
        return buildingDetailMapper.selectList(queryWrapper);
    }

    /**
     * 新增楼层管理
     *
     * @param buildingDetail 楼层管理
     * @return 结果
     */
    @Override
    public int insertBuildingDetail(BuildingDetail buildingDetail) {
        return buildingDetailMapper.insert(buildingDetail);
    }

    /**
     * 修改楼层管理
     *
     * @param buildingDetail 楼层管理
     * @return 结果
     */
    @Override
    public int updateBuildingDetail(BuildingDetail buildingDetail) {
        return buildingDetailMapper.updateById(buildingDetail);
    }

    /**
     * 删除楼层管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuildingDetailByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return buildingDetailMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }


}
