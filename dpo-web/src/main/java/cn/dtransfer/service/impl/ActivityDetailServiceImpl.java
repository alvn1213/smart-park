package cn.dtransfer.service.impl;

import cn.dtransfer.service.IActivityDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ActivityDetail;
import cn.dtransfer.admin.mapper.ActivityDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 活动报名详情Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service("activityDetailAppService")
public class ActivityDetailServiceImpl extends ServiceImpl<ActivityDetailMapper, ActivityDetail> implements IActivityDetailService {
    @Autowired
    private ActivityDetailMapper activityDetailMapper;


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
