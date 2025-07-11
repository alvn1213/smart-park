package cn.dtransfer.controller.mobile;

import cn.dtransfer.service.IActivityDetailService;
import cn.dtransfer.service.IActivityService;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Activity;
import cn.dtransfer.admin.domain.ActivityDetail;
import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.IUserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;

/**
 * 社群活动 APP端
 *
 * @author dtransfer
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/activity")
public class ActivityAPIController extends BaseController {


    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityDetailService activityDetailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IParkService parkService;


    /**
     * 查询活动管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("list")
    public R list(Activity activity) {
        List<Map> activityMaps = Lists.newArrayList();
        startPage();
        List<Activity> activities = activityService.selectActivityAppList(activity);
        if (CollectionUtil.isNotEmpty(activities)) {
            for (Activity item : activities) {
                Map<String, Object> activityMap = Maps.newHashMap();
                List<Map> activityDetailMaps = getActivityMaps(item, activityMap);
                activityMap.put("activityDetails", activityDetailMaps);
                activityMaps.add(activityMap);
            }
        }
        return result(activityMaps);
    }

    /**
     * 查询个人活动管理列表
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("myList")
    public R myList(Long userId) {
        startPage();
        List<Activity> activities = activityService.selectActivityAppListByUserId(userId);
        List<Map> activityMaps = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(activities)) {
            for (Activity item : activities) {
                Map<String, Object> activityMap = Maps.newHashMap();
                activityMap.put("id", item.getId());
                activityMap.put("name", item.getName());
                activityMap.put("smallImg", item.getSmallImg());
                activityMap.put("status", item.getStatus());
                activityMaps.add(activityMap);
            }
        }
        return result(activityMaps);
    }


    /**
     * 活动报名
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("addSign")
    public R addSign(@RequestBody ActivityDetail activityDetail, Long parkId) {
        if(parkId == null){
            return R.error("请选择园区！！");
        }
        Park park = parkService.selectParkById(parkId);
        User user = userService.selectUserById(activityDetail.getUserId());
        if (user == null) {
            return R.error("用户不存在");
        }
        activityDetail.setSignDate(DateUtils.getNowDate());
        activityDetail.setPhone(user.getMobile());
        activityDetail.setCreateBy(getLoginName());
        activityDetail.setParkId(park.getId());
        activityDetail.setTenantId(park.getTenantId());
        return toAjax(activityDetailService.insertActivityDetail(activityDetail));
    }

    /**
     * 报名详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("member:center:view")
    @GetMapping("signDetail")
    public R signDetail(Long id) {
        Activity activity = activityService.selectActivityByAppId(id);
        Map<String, Object> activityMap = Maps.newHashMap();
        List<Map> activityDetailMaps = getActivityMaps(activity, activityMap);
        Long userId = getCurrentUserId();
        Boolean isSign = false;
        if (CollectionUtil.isNotEmpty(activity.getActivityDetailList())) {
            for (ActivityDetail activityDetail : activity.getActivityDetailList()) {
                if (activityDetail != null && activityDetail.getUserId() != null && activityDetail.getUserId().equals(userId)) {
                    isSign = true;
                }
            }
        }
        activityMap.put("isSign", isSign);
        activityMap.put("activityDetails", activityDetailMaps);
        return R.data(activityMap);
    }

    /**
     * APP端封装参数
     *
     * @param activity
     * @param activityMap
     * @return
     */
    private List<Map> getActivityMaps(Activity activity, Map<String, Object> activityMap) {
        activityMap.put("id", activity.getId());
        activityMap.put("name", activity.getName());
        activityMap.put("signBegin", activity.getSignBegin());
        activityMap.put("signEnd", activity.getSignEnd());
        activityMap.put("actBegin", activity.getActBegin());
        activityMap.put("actEnd", activity.getActEnd());
        activityMap.put("location", activity.getLocation());
        activityMap.put("headImg", activity.getHeadImg());
        activityMap.put("smallImg", activity.getSmallImg());
        activityMap.put("content", activity.getContent());
        activityMap.put("contract", activity.getContract());
        activityMap.put("fullNum", activity.getFullNum());
        activityMap.put("status", activity.getStatus());
        activityMap.put("price", activity.getPrice());
        activityMap.put("currentNum", activity.getActivityDetailList() != null ? activity.getActivityDetailList().size() : 0);
        List<Map> activityDetailMaps = Lists.newArrayList();
        List<ActivityDetail> activityDetails = activity.getActivityDetailList();
        if (CollectionUtil.isNotEmpty(activityDetails)) {
            for (ActivityDetail activityDetail : activityDetails) {
                if (activityDetail != null && activityDetail.getUserId() != null) {
                    Map<String, Object> activityDetailMap = Maps.newHashMap();
                    activityDetailMap.put("userId", activityDetail.getUserId());
                    activityDetailMap.put("userName", activityDetail.getUserName());
                    activityDetailMap.put("avatar", activityDetail.getUser() != null ? activityDetail.getUser().getAvatar() : "");
                    activityDetailMaps.add(activityDetailMap);
                }
            }
        }
        return activityDetailMaps;
    }

}
