package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Activity;

import java.util.List;

/**
 * 活动管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IActivityService extends IService<Activity> {
    /**
     * 查询活动管理
     *
     * @param id 活动管理ID
     * @return 活动管理
     */
    Activity selectActivityById(Long id);

    /**
     * 查询活动管理列表
     *
     * @param activity 活动管理
     * @return 活动管理集合
     */
    List<Activity> selectActivityList(Activity activity);

    /**
     * 新增活动管理
     *
     * @param activity 活动管理
     * @return 结果
     */
    int insertActivity(Activity activity);

    /**
     * 修改活动管理
     *
     * @param activity 活动管理
     * @return 结果
     */
    int updateActivity(Activity activity);

    /**
     * 批量删除活动管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteActivityByIds(String ids);



    /**
     * 查询所有活动
     * @return
     */
    List<Activity> findAll();


    /**
     * 更新活动状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Activity.Status status);
}
