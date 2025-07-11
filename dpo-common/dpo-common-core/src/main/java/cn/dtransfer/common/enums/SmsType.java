package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 短信类型
 */
public enum SmsType implements IEnum<Integer> {


    /**
     * 会员登录
     */
    MEMBER_LOGIN("_MEMBER_LOGIN", 0),

    /**
     * 会员注册
     */
    MEMBER_REGISTER("_MEMBER_REGISTER", 1),

    /**
     * 忘记密码
     */
    FORGOT_PASSWORD("_FORGOT_PASSWORD", 2),

    /**
     * 重置密码
     */
    RESET_PASSWORD("_RESET_PASSWORD", 3),

    /**
     * 重置手机
     */
    RESET_MOBILE("_RESET_MOBILE", 4),

    /**
     * 服务申请
     */
    APPLY_SERVICE("_APPLY_SERVICE", 5);



    private String name;
    private int value;

    SmsType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

}
