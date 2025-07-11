package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IServiceMenuService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ServiceMenu;
import cn.dtransfer.admin.mapper.ServiceMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-25
 */
@Service
public class ServiceMenuServiceImpl extends ServiceImpl<ServiceMenuMapper, ServiceMenu> implements IServiceMenuService {
    @Autowired
    private ServiceMenuMapper serviceMenuMapper;

    /**
     * 查询菜单管理
     *
     * @param id 菜单管理ID
     * @return 菜单管理
     */
    @Override
    public ServiceMenu selectServiceMenuById(Long id) {
        return serviceMenuMapper.selectById(id);
    }

    /**
     * 查询菜单管理列表
     *
     * @param serviceMenu 菜单管理
     * @return 菜单管理
     */
    @Override
    public List<ServiceMenu> selectServiceMenuList(ServiceMenu serviceMenu) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (serviceMenu.getMenuImg() != null) {
            queryWrapper.like("menu_name", serviceMenu.getMenuName());
        }
        return serviceMenuMapper.selectList(queryWrapper);
    }

    /**
     * 新增菜单管理
     *
     * @param serviceMenu 菜单管理
     * @return 结果
     */
    @Override
    public int insertServiceMenu(ServiceMenu serviceMenu) {
        return serviceMenuMapper.insert(serviceMenu);
    }

    /**
     * 修改菜单管理
     *
     * @param serviceMenu 菜单管理
     * @return 结果
     */
    @Override
    public int updateServiceMenu(ServiceMenu serviceMenu) {
        return serviceMenuMapper.updateById(serviceMenu);
    }

    /**
     * 删除菜单管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteServiceMenuByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return serviceMenuMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除菜单管理信息
     *
     * @param id 菜单管理ID
     * @return 结果
     */
    @Override
    public int deleteServiceMenuById(Long id) {
        return serviceMenuMapper.deleteById(id);
    }
}
