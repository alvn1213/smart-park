package cn.dtransfer.controller.mobile.member;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.bean.BeanUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.IConfigService;
import cn.dtransfer.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.Map;

/**
 * 个人资料
 *
 * @author dtransfer
 */
@RestController
@RequestMapping("/user/profile")
public class ProfileAPIController extends BaseController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IConfigService configService;



    /**
     * 当前用户信息
     */
    @RequiresPermissions("member:center:view")
    @GetMapping
    public R index(@LoginUser User user) {
        String jsonConfig = configService.selectConfigByKey("sys.setting.siteUrl");
        JSONObject jsonObject = JSON.parseObject(jsonConfig);
        String siteUrl = jsonObject.getString("siteUrl");
        Map<String, Object> map = Maps.newHashMap();
        user = userService.selectUserById(user.getId());
        map.put("userId", user.getId());
        map.put("mobile", user.getMobile());
        if (user.getAvatar().contains(Constants.RESOURCE_PREFIX)) {
            map.put("avatar", siteUrl + user.getAvatar());
        } else {
            map.put("avatar", user.getAvatar());
        }
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickname());
        map.put("sex", user.getGender());
        return R.data(map);
    }

    /**
     * 更新当前用户
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("/update")
    public R update(@RequestBody User user, @LoginUser User currentUser) {
        BeanUtils.copyBeanProp(currentUser, user);
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(currentUser))) {
            return R.error("修改用户'" + currentUser.getUsername()+ "'失败，手机号码已存在");
        }
        currentUser.setUpdateBy(getLoginName());
        return toAjax(userService.updateUser(currentUser));
    }
}
