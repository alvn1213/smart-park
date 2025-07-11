package cn.dtransfer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.ApplyPark;

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
     * 新增入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    int insertApplyPark(ApplyPark applyPark);

    /**
     * 修改入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    int updateApplyPark(ApplyPark applyPark);


    /**
     * 根据用户id查询入园申请单
     * @param createUserId
     * @return
     */
    List<ApplyPark> selectApplyParkByCurrentUser(Long createUserId);


}
