package cn.dtransfer.quartz.task;


import cn.dtransfer.admin.domain.Activity;
import cn.dtransfer.admin.service.IActivityService;
import cn.dtransfer.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 活动定时任务
 *
 * @author dtransfer
 */
@Slf4j
@Component("activityTask")
public class ActivityTask {

    @Autowired
    private IActivityService activityService;

    public void updateStatus() {
        List<Activity> activities = activityService.findAll();
        for (Activity item : activities) {
            if (DateUtils.getNowDate().getTime() < item.getSignBegin().getTime()) {
                if (!item.getStatus().equals(Activity.Status.SIGN_NOT_START)) {
                    activityService.updateStatus(item.getId(), Activity.Status.SIGN_NOT_START);
                }
            } else if (DateUtils.getNowDate().getTime() > item.getSignBegin().getTime() && DateUtils.getNowDate().getTime() < item.getSignEnd().getTime()) {
                if (!item.getStatus().equals(Activity.Status.SIGNING)) {
                    activityService.updateStatus(item.getId(), Activity.Status.SIGNING);
                }
            } else if (DateUtils.getNowDate().getTime() > item.getSignEnd().getTime() && DateUtils.getNowDate().getTime() < item.getActBegin().getTime()) {
                if (!item.getStatus().equals(Activity.Status.ACT_NOT_START)) {
                    //item.setStatus(Activity.Status.ACT_NOT_START);
                    activityService.updateStatus(item.getId(), Activity.Status.ACT_NOT_START);
                }
            } else if (DateUtils.getNowDate().getTime() > item.getActBegin().getTime() && DateUtils.getNowDate().getTime() < item.getSignEnd().getTime()) {
                if (!item.getStatus().equals(Activity.Status.ACTING)) {
                    item.setStatus(Activity.Status.ACTING);
                    activityService.updateStatus(item.getId(), Activity.Status.ACTING);
                }
            } else if (DateUtils.getNowDate().getTime() > item.getSignEnd().getTime()) {
                if (!item.getStatus().equals(Activity.Status.ACT_END)) {
                    item.setStatus(Activity.Status.ACT_END);
                    activityService.updateStatus(item.getId(), Activity.Status.ACT_END);
                }
            }

        }
    }
}
