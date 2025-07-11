package cn.dtransfer.service;

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
     * 查询菜单管理列表
     *
     * @param serviceMenu 菜单管理
     * @return 菜单管理集合
     */
    List<ServiceMenu> selectServiceMenuList(ServiceMenu serviceMenu);

}
