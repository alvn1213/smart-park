package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IPolicyService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements IPolicyService {
    @Autowired
    private PolicyMapper policyMapper;

    /**
     * 查询政策管理
     *
     * @param id 政策管理ID
     * @return 政策管理
     */
    @Override
    public Policy selectPolicyById(Long id) {
        return policyMapper.selectById(id);
    }

    /**
     * 查询政策管理列表
     *
     * @param policy 政策管理
     * @return 政策管理
     */
    @Override
    public List<Policy> selectPolicyList(Policy policy) {
        QueryWrapper<Policy> queryWrapper =new QueryWrapper<>();
        return policyMapper.selectList(queryWrapper);
    }

    /**
     * 新增政策管理
     *
     * @param policy 政策管理
     * @return 结果
     */
    @Override
    public int insertPolicy(Policy policy) {
        policy.setIsMarketable(false);
        return policyMapper.insert(policy);
    }

    /**
     * 修改政策管理
     *
     * @param policy 政策管理
     * @return 结果
     */
    @Override
    public int updatePolicy(Policy policy) {
        return policyMapper.updateById(policy);
    }

    /**
     * 删除政策管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePolicyByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return policyMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除政策管理信息
     *
     * @param id 政策管理ID
     * @return 结果
     */
    @Override
    public int deletePolicyById(Long id) {
        return policyMapper.deleteById(id);
    }

}
