package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.CustomerContractRefundRoom;

import java.util.List;

/**
 * 退租房间关联Service接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
public interface ICustomerContractRefundRoomService extends IService<CustomerContractRefundRoom> {
    /**
     * 查询退租房间关联
     *
     * @param id 退租房间关联ID
     * @return 退租房间关联
     */
    CustomerContractRefundRoom selectCustomerContractRefundRoomById(Long id);

    /**
     * 查询退租房间关联列表
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 退租房间关联集合
     */
    List<CustomerContractRefundRoom> selectCustomerContractRefundRoomList(CustomerContractRefundRoom customerContractRefundRoom);

    /**
     * 新增退租房间关联
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 结果
     */
    int insertCustomerContractRefundRoom(CustomerContractRefundRoom customerContractRefundRoom);

    /**
     * 修改退租房间关联
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 结果
     */
    int updateCustomerContractRefundRoom(CustomerContractRefundRoom customerContractRefundRoom);

    /**
     * 批量删除退租房间关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCustomerContractRefundRoomByIds(String ids);

    /**
     * 删除退租房间关联信息
     *
     * @param id 退租房间关联ID
     * @return 结果
     */
    int deleteCustomerContractRefundRoomById(Long id);
}
