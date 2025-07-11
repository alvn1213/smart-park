package cn.dtransfer.service.impl;

import cn.dtransfer.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Activity;
import cn.dtransfer.admin.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service("activityAppService")
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    /**
     * 查询活动管理列表
     *
     * @param activity 活动管理
     * @return 活动管理
     */
    @Override
    public List<Activity> selectActivityAppList(Activity activity)
    {
        return activityMapper.selectActivityForAppList(activity);
    }

    /**
     * 查询活动管理列表(app端 个人查询)
     *
     * @param userId
     * @return 活动管理集合
     */
    @Override
    public List<Activity> selectActivityAppListByUserId(Long userId) {
        return activityMapper.selectActivityForAppListByUserId(userId);
    }


    /**
     * 查询活动管理app端
     *
     * @param id 活动管理ID
     * @return 活动管理
     */
    @Override
    public Activity selectActivityByAppId(Long id) {
        return activityMapper.selectActivityByAppId(id);
    }


}
