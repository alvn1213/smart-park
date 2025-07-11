package cn.dtransfer.admin.service;

import cn.dtransfer.admin.domain.CustomerContractRoom;

import java.util.List;

/**
 * 客户房间合同Service接口
 *
 * @author dtransfer
 * @date 2024-03-30
 */
public interface ICustomerContractRoomService {


    /**
     * 新增客户房间合同关系
     *
     * @param customerContractRoom 房间合同关系
     * @return 结果
     */
    int insertCustomerContractRoom(CustomerContractRoom customerContractRoom);


    /**
     * 根据合同id查询房间合同绑定关系
     * @param contractId
     * @return
     */
    List<CustomerContractRoom> findByContractId(Long contractId);



}
