package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IApplySettleService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.admin.domain.ApplySettleFile;
import cn.dtransfer.admin.mapper.ApplySettleFileMapper;
import cn.dtransfer.admin.mapper.ApplySettleMapper;
import cn.dtransfer.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 入驻申请Service业务层处理
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Service
public class ApplySettleServiceImpl extends ServiceImpl<ApplySettleMapper, ApplySettle> implements IApplySettleService {
    @Autowired
    private ApplySettleMapper applySettleMapper;

    @Autowired
    private ApplySettleFileMapper applySettleFileMapper;

    /**
     * 查询入驻申请
     *
     * @param id 入驻申请ID
     * @return 入驻申请
     */
    @Override
    public ApplySettle selectApplySettleById(Long id) {
        return applySettleMapper.selectById(id);
    }

    /**
     * 查询入驻申请列表
     *
     * @param applySettle 入驻申请
     * @return 入驻申请
     */
    @Override
    public List<ApplySettle> selectApplySettleList(ApplySettle applySettle) {
        QueryWrapper<ApplySettle> queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(applySettle.getName())) {
            queryWrapper.like("name", applySettle.getName());
        }
        if (StringUtils.isNotEmpty(applySettle.getPhone())) {
            queryWrapper.eq("phone", applySettle.getPhone());
        }
        if (StringUtils.isNotEmpty(applySettle.getUserName())) {
            queryWrapper.eq("user_name", applySettle.getUserName());
        }
        if (applySettle.getStatus() != null) {
            queryWrapper.eq("status", applySettle.getStatus().getValue());
        }
        queryWrapper.eq("delete_flag", 0);
        return applySettleMapper.selectList(queryWrapper);
    }


    /**
     * 修改入驻申请
     *
     * @param applySettle 入驻申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateApplySettle(ApplySettle applySettle) {
        int result = applySettleMapper.updateById(applySettle);
        applySettleFileMapper.deleteByApplySettleId(applySettle.getId());
        if (CollectionUtil.isNotEmpty(applySettle.getApplySettleFileList())) {
            for (ApplySettleFile applySettleFile : applySettle.getApplySettleFileList()) {
                applySettleFile.setApplySettleId(applySettle.getId());
                applySettleFileMapper.insert(applySettleFile);
            }
        }
        return result;
    }

    /**
     * 删除入驻申请对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteApplySettleByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return applySettleMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 批量审批
     * @param ids 需要审批的数据ID
     * @return
     */
    @Override
    public int approveApplySettleByIds(String ids, ApplySettle.Status status, String remark) {
        ApplySettle applySettle = new ApplySettle();
        applySettle.setStatus(status);
        applySettle.setRemark(remark);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id",ids.split(","));
        return applySettleMapper.update(applySettle, queryWrapper);
    }

    /**
     * 取消审批
     * @param id
     * @return
     */
    @Override
    public int cancelApprove(Long id) {
        ApplySettle applySettle = new ApplySettle();
        applySettle.setStatus(ApplySettle.Status.APPROVING);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        return applySettleMapper.update(applySettle, queryWrapper);
    }


}
