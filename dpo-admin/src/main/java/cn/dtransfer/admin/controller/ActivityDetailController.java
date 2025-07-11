package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IActivityDetailService;
import cn.dtransfer.admin.domain.ActivityDetail;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 活动报名详情 提供者
 *
 * @author dtransfer
 * @date 2024-04-15
 */
@RestController
@RequestMapping("/admin/activityDetail")
public class ActivityDetailController extends BaseController {

    @Autowired
    private IActivityDetailService activityDetailService;


    /**
     * 查询活动报名详情列表
     */
    @RequiresPermissions("admin:activityDetail:list")
    @GetMapping("list")
    public R list(ActivityDetail activityDetail) {
        startPage();
        return result(activityDetailService.selectActivityDetailList(activityDetail));
    }


}
