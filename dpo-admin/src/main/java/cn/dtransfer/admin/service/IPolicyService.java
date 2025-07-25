package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.Policy;

import java.util.List;

/**
 * 政策管理Service接口
 *
 * @author dtransfer
 * @date 2024-03-23
 */
public interface IPolicyService extends IService<Policy> {
    /**
     * 查询政策管理
     *
     * @param id 政策管理ID
     * @return 政策管理
     */
    Policy selectPolicyById(Long id);

    /**
     * 查询政策管理列表
     *
     * @param policy 政策管理
     * @return 政策管理集合
     */
    List<Policy> selectPolicyList(Policy policy);

    /**
     * 新增政策管理
     *
     * @param policy 政策管理
     * @return 结果
     */
    int insertPolicy(Policy policy);

    /**
     * 修改政策管理
     *
     * @param policy 政策管理
     * @return 结果
     */
    int updatePolicy(Policy policy);

    /**
     * 批量删除政策管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePolicyByIds(String ids);

    /**
     * 删除政策管理信息
     *
     * @param id 政策管理ID
     * @return 结果
     */
    int deletePolicyById(Long id);


}
