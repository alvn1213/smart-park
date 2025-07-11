package cn.dtransfer.system.domain;

import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.handlers.UserTypeEnumHandler;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.dtransfer.common.core.domain.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户对象 sys_user
 *
 */
@Data
@Table(name = "sys_user")
public class User extends BaseEntity<User> {
    private static final long serialVersionUID = 1L;


    @TableField(typeHandler = UserTypeEnumHandler.class)
    private Type dtype;

    /**
     * 类型
     */
    public enum Type implements IEnum<String> {

        /**
         * 会员
         */
        Member("会员","Member"),

        /**
         * 商家
         */
        Business("商家","Business"),

        /**
         * 平台
         */
        Admin("平台","Admin");

        private String name;
        private String    value;

        Type(String name, String value) {
            this.name  = name;
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static Type parse(Integer value) {
            for (Type type : values()) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }

    }

    /**
     * 登录名称
     */
    @NotBlank(message = "登录名称不能为空")
    private String username;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门对象
     */
    private Dept dept;

    /**
     * 角色信息
     */
    private List<Role> roles;

    /**
     * 角色组
     */
    private List<Long> roleIds;

    /**
     * 权限信息
     */
    private Set<String> buttons;

    /**
     * 在线用户记录
     */
    @TableField(exist = false)
    private CurrentUserVO userOnline;

    /**
     * 子部门id集合
     */
    @TableField(exist = false)
    private List<Long> deptIds;

    /**
     * 是否超管
     *
     * @param id
     * @return
     */
    public static boolean isAdmin(Long id) {
        return id != null && 1L == id;
    }



}
