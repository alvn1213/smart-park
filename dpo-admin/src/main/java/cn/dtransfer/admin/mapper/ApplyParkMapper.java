package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ApplyPark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 入园申请Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Mapper
public interface ApplyParkMapper extends BaseMapper<ApplyPark> {


    /**
     * 根据当前用户查询入园申请单
     * @param createUserId
     * @return
     */
    List<ApplyPark> selectApplyParkByUSerId(Long createUserId);

}
