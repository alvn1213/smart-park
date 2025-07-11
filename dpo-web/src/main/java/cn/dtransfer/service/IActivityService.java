package cn.dtransfer.service;

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
     * 查询活动管理列表
     *
     * @param activity 活动管理
     * @return 活动管理集合
     */
    List<Activity> selectActivityAppList(Activity activity);

    /**
     * 查询活动管理列表(app端 个人查询)
     *
     * @param userId
     * @return 活动管理集合
     */
    List<Activity> selectActivityAppListByUserId(Long userId);


    /**
     * 查询活动管理(app端)
     *
     * @param id 活动管理ID
     * @return 活动管理
     */
    Activity selectActivityByAppId(Long id);


}
