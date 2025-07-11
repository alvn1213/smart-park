package cn.dtransfer.system.domain.vo;

import java.util.List;
import java.util.Set;

public class UserVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 园区ID
     */
    private Long parkId;

    /**
     * 角色组
     */
    private List<Long> roleIds;

    /**
     * 权限信息
     */
    private Set<String> buttons;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Set<String> getButtons() {
        return buttons;
    }

    public void setButtons(Set<String> buttons) {
        this.buttons = buttons;
    }
}
