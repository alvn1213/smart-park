package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ServiceMenu;

import java.util.List;

/**
 * 菜单管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-25
 */
public interface IServiceMenuService extends IService<ServiceMenu> {
    /**
     * 查询菜单管理
     *
     * @param id 菜单管理ID
     * @return 菜单管理
     */
    ServiceMenu selectServiceMenuById(Long id);

    /**
     * 查询菜单管理列表
     *
     * @param serviceMenu 菜单管理
     * @return 菜单管理集合
     */
    List<ServiceMenu> selectServiceMenuList(ServiceMenu serviceMenu);

    /**
     * 新增菜单管理
     *
     * @param serviceMenu 菜单管理
     * @return 结果
     */
    int insertServiceMenu(ServiceMenu serviceMenu);

    /**
     * 修改菜单管理
     *
     * @param serviceMenu 菜单管理
     * @return 结果
     */
    int updateServiceMenu(ServiceMenu serviceMenu);

    /**
     * 批量删除菜单管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteServiceMenuByIds(String ids);

    /**
     * 删除菜单管理信息
     *
     * @param id 菜单管理ID
     * @return 结果
     */
    int deleteServiceMenuById(Long id);
}
