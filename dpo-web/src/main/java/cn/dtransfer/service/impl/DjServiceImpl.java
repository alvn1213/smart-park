package cn.dtransfer.service.impl;

import cn.dtransfer.service.IDjService;
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
@Service("djAppService")
public class DjServiceImpl extends ServiceImpl<DjMapper, Dj> implements IDjService {
    @Autowired
    private DjMapper djMapper;



    /**
     * 查询党建管理列表
     *
     * @param dj 党建管理
     * @return 党建管理
     */
    @Override
    public List<Dj> selectDjList(Dj dj) {
        QueryWrapper<Dj> queryWrapper = new QueryWrapper();
        queryWrapper.eq("park_id", dj.getParkId());
        queryWrapper.eq("is_marketable", true);
        return djMapper.selectList(queryWrapper);
    }


}
