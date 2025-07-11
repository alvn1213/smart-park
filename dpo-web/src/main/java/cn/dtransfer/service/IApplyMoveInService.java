package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplyMoveIn;

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
     * 新增注册迁入申请
     *
     * @param applyMoveIn 注册迁入申请
     * @return 结果
     */
    int insertApplyMoveIn(ApplyMoveIn applyMoveIn);

    /**
     * 修改注册迁入申请
     *
     * @param applyMoveIn 注册迁入申请
     * @return 结果
     */
    int updateApplyMoveIn(ApplyMoveIn applyMoveIn);



    /**
     * 查询注册迁入申请
     *
     * @param createUserId 注册迁入申请ID
     * @return 注册迁入申请
     */
    List<ApplyMoveIn> selectApplyMoveInByCreateUserId(Long createUserId);

}
