package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IBuildingService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Building;
import cn.dtransfer.admin.mapper.BuildingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼宇管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements IBuildingService {
    @Autowired
    private BuildingMapper buildingMapper;

    /**
     * 查询楼宇管理
     *
     * @param id 楼宇管理ID
     * @return 楼宇管理
     */
    @Override
    public Building selectBuildingById(Long id) {
        return buildingMapper.selectById(id);
    }

    /**
     * 查询楼宇管理列表
     *
     * @param building 楼宇管理
     * @return 楼宇管理
     */
    @Override
    public List<Building> selectBuildingList(Building building) {
        return buildingMapper.selectBuildingList(building);
    }

    /**
     * 新增楼宇管理
     *
     * @param building 楼宇管理
     * @return 结果
     */
    @Override
    public int insertBuilding(Building building) {
        return buildingMapper.insert(building);
    }

    /**
     * 修改楼宇管理
     *
     * @param building 楼宇管理
     * @return 结果
     */
    @Override
    public int updateBuilding(Building building) {
        return buildingMapper.updateById(building);
    }

    /**
     * 删除楼宇管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBuildingByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return buildingMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }


}
