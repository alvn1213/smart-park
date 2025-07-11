package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IActivityService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Autowired
    private ActivityMapper activityMapper;


    /**
     * 查询活动管理
     *
     * @param id 活动管理ID
     * @return 活动管理
     */
    @Override
    public Activity selectActivityById(Long id) {
        return activityMapper.selectActivityById(id);
    }

    /**
     * 查询活动管理列表
     *
     * @param activity 活动管理
     * @return 活动管理
     */
    @Override
    public List<Activity> selectActivityList(Activity activity) {
        return activityMapper.selectActivityList(activity);
    }

    /**
     * 新增活动管理
     *
     * @param activity 活动管理
     * @return 结果
     */
    @Override
    public int insertActivity(Activity activity) {
        activity.setStatus(Activity.Status.SIGN_NOT_START);
        activity.setIsMarketable(false);
        return activityMapper.insert(activity);
    }

    /**
     * 修改活动管理
     *
     * @param activity 活动管理
     * @return 结果
     */
    @Override
    public int updateActivity(Activity activity) {
        return activityMapper.updateById(activity);
    }

    /**
     * 删除活动管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteActivityByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return activityMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }


    /**
     * 查找所有
     * @return
     */
    @Override
    public List<Activity> findAll() {
        return activityMapper.findAll();
    }


    @Override
    public int updateStatus(Long id, Activity.Status status) {
        return activityMapper.updateStatus(id, status);
    }
}
