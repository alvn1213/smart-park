package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.ICustomerContractRefundRoomService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.CustomerContractRefundRoom;
import cn.dtransfer.admin.mapper.CustomerContractRefundRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退租房间关联Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Service
public class CustomerContractRefundRoomServiceImpl extends ServiceImpl<CustomerContractRefundRoomMapper, CustomerContractRefundRoom> implements ICustomerContractRefundRoomService {
    @Autowired
    private CustomerContractRefundRoomMapper customerContractRefundRoomMapper;

    /**
     * 查询退租房间关联
     *
     * @param id 退租房间关联ID
     * @return 退租房间关联
     */
    @Override
    public CustomerContractRefundRoom selectCustomerContractRefundRoomById(Long id) {
        return customerContractRefundRoomMapper.selectCustomerContractRefundRoomById(id);
    }

    /**
     * 查询退租房间关联列表
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 退租房间关联
     */
    @Override
    public List<CustomerContractRefundRoom> selectCustomerContractRefundRoomList(CustomerContractRefundRoom customerContractRefundRoom) {
        QueryWrapper queryWrapper = new QueryWrapper();
        return customerContractRefundRoomMapper.selectCustomerContractRefundRoomList(customerContractRefundRoom);
    }

    /**
     * 新增退租房间关联
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 结果
     */
    @Override
    public int insertCustomerContractRefundRoom(CustomerContractRefundRoom customerContractRefundRoom) {
        return customerContractRefundRoomMapper.insert(customerContractRefundRoom);
    }

    /**
     * 修改退租房间关联
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 结果
     */
    @Override
    public int updateCustomerContractRefundRoom(CustomerContractRefundRoom customerContractRefundRoom) {
        return customerContractRefundRoomMapper.updateById(customerContractRefundRoom);
    }

    /**
     * 删除退租房间关联对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractRefundRoomByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractRefundRoomMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除退租房间关联信息
     *
     * @param id 退租房间关联ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractRefundRoomById(Long id) {
        return customerContractRefundRoomMapper.deleteById(id);
    }
}
