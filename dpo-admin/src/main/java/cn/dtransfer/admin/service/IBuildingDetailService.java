package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.BuildingDetail;

import java.util.List;

/**
 * 楼层管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
public interface IBuildingDetailService extends IService<BuildingDetail> {
    /**
     * 查询楼层管理
     *
     * @param id 楼层管理ID
     * @return 楼层管理
     */
    BuildingDetail selectBuildingDetailById(Long id);

    /**
     * 查询楼层管理列表
     *
     * @param buildingDetail 楼层管理
     * @return 楼层管理集合
     */
    List<BuildingDetail> selectBuildingDetailList(BuildingDetail buildingDetail);

    /**
     * 新增楼层管理
     *
     * @param buildingDetail 楼层管理
     * @return 结果
     */
    int insertBuildingDetail(BuildingDetail buildingDetail);

    /**
     * 修改楼层管理
     *
     * @param buildingDetail 楼层管理
     * @return 结果
     */
    int updateBuildingDetail(BuildingDetail buildingDetail);

    /**
     * 批量删除楼层管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBuildingDetailByIds(String ids);


}
