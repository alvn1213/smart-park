package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IActivityDetailService;
import cn.dtransfer.admin.service.IActivityService;
import cn.dtransfer.admin.domain.Activity;
import cn.dtransfer.admin.domain.ActivityDetail;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.ICurrentUserService;
import cn.dtransfer.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Date;

/**
 * 活动管理 提供者
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@RestController
@RequestMapping("/admin/activity")
public class ActivityController extends BaseController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICurrentUserService userOnlineService;


    /**
     * 查询活动管理
     */
    @RequiresPermissions("admin:activity:edit")
    @GetMapping("get/{id}")
    public Activity get(@PathVariable("id") Long id) {
        return activityService.selectActivityById(id);
    }

    /**
     * 查询活动管理列表
     */
    @RequiresPermissions("admin:activity:list")
    @GetMapping("list")
    public R list(Activity activity) {
        startPage();
        return result(activityService.selectActivityList(activity));
    }

    /**
     * 新增保存活动管理
     */
    @RequiresPermissions("admin:activity:add")
    @PostMapping("save")
    public R addSave(@RequestBody Activity activity) {
        ValidatorUtils.validateEntity(activity);
        activity.setTenantId(userOnlineService.getTenantId());
        activity.setParkId(userOnlineService.getParkId());
        activity.setCreateBy(userOnlineService.getLoginName());
        return toAjax(activityService.insertActivity(activity));
    }

    /**
     * 修改保存活动管理
     */
    @RequiresPermissions("admin:activity:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Activity activity) {
        ValidatorUtils.validateEntity(activity);
        activity.setUpdateBy(userOnlineService.getLoginName());
        activity.setUpdateTime(new Date());
        return toAjax(activityService.updateActivity(activity));
    }

    /**
     * 删除活动管理
     */
    @RequiresPermissions("admin:activity:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(activityService.deleteActivityByIds(ids));
    }


    /**
     * 活动报名
     */
    @RequiresPermissions("admin:activity:add")
    @PostMapping("addSign")
    public R addSign(@RequestBody ActivityDetail activityDetail) {
        User user = userService.selectUserById(activityDetail.getUserId());
        if (user == null) {
            return R.error("用户不存在");
        }
        activityDetail.setSignDate(DateUtils.getNowDate());
        activityDetail.setPhone(user.getMobile());
        return toAjax(activityDetailService.insertActivityDetail(activityDetail));
    }

    /**
     * 定时任务状态修改
     */
    @RequiresPermissions("admin:activity:edit")
    @PostMapping("/changeMarketable")
    public R changeMarketable(@RequestBody Activity activity) {
        Activity newActivity = activityService.selectActivityById(activity.getId());
        if (newActivity == null) {
            return R.error("活动不存在!");
        }
        activity.setMarketableTime(DateUtils.getNowDate());
        return toAjax(activityService.updateActivity(activity));
    }


}
