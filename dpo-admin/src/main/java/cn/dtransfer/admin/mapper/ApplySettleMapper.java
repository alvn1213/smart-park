package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ApplySettle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 入驻申请Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Mapper
public interface ApplySettleMapper extends BaseMapper<ApplySettle> {


    /**
     * 根据用户id查询入驻情况
     * @param createUserId
     * @return
     */
    List<ApplySettle> selectApplySettleByUSerId(Long createUserId);

}
