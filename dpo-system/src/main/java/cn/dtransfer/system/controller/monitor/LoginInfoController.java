package cn.dtransfer.system.controller.monitor;

import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.ILoginInfoService;
import cn.dtransfer.system.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 系统访问记录 提供者
 *
 */
@RestController
@RequestMapping("monitor/loginInfo")
public class LoginInfoController extends BaseController {

    @Autowired
    private ILoginInfoService loginInfoService;

    /**
     * 查询系统访问记录列表
     */
    @RequiresPermissions("monitor:logininfo:list")
    @GetMapping("list")
    public R list(LoginInfo loginInfo) {
        startPage();
        return result(loginInfoService.selectLogininforList(loginInfo));
    }


    /**
     * 删除系统访问记录
     */
    @RequiresPermissions("monitor:logininfo:remove")
    @OperLog(title = "删除访问日志", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(loginInfoService.deleteLogininforByIds(ids));
    }

    /**
     *
     * 清除系统访问记录
     */
    @RequiresPermissions("monitor:logininfo:clean")
    @OperLog(title = "清空访问日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    public R clean() {
        loginInfoService.cleanLogininfor();
        return R.ok();
    }

}
