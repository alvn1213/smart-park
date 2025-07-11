package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.domain.*;
import cn.dtransfer.admin.service.ICustomerContractBillService;
import cn.dtransfer.admin.service.ICustomerContractRefundRoomService;
import cn.dtransfer.admin.service.ICustomerContractRefundService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.*;
import cn.dtransfer.admin.mapper.CustomerContractBillMapper;
import cn.dtransfer.admin.mapper.CustomerContractMapper;
import cn.dtransfer.admin.mapper.CustomerContractRefundMapper;
import cn.dtransfer.admin.mapper.CustomerContractRoomMapper;
import cn.dtransfer.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 客户合同退租Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Service
public class CustomerContractRefundServiceImpl extends ServiceImpl<CustomerContractRefundMapper, CustomerContractRefund> implements ICustomerContractRefundService {

    @Autowired
    private CustomerContractRefundMapper customerContractRefundMapper;

    @Autowired
    private ICustomerContractRefundRoomService customerContractRefundRoomService;

    @Autowired
    private CustomerContractRoomMapper customerContractRoomMapper;

    @Autowired
    private CustomerContractBillMapper customerContractBillMapper;

    @Autowired
    private ICustomerContractBillService customerContractBillService;

    @Autowired
    private CustomerContractMapper customerContractMapper;

    /**
     * 查询客户合同退租
     *
     * @param id 客户合同退租ID
     * @return 客户合同退租
     */
    @Override
    public CustomerContractRefund selectCustomerContractRefundById(Long id) {
        return customerContractRefundMapper.selectCustomerContractRefundById(id);
    }

    /**
     * 查询客户合同退租列表
     *
     * @param customerContractRefund 客户合同退租
     * @return 客户合同退租
     */
    @Override
    public List<CustomerContractRefund> selectCustomerContractRefundList(CustomerContractRefund customerContractRefund) {
        return customerContractRefundMapper.selectContractRefundList(customerContractRefund);
    }

    /**
     * 新增客户合同退租
     *
     * @param customerContractRefund 客户合同退租
     * @return 结果
     */
    @Override
    public int insertCustomerContractRefund(CustomerContractRefund customerContractRefund) {
        List<CustomerContractRefundRoom> customerContractRefundRooms = customerContractRefund.getCustomerContractRefundRooms();
        if (customerContractRefundRooms != null) {
            for (CustomerContractRefundRoom item : customerContractRefundRooms) {
                item.setRefundId(customerContractRefund.getId());
                customerContractRefundRoomService.insertCustomerContractRefundRoom(item);
            }
        }
        return customerContractRefundMapper.insert(customerContractRefund);
    }

    /**
     * 修改客户合同退租
     *
     * @param customerContractRefund 客户合同退租
     * @return 结果
     */
    @Override
    public int updateCustomerContractRefund(CustomerContractRefund customerContractRefund) {
        customerContractRefund.setStatus(true);
        return customerContractRefundMapper.updateById(customerContractRefund);
    }

    /**
     * 确定退款
     * <p>
     * VOIDED
     *
     * @param ids 合同ids
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int voided(String ids, Long id) {
        // 根据id查询退租的信息
        CustomerContractRefund customerContractRefund = selectCustomerContractRefundById(id);
        // 时间转换格式
        SimpleDateFormat Format = new SimpleDateFormat("yyyy MM");
        String RefundDate = Format.format(customerContractRefund.getRefundDate());
        // 录入退租费用到账单
        List<CustomerContractBill> customerContractBillList = customerContractBillMapper.selectCustomerContractBillContractId(new Long(ids));
        for (CustomerContractBill item : customerContractBillList) {
            // 转换时间格式（yyyy-MM）
            String BillEndDate = Format.format(item.getBillEndDate());
            // 应退金额和其他费用，录入到退租的月份账单中
            if (BillEndDate.equals(RefundDate)) {
                item.setOtherFee(customerContractRefund.getReceiveFee());
                item.setRefundFee(customerContractRefund.getRefundFee());
                customerContractBillService.updateCustomerContractBill(item);
            }
        }
        // 更改房间的租赁状态-未租
        customerContractRoomMapper.updateRoomStatus(Convert.toStrArray(ids), Room.Status.NO);
        // 未收款的单据直接作废
        customerContractBillMapper.voidedContractBillByContractIds(Convert.toStrArray(ids));
        // 更改合同状态
        int result = customerContractMapper.updateContractsStatus(Convert.toStrArray(ids), CustomerContract.Status.END);
        if (result > 0) {
            // 改变退租状态
            this.updateCustomerContractRefund(customerContractRefund);
        }
        return result;
    }

    /**
     * 删除客户合同退租对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractRefundByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractRefundMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除客户合同退租信息
     *
     * @param id 客户合同退租ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractRefundById(Long id) {
        return customerContractRefundMapper.deleteById(id);
    }
}
