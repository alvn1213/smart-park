package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IActivityDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ActivityDetail;
import cn.dtransfer.admin.mapper.ActivityDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动报名详情Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service
public class ActivityDetailServiceImpl extends ServiceImpl<ActivityDetailMapper, ActivityDetail> implements IActivityDetailService {
    @Autowired
    private ActivityDetailMapper activityDetailMapper;

    @Override
    public List<ActivityDetail> selectActivityDetailList(ActivityDetail activityDetail) {
        return activityDetailMapper.selectActivityDetailList(activityDetail);
    }

    /**
     * 新增活动报名详情
     *
     * @param activityDetail 活动报名详情
     * @return 结果
     */
    @Override
    public int insertActivityDetail(ActivityDetail activityDetail) {
        return activityDetailMapper.insert(activityDetail);
    }


}
