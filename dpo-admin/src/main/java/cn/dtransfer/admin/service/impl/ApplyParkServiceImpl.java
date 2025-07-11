package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IApplyParkService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplyPark;
import cn.dtransfer.admin.domain.ApplyParkFile;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.admin.mapper.ApplyParkFileMapper;
import cn.dtransfer.admin.mapper.ApplyParkMapper;
import cn.dtransfer.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 入园申请Service业务层处理
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Service
public class ApplyParkServiceImpl extends ServiceImpl<ApplyParkMapper, ApplyPark> implements IApplyParkService {
    @Autowired
    private ApplyParkMapper applyParkMapper;

    @Autowired
    private ApplyParkFileMapper applyParkFileMapper;

    /**
     * 查询入园申请
     *
     * @param id 入园申请ID
     * @return 入园申请
     */
    @Override
    public ApplyPark selectApplyParkById(Long id) {
        return applyParkMapper.selectById(id);
    }

    /**
     * 查询入园申请列表
     *
     * @param applyPark 入园申请
     * @return 入园申请
     */
    @Override
    public List<ApplyPark> selectApplyParkList(ApplyPark applyPark) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(applyPark.getName())) {
            queryWrapper.like("name", applyPark.getName());
        }
        if (StringUtils.isNotEmpty(applyPark.getPhone())) {
            queryWrapper.eq("phone", applyPark.getPhone());
        }
        queryWrapper.eq("delete_flag", 0);
        return applyParkMapper.selectList(queryWrapper);
    }


    /**
     * 修改入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateApplyPark(ApplyPark applyPark) {
        int result = applyParkMapper.updateById(applyPark);
        applyParkFileMapper.deleteByApplyParkId(applyPark.getId());
        List<ApplyParkFile> applyParkFileList = applyPark.getApplyParkFileList();
        if (CollectionUtil.isNotEmpty(applyParkFileList)) {
            for (ApplyParkFile applyParkFile : applyParkFileList) {
                applyParkFile.setApplyParkId(applyPark.getId());
                applyParkFileMapper.insert(applyParkFile);
            }
        }
        return result;
    }

    /**
     * 删除入园申请对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteApplyParkByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return applyParkMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }


    /**
     *
     * @param ids 需要审批的数据ID
     * @param status
     * @return
     */
    @Override
    public int approveApplyParkByIds(String ids, ApplySettle.Status status, String remark) {
        ApplyPark applyPark = new ApplyPark();
        applyPark.setStatus(status);
        applyPark.setRemark(remark);
        QueryWrapper queryWrapper = new QueryWrapper();
        String[] idArr = ids.split(",");
        queryWrapper.in("id", idArr);
        return applyParkMapper.update(applyPark, queryWrapper);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public int cancelApprove(Long id) {
        ApplyPark applyPark = new ApplyPark();
        applyPark.setStatus(ApplySettle.Status.APPROVING);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        return applyParkMapper.update(applyPark, queryWrapper);
    }



}
