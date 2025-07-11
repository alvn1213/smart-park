package cn.dtransfer.service.impl;

import cn.dtransfer.service.IServiceMenuService;
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
@Service("serviceMenuAppService")
public class ServiceMenuServiceImpl extends ServiceImpl<ServiceMenuMapper, ServiceMenu> implements IServiceMenuService {
    @Autowired
    private ServiceMenuMapper serviceMenuMapper;


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
        queryWrapper.eq("park_id", serviceMenu.getParkId());
        return serviceMenuMapper.selectList(queryWrapper);
    }


}
