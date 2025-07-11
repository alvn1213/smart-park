package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplyPark;
import cn.dtransfer.admin.domain.ApplySettle;

import java.util.List;

/**
 * 入园申请Service接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
public interface IApplyParkService extends IService<ApplyPark> {
    /**
     * 查询入园申请
     *
     * @param id 入园申请ID
     * @return 入园申请
     */
    ApplyPark selectApplyParkById(Long id);

    /**
     * 查询入园申请列表
     *
     * @param applyPark 入园申请
     * @return 入园申请集合
     */
    List<ApplyPark> selectApplyParkList(ApplyPark applyPark);


    /**
     * 修改入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    int updateApplyPark(ApplyPark applyPark);

    /**
     * 批量删除入园申请
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApplyParkByIds(String ids);



    /**
     * 批量审批入园申请
     *
     * @param ids 需要审批的数据ID
     * @return 结果
     */
    int approveApplyParkByIds(String ids, ApplySettle.Status status, String remark);


    /**
     * 取消审批
     * @param id
     * @return
     */
    int cancelApprove(Long id);


}
