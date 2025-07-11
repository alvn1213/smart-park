package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IApplyMoveInService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplyMoveIn;
import cn.dtransfer.admin.domain.ApplyMoveInFile;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.admin.mapper.ApplyMoveInFileMapper;
import cn.dtransfer.admin.mapper.ApplyMoveInMapper;
import cn.dtransfer.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 注册迁入申请Service业务层处理
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Service
public class ApplyMoveInServiceImpl extends ServiceImpl<ApplyMoveInMapper, ApplyMoveIn> implements IApplyMoveInService {
    @Autowired
    private ApplyMoveInMapper applyMoveInMapper;

    @Autowired
    private ApplyMoveInFileMapper applyMoveInFileMapper;

    /**
     * 查询注册迁入申请
     *
     * @param id 注册迁入申请ID
     * @return 注册迁入申请
     */
    @Override
    public ApplyMoveIn selectApplyMoveInById(Long id) {
        return applyMoveInMapper.selectById(id);
    }

    /**
     * 查询注册迁入申请列表
     *
     * @param applyMoveIn 注册迁入申请
     * @return 注册迁入申请
     */
    @Override
    public List<ApplyMoveIn> selectApplyMoveInList(ApplyMoveIn applyMoveIn) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(applyMoveIn.getApplyName())) {
            queryWrapper.like("apply_name", applyMoveIn.getApplyName());
        }
        if (StringUtils.isNotEmpty(applyMoveIn.getPhone())) {
            queryWrapper.eq("phone", applyMoveIn.getPhone());
        }
        queryWrapper.eq("delete_flag", 0);
        return applyMoveInMapper.selectList(queryWrapper);
    }


    /**
     * 修改注册迁入申请
     *
     * @param applyMoveIn 注册迁入申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateApplyMoveIn(ApplyMoveIn applyMoveIn) {
        int result = applyMoveInMapper.updateById(applyMoveIn);
        List<ApplyMoveInFile> applyMoveInFileList = applyMoveIn.getApplyMoveInFileList();
        if (CollectionUtil.isNotEmpty(applyMoveInFileList)) {
            applyMoveInFileMapper.deleteByApplyMoveInId(applyMoveIn.getId());
            for (ApplyMoveInFile applyMoveInFile : applyMoveInFileList) {
                applyMoveInFile.setMoveInId(applyMoveIn.getId());
                applyMoveInFileMapper.insert(applyMoveInFile);
            }
        }
        return result;
    }

    /**
     * 删除注册迁入申请对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteApplyMoveInByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return applyMoveInMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }




    /**
     *
     * @param ids 需要审批的数据ID
     * @param status
     * @return
     */
    @Override
    public int approveApplyMoveInByIds(String ids, ApplySettle.Status status, String remark) {
        ApplyMoveIn applyMoveIn = new ApplyMoveIn();
        applyMoveIn.setStatus(status);
        applyMoveIn.setRemark(remark);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("id", ids.split(","));
        return applyMoveInMapper.update(applyMoveIn, queryWrapper);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public int cancelApprove(Long id) {
        ApplyMoveIn applyMoveIn = new ApplyMoveIn();
        applyMoveIn.setStatus(ApplySettle.Status.APPROVING);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        return applyMoveInMapper.update(applyMoveIn, queryWrapper);
    }

}
