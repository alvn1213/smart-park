package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IParkService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.mapper.ParkMapper;
import cn.dtransfer.common.annotation.DataScope;
import cn.dtransfer.system.domain.Dept;
import cn.dtransfer.system.service.ICurrentUserService;
import cn.dtransfer.system.service.IDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 园区管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service
public class ParkServiceImpl extends ServiceImpl<ParkMapper, Park> implements IParkService {
    @Autowired
    private ParkMapper parkMapper;

    @Autowired
    private ICurrentUserService currentUserService;

    @Autowired
    private IDeptService deptService;

    /**
     * 查询园区管理
     *
     * @param id 园区管理ID
     * @return 园区管理
     */
    @Override
    public Park selectParkById(Long id) {
        return parkMapper.selectById(id);
    }

    /**
     * 查询园区管理列表
     *
     * @param park 园区管理
     * @return 园区管理
     */
    @Override
    public List<Park> selectParkList(Park park) {
        return parkMapper.selectParkList(park);
    }

    /**
     * 新增园区管理
     *
     * @param park 园区管理
     * @return 结果
     */
    @Override
    public int insertPark(Park park) {
        return parkMapper.insert(park);
    }

    /**
     * 修改园区管理
     *
     * @param park 园区管理
     * @return 结果
     */
    @Override
    public int updatePark(Park park) {
        return parkMapper.updateById(park);
    }

    /**
     * 删除园区管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteParkByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return parkMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }


    /**
     * 判断是否存在
     *
     * @param property 属性名称
     * @param value    属性值
     * @return 是否存在
     */
    @Override
    public boolean exists(String property, Object value) {
        QueryWrapper<Park> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(property) && value != null) {
            queryWrapper.eq(property, value);
        }
        return parkMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 判断是否唯一
     *
     * @param id       ID
     * @param property 属性名称
     * @param value    属性值
     * @return 是否唯一
     */
    @Override
    public boolean unique(Long id, String property, Object value) {
        QueryWrapper<Park> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(property) && value != null) {
            queryWrapper.eq(property, value);
        }
        if (id != null) {
            queryWrapper.ne("id", id);
        }
        return parkMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 查询园区管理
     *
     * @return 园区管理
     */
    @Override
    @DataScope(deptAlias = "d")
    public Park selectCurrentParkByDeptId(Park park) {
        return parkMapper.selectByDeptId(park);
    }


    /**
     * 查询园区管理列表
     *
     * @param park 园区管理
     * @return 园区管理
     */
    @Override
    public List<Park> selectAllParkList(Park park) {
        return parkMapper.selectParkList(park);
    }

    /**
     * 通过部门初始化园区
     * @param dept
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int initPark(Dept dept) {
        deptService.insertPark(dept);
        Park park = new Park();
//        park.setDeptId(dept.getId());
        park.setTenantId(dept.getTenantId());
        park.setIsMarketable(Boolean.FALSE);
        park.setName(dept.getDeptName());
        return parkMapper.insert(park);
    }

}
