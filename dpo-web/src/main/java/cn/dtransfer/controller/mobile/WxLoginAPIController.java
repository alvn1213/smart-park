package cn.dtransfer.controller.mobile;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.MessageUtils;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.domain.form.LoginRequest;
import cn.dtransfer.system.log.publish.PublishFactory;
import cn.dtransfer.system.service.IAccessTokenService;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信用户直接登录
 *
 * @author dtransfer
 */
@Slf4j
@RestController
public class WxLoginAPIController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccessTokenService tokenService;

    @Autowired
    private WxMaService wxMaService;

    @PostMapping("/social_user_login/login")
    public R social(@RequestBody LoginRequest request) {
        if (ObjectUtil.isEmpty(request)) {
            return R.error("empty jscode");
        }
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(request.getCode());
            log.info(session.getSessionKey());
            log.info(session.getOpenid());
            // 检查是否是否存在
            User user = userService.selectUserByOpenid(session.getOpenid());
            // 不存在则注册
            if (user == null) {
                user = new User();
                JSONObject jsonObject = JSON.parseObject(request.getRawData());
                // 忽略保存昵称的头像信息
                String mobile = RandomUtil.randomInt(11);
                user.setUsername(mobile);
                user.setNickname(jsonObject.getString("nickName"));
                user.setMobile(mobile);
                user.setGender(jsonObject.getString("gender"));
                user.setAvatar(jsonObject.getString("avatarUrl"));
                user.setCreateBy("system");
                user.setCreateTime(DateUtils.getNowDate());
                user.setSalt(RandomUtil.randomStr(6));
                user.setOpenid(session.getOpenid());
                user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), "000000", user.getSalt()));
                userService.insertAppUser(user);
            }

            // 登记在线信息
            PublishFactory.recordLoginInfo(user.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            return R.ok(tokenService.createToken(user));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.error("获取微信用户数据失败");
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }

    }
    @PostMapping("/wx/login")
    public R login(@RequestBody User userInfo) {
        try {
            // 检查是否是否存在
            User user = userService.selectUserByOpenid(userInfo.getOpenid());
            // 不存在则注册
            if (user == null) {
                // 设置默认信息
                user = new User();
                String nickname = userInfo.getUsername() ;
                // 忽略保存昵称的头像信息
                String mobile = RandomUtil.randomInt(11);
                user.setUsername(mobile);
                user.setNickname(nickname);
                user.setMobile(mobile);
                user.setGender(userInfo.getGender());
                user.setAvatar(userInfo.getAvatar());
                user.setCreateBy(nickname);
                user.setCreateTime(DateUtils.getNowDate());
                user.setSalt(RandomUtil.randomStr(6));
                user.setOpenid(userInfo.getOpenid());
                user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), "000000", user.getSalt()));

                userService.insertAppUser(user);
            }

            // 登记在线信息
            PublishFactory.recordLoginInfo(user.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            return R.ok(tokenService.createToken(user));
        } catch (Exception e) {
            log.error("调用微信服务器出现异常", e);
            return R.error("获取微信用户数据失败");
        }
    }
}
