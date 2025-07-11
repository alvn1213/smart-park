package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractRoomService;
import cn.dtransfer.admin.domain.CustomerContractRoom;
import cn.dtransfer.admin.mapper.CustomerContractRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户房间合同Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-30
 */

@Service
public class CustomerContractRoomServiceImpl implements ICustomerContractRoomService {

    @Autowired
    private CustomerContractRoomMapper customerContractRoomMapper;

    @Override
    public int insertCustomerContractRoom(CustomerContractRoom customerContractRoom) {
        return customerContractRoomMapper.insert(customerContractRoom);
    }

    @Override
    public List<CustomerContractRoom> findByContractId(Long contractId) {
        return customerContractRoomMapper.findByContractId(contractId);
    }
}
