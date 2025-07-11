package cn.dtransfer.system.controller;

import cn.dtransfer.system.service.ISmsService;
import cn.hutool.core.util.EnumUtil;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信 提供者
 */
@RestController
@RequestMapping("system/sms")
public class SmsController extends BaseController {

    @Autowired
    private ISmsService smsService;

    /**
     * 发送手机短信
     */
    @GetMapping("/send")
    public R sms(String mobile, String smsType) {
        if (StringUtils.isEmpty(mobile)) {
            return R.error("手机号不能为空。");
        }

        SmsType smsTypeObj = EnumUtil.fromStringQuietly(SmsType.class, smsType);
        if (smsTypeObj == null) {
            return R.error("验证码类型不能为空。");
        }
        String content = RandomUtil.randomInt(4);
        String result = smsService.send(mobile, content, smsTypeObj);
        return StringUtils.equals("OK", result) ? R.ok("发送成功！" + content) : R.error("发送失败, 原因：" + result);
    }

}
