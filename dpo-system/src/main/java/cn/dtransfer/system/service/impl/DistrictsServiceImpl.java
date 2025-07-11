package cn.dtransfer.system.service.impl;

import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.system.domain.vo.DistrictsVO;
import cn.dtransfer.system.mapper.DistrictsMapper;
import cn.dtransfer.system.service.IDistrictsService;
import cn.dtransfer.system.domain.Districts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区 服务层实现
 *
 */
@Service
public class DistrictsServiceImpl implements IDistrictsService {

    @Autowired
    private DistrictsMapper districtsMapper;

    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
    public Districts selectDistrictsById(Integer id) {
        return districtsMapper.selectDistrictsById(id);
    }

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    @Override
    public List<Districts> selectDistrictsList(Districts districts) {
        return districtsMapper.selectDistrictsList(districts);
    }

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int insertDistricts(Districts districts) {
        return districtsMapper.insertDistricts(districts);
    }

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int updateDistricts(Districts districts) {
        return districtsMapper.updateDistricts(districts);
    }

    /**
     * 删除地区对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDistrictsByIds(String ids) {
        return districtsMapper.deleteDistrictsByIds(Convert.toStrArray(ids));
    }

    /**
     * 地区树形
     *
     * @return
     */
    @Override
    public List<DistrictsVO> getDistrictsTree() {
        List<DistrictsVO> areaList = districtsMapper.selectDistrictsVOList();
        List<DistrictsVO> nodeList = new ArrayList<>();
        for (DistrictsVO node1 : areaList) {
            boolean mark = false;
            for (DistrictsVO node2 : areaList) {
                if (node1.getParentId() > 0L && node1.getParentId() == node2.getId()) {
                    mark = true;
                    if (node2.getChildren() == null) {
                        node2.setChildren(new ArrayList<>());
                    }
                    node2.getChildren().add(node1);
                    break;
                }
            }
            if (!mark) {
                nodeList.add(node1);
            }
        }
        return nodeList;
    }

}
