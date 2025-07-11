package cn.dtransfer.service;

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
     * 查询政策管理列表
     *
     * @param policy 政策管理
     * @return 政策管理集合
     */
    List<Policy> selectPolicyList(Policy policy);



}
