package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Building;

import java.util.List;

/**
 * 楼宇管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
public interface IBuildingService extends IService<Building> {
    /**
     * 查询楼宇管理
     *
     * @param id 楼宇管理ID
     * @return 楼宇管理
     */
    Building selectBuildingById(Long id);

    /**
     * 查询楼宇管理列表
     *
     * @param building 楼宇管理
     * @return 楼宇管理集合
     */
    List<Building> selectBuildingList(Building building);

    /**
     * 新增楼宇管理
     *
     * @param building 楼宇管理
     * @return 结果
     */
    int insertBuilding(Building building);

    /**
     * 修改楼宇管理
     *
     * @param building 楼宇管理
     * @return 结果
     */
    int updateBuilding(Building building);

    /**
     * 批量删除楼宇管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuildingByIds(String ids);


}
