package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

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
     * 查询活动app列表
     *
     * @param activity 活动管理
     * @return 活动管理集合
     */
    List<Activity> selectActivityForAppList(Activity activity);

    /**
     * 查询活动管理app端
     *
     * @param id 活动管理ID
     * @return 活动管理
     */
    Activity selectActivityByAppId(Long id);


    /**
     * 查询活动管理列表(app端 个人查询)
     *
     * @param userId
     * @return 活动管理集合
     */
    List<Activity> selectActivityForAppListByUserId(Long userId);


    /**
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") Long id, @Param("status") Activity.Status status);

    /**
     * 查询所有
     * @return
     */
    List<Activity> findAll();

}
