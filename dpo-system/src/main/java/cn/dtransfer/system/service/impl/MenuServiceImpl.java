package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.domain.Menu;
import cn.dtransfer.system.mapper.MenuMapper;
import cn.dtransfer.system.mapper.RoleMenuMapper;
import cn.dtransfer.system.service.IMenuService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.dtransfer.common.constant.UserConstants;
import cn.dtransfer.common.core.domain.Ztree;
import cn.dtransfer.common.redis.annotation.RedisCache;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.Role;
import cn.dtransfer.system.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 菜单 业务层处理
 *
 */
@Service
public class MenuServiceImpl implements IMenuService {

    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 根据用户查询菜单
     *
     * @param user 用户信息
     * @return 菜单列表
     */
    @Override
    public List<Menu> selectMenusByUser(User user) {
        List<Menu> menus = new LinkedList<>();
        // 管理员显示所有菜单信息
        if (User.isAdmin(user.getId())) {
            menus = menuMapper.selectMenuNormalAll();
        } else {
            menus = menuMapper.selectMenusByUserId(user.getId());
        }
        return menus;
    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 查询菜单集合
     *
     * @return 所有菜单信息
     */
    @Override
    public List<Menu> selectMenuAll() {
        return menuMapper.selectMenuAll();
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    @RedisCache(key = "user_perms", fieldKey = "#userId", expired = 30)
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限数组
     */
    @Override
    public String[] selectPermsStrArray(Long userId) {
        Set<String> permissionsSet = this.selectPermsByUserId(userId);

        if (CollectionUtil.isNotEmpty(permissionsSet)) {
            return Convert.toStrArray(permissionsSet);
        }
        return null;
    }

    @Override
    public List<Menu> selectMenuIdsByRoleId(Long roleId) {
        return menuMapper.selectMenuIdsByRoleId(roleId);
    }

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    @Override
    public List<Ztree> roleMenuTreeData(Role role) {
        Long roleId = role.getId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<Menu> menuList = menuMapper.selectMenuAll();
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
            ztrees = initZtree(menuList, roleMenuList, true);
        } else {
            ztrees = initZtree(menuList, null, true);
        }
        return ztrees;
    }

    /**
     * 查询所有菜单
     *
     * @return 菜单列表
     */
    @Override
    public List<Ztree> menuTreeData() {
        List<Menu> menuList = menuMapper.selectMenuAll();
        List<Ztree> ztrees = initZtree(menuList);
        return ztrees;
    }

    /**
     * 查询系统所有权限
     *
     * @return 权限列表
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll() {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<Menu> permissions = menuMapper.selectMenuAll();
        if (StringUtils.isNotEmpty(permissions)) {
            for (Menu menu : permissions) {
                section.put(menu.getMenuName(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList) {
        return initZtree(menuList, null, false);
    }

    /**
     * 对象转菜单树
     *
     * @param menuList     菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<Menu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (Menu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, roleMenuList, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getId() + menu.getPerms()));
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    public String transMenuName(Menu menu, List<String> roleMenuList, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag) {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public Menu selectMenuById(Long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     *
     * @param parentId 父级菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId) {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(Long menuId) {
        return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int insertMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */
    @Override
    public String checkMenuNameUnique(Menu menu) {
        Long menuId = StringUtils.isNull(menu.getId()) ? -1L : menu.getId();
        Menu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != menuId.longValue()) {
            return UserConstants.MENU_NAME_NOT_UNIQUE;
        }
        return UserConstants.MENU_NAME_UNIQUE;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<Menu> getChildPerms(List<Menu> list, int parentId) {
        List<Menu> returnList = new ArrayList<Menu>();
        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext(); ) {
            Menu t = (Menu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Menu> list, Menu t) {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext()) {
                    Menu n = (Menu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Menu> getChildList(List<Menu> list, Menu t) {
        List<Menu> tlist = new ArrayList<Menu>();
        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu n = (Menu) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Menu> list, Menu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
