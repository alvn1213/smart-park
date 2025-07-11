package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Building;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 楼宇管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-24
 */
@Mapper
public interface BuildingMapper extends BaseMapper<Building> {


    /**
     * 查询楼宇
     * @param building
     * @return
     */
    List<Building> selectBuildingList(Building building);
}
