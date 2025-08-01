package cn.dtransfer.system.service;

import cn.dtransfer.system.domain.vo.DistrictsVO;
import cn.dtransfer.system.domain.Districts;

import java.util.List;

/**
 * 地区 服务层
 *
 */
public interface IDistrictsService {

    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    Districts selectDistrictsById(Integer id);

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    List<Districts> selectDistrictsList(Districts districts);

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    int insertDistricts(Districts districts);

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    int updateDistricts(Districts districts);

    /**
     * 删除地区信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteDistrictsByIds(String ids);

    /**
     * 地区树形
     *
     * @return
     */
    List<DistrictsVO> getDistrictsTree();

}
