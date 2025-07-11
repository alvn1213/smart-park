package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Park;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 园区管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Mapper
public interface ParkMapper extends BaseMapper<Park> {

    /**
     * 查询园区列表
     * @param park
     * @return
     */
    List<Park> selectParkList(Park park);


    /**
     * 运营用户的园区显示
     * @return
     */
    Park selectByDeptId(Park park);


    /**
     * 查询园区列表
     * @param park
     * @return
     */
    List<Park> selectAllParkList(Park park);

}
