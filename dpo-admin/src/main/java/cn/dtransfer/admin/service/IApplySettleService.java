package cn.dtransfer.admin.service;

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
     * 查询入驻申请列表
     *
     * @param applySettle 入驻申请
     * @return 入驻申请集合
     */
    List<ApplySettle> selectApplySettleList(ApplySettle applySettle);


    /**
     * 修改入驻申请
     *
     * @param applySettle 入驻申请
     * @return 结果
     */
    int updateApplySettle(ApplySettle applySettle);

    /**
     * 批量删除入驻申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApplySettleByIds(String ids);




    /**
     * 批量审批入驻申请
     *
     * @param ids 需要审批的数据ID
     * @return 结果
     */
    int approveApplySettleByIds(String ids, ApplySettle.Status status, String remark);


    /**
     * 取消审批
     * @param id
     * @return
     */
    int cancelApprove(Long id);


}
