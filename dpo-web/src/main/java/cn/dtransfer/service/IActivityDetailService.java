package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ActivityDetail;

/**
 * 活动报名详情Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IActivityDetailService extends IService<ActivityDetail> {

    /**
     * 新增活动报名详情
     *
     * @param activityDetail 活动报名详情
     * @return 结果
     */
    int insertActivityDetail(ActivityDetail activityDetail);

}
