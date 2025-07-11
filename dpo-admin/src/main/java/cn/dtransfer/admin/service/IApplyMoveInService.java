package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplyMoveIn;
import cn.dtransfer.admin.domain.ApplySettle;

import java.util.List;

/**
 * 注册迁入申请Service接口
 *
 * @author dtransfer
 * @date 2024-04-13
 */
public interface IApplyMoveInService extends IService<ApplyMoveIn> {
    /**
     * 查询注册迁入申请
     *
     * @param id 注册迁入申请ID
     * @return 注册迁入申请
     */
    ApplyMoveIn selectApplyMoveInById(Long id);

    /**
     * 查询注册迁入申请列表
     *
     * @param applyMoveIn 注册迁入申请
     * @return 注册迁入申请集合
     */
    List<ApplyMoveIn> selectApplyMoveInList(ApplyMoveIn applyMoveIn);


    /**
     * 修改注册迁入申请
     *
     * @param applyMoveIn 注册迁入申请
     * @return 结果
     */
    int updateApplyMoveIn(ApplyMoveIn applyMoveIn);

    /**
     * 批量删除注册迁入申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApplyMoveInByIds(String ids);



    /**
     * 批量审批迁入申请
     *
     * @param ids 需要审批的数据ID
     * @return 结果
     */
    int approveApplyMoveInByIds(String ids, ApplySettle.Status status, String remark);


    /**
     * 取消审批
     * @param id
     * @return
     */
    int cancelApprove(Long id);

}
