package cn.dtransfer.system.util;

import cn.dtransfer.common.utils.security.Md5Utils;
import cn.dtransfer.system.domain.User;

/**
 * 密码加/解密
 */
public class PasswordUtils {

    public static boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getUsername(), newPassword, user.getSalt()));
    }

    public static String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
}
