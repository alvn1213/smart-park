package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ActivityDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 活动报名详情Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Mapper
public interface ActivityDetailMapper extends BaseMapper<ActivityDetail> {

    /**
     * 查询活动报名详情列表
     *
     * @param activityDetail 活动报名详情
     * @return 活动报名详情集合
     */
    List<ActivityDetail> selectActivityDetailList(ActivityDetail activityDetail);
}
