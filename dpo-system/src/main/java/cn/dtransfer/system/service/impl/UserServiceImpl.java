package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.domain.UserRole;
import cn.dtransfer.system.mapper.RoleMapper;
import cn.dtransfer.system.mapper.UserMapper;
import cn.dtransfer.system.mapper.UserRoleMapper;
import cn.dtransfer.system.service.IConfigService;
import cn.dtransfer.system.service.IUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.dtransfer.common.annotation.DataScope;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.common.exception.BusinessException;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.security.Md5Utils;
import cn.dtransfer.system.domain.Role;
import cn.dtransfer.system.domain.User;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 用户 业务层处理
 *
 */
@Service
public class UserServiceImpl  implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IConfigService configService;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<User> selectAllocatedList(User user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<User> selectUnallocatedList(User user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String username, User.Type type) {
        return userMapper.selectUserByUsername(username, type);
    }

    /**
     * 通过Openid查询用户
     *
     * @param openid
     * @return 用户对象信息
     */
    @Override
    public User selectUserByOpenid(String openid) {
        return userMapper.selectUserByOpenid(openid);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param mobile 手机号码
     * @return 用户对象信息
     */
    @Override
    public User selectUserByMobile(String mobile) {
        return userMapper.selectUserByMobile(mobile);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws BusinessException {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (User.isAdmin(userId)) {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(User user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }


    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertAppUser(User user) {
        // 新增用户信息
        int rows = userMapper.insertAppUser(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User user) {
        Long userId = user.getId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(User user) {
        List<Long> roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<UserRole> userRoleList = Lists.newArrayList();
            for (Long roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            if (CollUtil.isNotEmpty(userRoleList)) {
                userRoleMapper.batchUserRole(userRoleList);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户名
     * @return
     */
    @Override
    public String checkUsernameUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkUsernameUnique(user.getUsername());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户昵称是否唯一
     *
     * @param user 用户名
     * @return
     */
    @Override
    public String checkNicknameUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkNicknameUnique(user.getUsername());;
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
            return UserConstants.NICK_NAME_NOT_UNIQUE;
        }
        return UserConstants.NICK_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkMobileUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkPhoneUnique(user.getMobile());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(User user) {
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list) {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString())) {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }


    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<User> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (User user : userList) {
            try {
                // 验证是否存在这个用户
                User u = userMapper.selectUserByUsername(user.getUsername(), User.Type.Admin);
                if (StringUtils.isNull(u)) {
                    user.setPassword(Md5Utils.hash(user.getUsername() + password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUsername() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUsername() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUsername() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Set<Long> selectUserIdsHasRoles(Long[] roleIds) {
        return ArrayUtil.isNotEmpty(roleIds) ? userMapper.selectUserIdsHasRoles(roleIds) : null;
    }

    @Override
    public Set<Long> selectUserIdsInDepts(Long[] deptIds) {
        return ArrayUtil.isNotEmpty(deptIds) ? userMapper.selectUserIdsInDepts(deptIds) : null;
    }

}
