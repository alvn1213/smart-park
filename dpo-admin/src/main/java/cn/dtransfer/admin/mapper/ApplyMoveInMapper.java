package cn.dtransfer.admin.mapper;


import cn.dtransfer.admin.domain.ApplyMoveIn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 注册迁入申请Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Mapper
public interface ApplyMoveInMapper extends BaseMapper<ApplyMoveIn> {

    /**
     * 根据用户id查询迁入申请资料
     * @param createUserId
     * @return
     */
    List<ApplyMoveIn> selectApplyMoveInByUserId(Long createUserId);

}
