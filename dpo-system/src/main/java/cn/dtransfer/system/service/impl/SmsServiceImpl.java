package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.service.ISmsService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import cn.dtransfer.common.enums.SmsType;
import cn.dtransfer.common.redis.util.RedisUtils;
import cn.dtransfer.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * Service - 短信
 */
@Log4j2
@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private RedisUtils redis;

    @Value("${aliyun.sms.domain}")
    private String domain;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.signName}")
    private String signName;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    /**
     * 发送会员注册短信
     *
     * @param mobile  手机
     * @param content 内容
     */
    @Override
    public String sendRegisterSms(String mobile, String content) {
        // 设置短信有效时间
        redis.set(mobile + SmsType.MEMBER_REGISTER.getName(), content, 300);
        // 发送短信
        return send(mobile, content);
    }

    /**
     * 发送忘记密码短信
     *
     * @param mobile  手机
     * @param content 内容
     */
    @Override
    public String sendForgotPasswordSms(String mobile, String content) {
        // 设置短信有效时间
        redis.set(mobile + SmsType.FORGOT_PASSWORD.getName(), content, 300);
        // 发送短信
        return send(mobile, content);
    }

    /**
     * 发送重置手机短信
     *
     * @param mobile  手机
     * @param content 内容
     * @return
     */
    @Override
    public String sendResetSms(String mobile, String content) {
        // 设置短信有效时间
        redis.set(mobile + SmsType.RESET_MOBILE.getName(), content, 300);
        // 发送短信
        return send(mobile, content);
    }

    /**
     * 发送登录手机短信
     *
     * @param mobile  手机
     * @param content 内容
     * @return
     */
    @Override
    public String sendLoginSms(String mobile, String content) {
        // 设置短信有效时间
        redis.set(mobile + SmsType.MEMBER_LOGIN.getName(), content, 60);
        // 发送短信
        return send(mobile, content);
    }

    @Override
    public String sendApplyServiceSms(String mobile, String content) {
        // 设置短信有效时间
        redis.set(mobile + SmsType.APPLY_SERVICE.getName(), content, 60);
        // 发送短信
        return send(mobile, content);
    }

    /**
     * 发送会员注册短信
     *
     * @param mobile  手机
     * @param content 内容
     */
    @Override
    public String send(String mobile, String content, SmsType smsType) {
        // 设置短信有效时间
        redis.set(mobile + smsType.getName(), content, 300);
        // 发送短信
        return send(mobile, content);
    }

    /**
     * 验证短信有效性
     *
     * @param mobile  手机
     * @param captcha 验证码
     * @param smsType 验证类型
     * @return
     */
    @Override
    public boolean verify(String mobile, String captcha, SmsType smsType) {
        String key = mobile + smsType.getName();
        // 检验
        String code = redis.get(key);
        if (StringUtils.equals(captcha, code)) {
            redis.delete(key);
            return true;
        }
        return false;
    }

    /**
     * 发送短信
     *
     * @param mobile
     * @param content
     * @return
     */
    private String send(String mobile, String content) {
        Assert.hasText(mobile, "[Assertion failed] - mobile must have text; it must not be null, empty, or blank");
        Assert.hasText(content, "[Assertion failed] - content must have text; it must not be null, empty, or blank");

        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //接受验证码的手机号
        request.putQueryParameter("PhoneNumbers", mobile);
        //签名
        request.putQueryParameter("SignName", signName);
        //模板代码
        request.putQueryParameter("TemplateCode", templateCode);
        //用户定义的验证码内容
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + content + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = response.getData();
            log.info(result);
            JSONObject resultJson = JSONObject.parseObject(result);
            return resultJson.getString("Message");
        } catch (ServerException e) {
            log.error(e.getErrMsg());
        } catch (ClientException e) {
            log.error(e.getErrMsg());
        }
        return null;
    }


}
