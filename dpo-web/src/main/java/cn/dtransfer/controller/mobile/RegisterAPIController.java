package cn.dtransfer.controller.mobile;

import cn.dtransfer.system.service.IAccessTokenService;
import cn.dtransfer.system.service.ISmsService;
import cn.dtransfer.system.service.IUserService;
import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.domain.Customer;
import cn.dtransfer.admin.service.ICustomerService;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.log.annotation.OperLog;
import cn.dtransfer.system.log.enums.BusinessType;
import cn.dtransfer.system.util.PasswordUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 会员注册
 *
 * @author dtransfer
 */
@RestController
@RequestMapping("/user")
public class RegisterAPIController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IAccessTokenService tokenService;



    /**
     * 检查手机是否存在
     */
    @GetMapping("/check_mobile")
    public R checkMobile(User user) {
        return R.data(!UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user)));
    }

    /**
     * 发送手机短信
     */
    @GetMapping("/send_mobile")
    public R sendMobile(String mobile) {
        String content = RandomUtil.randomInt(4);
        String result = smsService.sendRegisterSms(mobile, content);
        return R.ok("发送成功！");
    }


    /**
     * 企业搜索
     */
    @PostMapping("/search_customer")
    public R searchCustomer(@RequestBody Customer customer) {
        List<Customer> customers = customerService.selectCustomerList(customer);
        List<Map<String, Object>> customerList = Lists.newArrayList();
        customers.stream().forEach(pCustomer -> {
            Map<String, Object> map = Maps.newHashMap();
            map.put("customerId", pCustomer.getId());
            map.put("name", pCustomer.getName());
            map.put("sector", pCustomer.getSector());
            if (StringUtils.isNotNull(pCustomer.getPark())) {
                Park park = pCustomer.getPark();
                String[] cityName = StringUtils.split(park.getCityName(), ",");
                map.put("parkName", cityName[0] + "," + park.getName());
            }
            customerList.add(map);
        });
        return R.data(customerList);
    }


    /**
     * 检查验证码是否合法
     */
    @GetMapping("/check_code")
    public R checkCode(String mobile, String code) {
        return R.data(smsService.verify(mobile, code, SmsType.MEMBER_REGISTER));
    }


    /**
     * 注册
     */
    @PostMapping("/register")
    @OperLog(title = "用户注册", businessType = BusinessType.INSERT)
    public R index(@RequestBody User user) {
        if (StringUtils.isEmpty(user.getMobile())) {
            return R.error("手机号码不能为空!");
        }
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkMobileUnique(user))) {
            return R.error("手机号码已存在!");
        }
        // 密码如果不在指定范围内 错误
        if (user.getPassword().length() < UserConstants.PASSWORD_MIN_LENGTH || user.getPassword().length() > UserConstants.PASSWORD_MAX_LENGTH) {
            return R.error(String.format("新密码长度在%s与%s之间", UserConstants.PASSWORD_MIN_LENGTH, UserConstants.PASSWORD_MAX_LENGTH));
        }

        user.setUsername(user.getMobile());
        user.setNickname(user.getNickname());
        user.setMobile(user.getMobile());

        // 验证
        ValidatorUtils.validateEntity(user);
        user.setCreateBy(user.getUsername());
        user.setCreateTime(DateUtils.getNowDate());
        user.setSalt(RandomUtil.randomStr(6));
        user.setPassword(PasswordUtils.encryptPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        userService.insertAppUser(user);
        User pUser = userService.selectUserById(user.getId());
        // 获取登录token
        return R.ok(tokenService.createToken(pUser));
    }


}
