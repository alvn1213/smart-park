package cn.dtransfer.service.impl;

import cn.dtransfer.service.IPolicyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Policy;
import cn.dtransfer.admin.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 政策管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service("policyAppService")
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements IPolicyService {
    @Autowired
    private PolicyMapper policyMapper;



    /**
     * 查询政策管理列表
     *
     * @param policy 政策管理
     * @return 政策管理
     */
    @Override
    public List<Policy> selectPolicyList(Policy policy) {
        QueryWrapper<Policy> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("park_id", policy.getParkId());
        queryWrapper.eq("is_marketable", true);
        return policyMapper.selectList(queryWrapper);
    }



}
