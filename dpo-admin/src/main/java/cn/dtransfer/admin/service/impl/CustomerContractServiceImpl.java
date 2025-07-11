package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.domain.*;
import cn.dtransfer.admin.service.ICustomerContractService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.*;
import cn.dtransfer.admin.mapper.CustomerContractBillMapper;
import cn.dtransfer.admin.mapper.CustomerContractLogMapper;
import cn.dtransfer.admin.mapper.CustomerContractMapper;
import cn.dtransfer.admin.mapper.CustomerContractRoomMapper;
import cn.dtransfer.common.constant.Constants;
import cn.dtransfer.common.core.text.Convert;
import cn.dtransfer.common.enums.BillType;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.RandomUtil;
import cn.dtransfer.system.domain.vo.CurrentUserVO;
import cn.dtransfer.system.service.ICurrentUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户合同Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-29
 */
@Service
public class CustomerContractServiceImpl extends ServiceImpl<CustomerContractMapper, CustomerContract> implements ICustomerContractService {

    @Autowired
    private CustomerContractMapper customerContractMapper;

    @Autowired
    private CustomerContractRoomMapper customerContractRoomMapper;

    @Autowired
    private CustomerContractBillMapper customerContractBillMapper;

    @Autowired
    private CustomerContractLogMapper customerContractLogMapper;

    @Autowired
    private ICurrentUserService currentUserService;


    /**
     * 查询客户合同
     *
     * @param id 客户合同ID
     * @return 客户合同
     */
    @Override
    public CustomerContract selectCustomerContractById(Long id) {
        CustomerContract customerContract = customerContractMapper.selectCustomerContractById(id);
        List<CustomerContractBill> customerContractBillList = customerContract.getCustomerContractBills();
        // 固定账单
        customerContract.setCustomerContractBills(customerContractBillList.stream().filter(r -> (r.getBillType() == null || r.getBillType().equals(BillType.GENERAL))).collect(Collectors.toList()));
        // 其他账单
        customerContract.setCustomerContractOtherBills(customerContractBillList.stream().filter(r -> (r.getBillType() != null && r.getBillType().equals(BillType.OTHER))).collect(Collectors.toList()));
        return customerContract;
    }

    /**
     * 查询客户合同列表
     *
     * @param customerContract 客户合同
     * @return 客户合同
     */
    @Override
    public List<CustomerContract> selectCustomerContractList(CustomerContract customerContract) {
        return customerContractMapper.selectCustomerContractList(customerContract);
    }

