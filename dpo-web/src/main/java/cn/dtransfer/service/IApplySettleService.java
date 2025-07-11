package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplySettle;

import java.util.List;


/**
 * 入驻申请Service接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
public interface IApplySettleService extends IService<ApplySettle> {

    /**
     * 查询入驻申请
     *
     * @param id 入驻申请ID
     * @return 入驻申请
     */
    ApplySettle selectApplySettleById(Long id);

    /**
     * 新增入驻申请
     *
     * @param applySettle 入驻申请
     * @return 结果
     */
    int insertApplySettle(ApplySettle applySettle);

    /**
     * 修改入驻申请
     *
     * @param applySettle 入驻申请
     * @return 结果
     */
    int updateApplySettle(ApplySettle applySettle);


    /**
     * 根据用户id查询入驻申请单
     * @param createUserId
     * @return
     */
    List<ApplySettle> selectApplySettleByCurrentUser(Long createUserId);


}
