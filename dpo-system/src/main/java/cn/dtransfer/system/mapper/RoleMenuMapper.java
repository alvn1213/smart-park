package cn.dtransfer.system.mapper;

import cn.dtransfer.system.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单关联表 数据层
 *
 */
@Mapper
public interface RoleMenuMapper {
    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量删除角色菜单关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleMenu(Long[] ids);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<RoleMenu> roleMenuList);
}