    /**
     * 新增客户合同
     *
     * @param customerContract 客户合同
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomerContract(CustomerContract customerContract) {
        // 如果是变更合同调用，将会根据原有合同产生新的合同，并将原有合同作废
        if (customerContract.getId() != null) {
            CustomerContract pCustomerContract = this.selectCustomerContractById(customerContract.getId());
            this.voided(pCustomerContract.getId().toString());
            // 变更后的合同版本自动加1
            customerContract.setVersion((pCustomerContract.getVersion() == null ? 0 : pCustomerContract.getVersion()) + 1);
            customerContract.setId(null);
        }
        customerContract.setCreateTime(DateUtils.getNowDate());
        List<Room> customerContractRooms = customerContract.getCustomerContractRooms();
        int result = customerContractMapper.insert(customerContract);
        if (CollectionUtil.isNotEmpty(customerContractRooms)) {
            insertCustomerContractRooms(customerContract, customerContractRooms);
        }
        return result;
    }


    /**
     * 修改客户合同
     *
     * @param customerContract 客户合同
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCustomerContract(CustomerContract customerContract) {
        CustomerContract pCustomerContract = this.selectCustomerContractById(customerContract.getId());
        Assert.notNull(pCustomerContract,"合同不存在！");
        customerContract.setUpdateTime(DateUtils.getNowDate());

        List<Room> customerContractRooms = customerContract.getCustomerContractRooms();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("contract_id", customerContract.getId());
        customerContractRoomMapper.delete(queryWrapper);
        if (CollectionUtil.isNotEmpty(customerContractRooms)) {
            insertCustomerContractRooms(customerContract, customerContractRooms);
        }
        List<CustomerContractBill> customerContractBills = customerContract.getCustomerContractBills();
        if (CollectionUtil.isNotEmpty(customerContractBills)) {
            QueryWrapper billQueryWrapper = new QueryWrapper();
            billQueryWrapper.eq("contract_id", customerContract.getId());
            customerContractBillMapper.delete(billQueryWrapper);
            for (CustomerContractBill customerContractBill : customerContractBills){
                customerContractBill.setParkId(customerContract.getParkId());
                customerContractBill.setTenantId(customerContract.getTenantId());
                customerContractBill.setSn(RandomUtil.generate_sn(Constants.BILL_PREFIX));
                customerContractBill.setManageSn(customerContract.getManageSn());
                customerContractBill.setStatus(CustomerContractBill.Status.UNRECEIVED);
                customerContractBill.setContractId(customerContract.getId());
                customerContractBill.setCustomerId(customerContract.getCustomerId());
                customerContractBill.setCreateBy(pCustomerContract.getCreateBy());
                customerContractBill.setCreateTime(pCustomerContract.getCreateTime());
                customerContractBill.setUpdateTime(customerContract.getUpdateTime());
                customerContractBill.setUpdateBy(customerContract.getUpdateBy());
                customerContractBill.setBillType(BillType.GENERAL);
                customerContractBill.setDeposit(customerContractBill.getDeposit() == null ? BigDecimal.ZERO : customerContractBill.getDeposit());
                customerContractBill.setRentOverdue(customerContractBill.getRentOverdue() == null ? BigDecimal.ZERO : customerContractBill.getRentOverdue());
                customerContractBillMapper.insert(customerContractBill);
            }
        }
        return customerContractMapper.updateById(customerContract);
    }

    /**
     * 删除客户合同对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCustomerContractByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return customerContractMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 判断是否存在
     *
     * @param property 属性名称
     * @param value    属性值
     * @return 是否存在
     */
    @Override
    public boolean exists(String property, Object value) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(property) && value != null) {
            queryWrapper.eq(property, value);
        }
        return customerContractMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 判断是否唯一
     *
     * @param id       ID
     * @param property 属性名称
     * @param value    属性值
     * @return 是否唯一
     */
    @Override
    public boolean unique(Long id, String property, Object value) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(property) && value != null) {
            queryWrapper.eq(property, value);
        }
        if (id != null) {
            queryWrapper.ne("id", id);
        }
        return customerContractMapper.selectCount(queryWrapper) > 0;
    }


    /**
     * 提交合同
     *
     * @param ids 合同ids
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int commit(String ids) {
        // 更改房间的租赁状态-已租
        customerContractRoomMapper.updateRoomStatus(Convert.toStrArray(ids), Room.Status.YES);
        return this.updateContractsStatus(ids, CustomerContract.Status.APPROVING);
    }


    /**
     * 修改合同状态
     *
     * @param ids 合同ids
     * @param status 合同状态
     * @return 结果
     */
    @Override
    public int updateContractsStatus(String ids, CustomerContract.Status status) {
        return customerContractMapper.updateContractsStatus(Convert.toStrArray(ids), status);
    }


    /**
     * 审批合同
     *
     * @param ids 合同ids
     * @param status 合同状态
     * @param remark 原因
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int approveContract(String ids, CustomerContract.Status status, String remark) {
        String[] arr =  Convert.toStrArray(ids);
        CurrentUserVO currentUserVO = currentUserService.getCurrentUser();
        for (String id : arr) {
            CustomerContractLog customerContractLog = new CustomerContractLog();
            customerContractLog.setContractId(Long.valueOf(id));
            customerContractLog.setStatus(status);
            customerContractLog.setRemark(remark);
            customerContractLog.setCreateBy(currentUserVO.getLoginName());
            customerContractLog.setParkId(currentUserVO.getParkId());
            customerContractLogMapper.insert(customerContractLog);
        }
        // 审批不通过将房间设置为未租
        if (status.equals(CustomerContract.Status.REFUSE)) {
            customerContractRoomMapper.updateRoomStatus(Convert.toStrArray(ids), Room.Status.NO);
        }
        return this.updateContractsStatus(ids,status);
    }


    /**
     * 作废合同
     *
     * @param ids 合同ids
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int voided(String ids) {
        // 更改房间的租赁状态-未租
        customerContractRoomMapper.updateRoomStatus(Convert.toStrArray(ids), Room.Status.NO);
        // 未收款的单据直接作废
        customerContractBillMapper.voidedContractBillByContractIds(Convert.toStrArray(ids));
        return this.updateContractsStatus(ids, CustomerContract.Status.VOIDED);
    }

    //
    private void insertCustomerContractRooms(CustomerContract customerContract, List<Room> customerContractRooms) {
        for (Room item : customerContractRooms) {
            CustomerContractRoom customerContractRoom = new CustomerContractRoom();
            customerContractRoom.setContractId(customerContract.getId());
            customerContractRoom.setRoomId(item.getId());
            customerContractRoomMapper.insert(customerContractRoom);
        }
    }
}
