package cn.dtransfer.system.controller.monitor;

import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.service.ICurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.RequiresPermissions;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;

/**
 * 在线用户记录 提供者
 *
 */
@RestController
@RequestMapping("monitor/online")
public class OnlineController extends BaseController {

    @Autowired
    private RedisUtils redis;
    @Autowired
    private TokenStore          tokenStore;
    @Autowired
    private ICurrentUserService currentUserService;

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    /**
     * 查询在线用户记录列表
     */
    @RequiresPermissions("monitor:online:list")
    @GetMapping("list")
    public R list(String loginName, String ipaddr) {
        return result(currentUserService.selectCurrentUserList(loginName, ipaddr));
    }


    /**
     * 强退用户
     */
    @RequiresPermissions("monitor:online:forceLogout")
    @OperLog(title = "强退在线用户", businessType = BusinessType.FORCE)
    @PostMapping("forceLogout")
    public R forceLogout(String userId, String accessToken) {
        Token token = tokenStore.findToken(userId, accessToken);
        if (token != null) {
            // 移除用户的某个token
            tokenStore.removeTokensByUserId(token.getUserId());
            redis.delete(ACCESS_USERID + ":" + token.getUserId());
            return R.ok();
        }
        return R.error("没有找到用户");
    }

}
