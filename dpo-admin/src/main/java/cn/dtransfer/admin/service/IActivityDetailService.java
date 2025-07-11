package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ActivityDetail;

import java.util.List;

/**
 * 活动报名详情Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IActivityDetailService extends IService<ActivityDetail> {

    /**
     * 查询活动报名详情列表
     *
     * @param activityDetail 活动报名详情
     * @return 活动报名详情集合
     */
    List<ActivityDetail> selectActivityDetailList(ActivityDetail activityDetail);

    /**
     * 新增活动报名详情
     *
     * @param activityDetail 活动报名详情
     * @return 结果
     */
    int insertActivityDetail(ActivityDetail activityDetail);


}
