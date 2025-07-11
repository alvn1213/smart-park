package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Policy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 政策管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
}
