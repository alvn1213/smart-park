package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IDjService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Dj;
import cn.dtransfer.admin.mapper.DjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 党建管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service
public class DjServiceImpl extends ServiceImpl<DjMapper, Dj> implements IDjService {
    @Autowired
    private DjMapper djMapper;

    /**
     * 查询党建管理
     *
     * @param id 党建管理ID
     * @return 党建管理
     */
    @Override
    public Dj selectDjById(Long id) {
        return djMapper.selectById(id);
    }

    /**
     * 查询党建管理列表
     *
     * @param dj 党建管理
     * @return 党建管理
     */
    @Override
    public List<Dj> selectDjList(Dj dj) {
        QueryWrapper<Dj> queryWrapper = new QueryWrapper();
        return djMapper.selectList(queryWrapper);
    }

    /**
     * 新增党建管理
     *
     * @param dj 党建管理
     * @return 结果
     */
    @Override
    public int insertDj(Dj dj) {
        dj.setIsMarketable(false);
        return djMapper.insert(dj);
    }

    /**
     * 修改党建管理
     *
     * @param dj 党建管理
     * @return 结果
     */
    @Override
    public int updateDj(Dj dj) {
        return djMapper.updateById(dj);
    }

    /**
     * 删除党建管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDjByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return djMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除党建管理信息
     *
     * @param id 党建管理ID
     * @return 结果
     */
    @Override
    public int deleteDjById(Long id) {
        return djMapper.deleteById(id);
    }
}
