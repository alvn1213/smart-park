package cn.dtransfer.service.impl;

import cn.dtransfer.service.IApplyMoveInService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplyMoveIn;
import cn.dtransfer.admin.domain.ApplyMoveInFile;
import cn.dtransfer.admin.mapper.ApplyMoveInFileMapper;
import cn.dtransfer.admin.mapper.ApplyMoveInMapper;
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
@Service("applyMoveInAppService")
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
     * 新增注册迁入申请
     *
     * @param applyMoveIn 注册迁入申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertApplyMoveIn(ApplyMoveIn applyMoveIn) {
        int result = applyMoveInMapper.insert(applyMoveIn);
        List<ApplyMoveInFile> applyMoveInFileList = applyMoveIn.getApplyMoveInFileList();
        if (CollectionUtil.isNotEmpty(applyMoveInFileList)) {
            for (ApplyMoveInFile applyMoveInFile : applyMoveInFileList) {
                applyMoveInFile.setMoveInId(applyMoveIn.getId());
                applyMoveInFileMapper.insert(applyMoveInFile);
            }
        }
        return result;
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
     *
     * @param createUserId 注册迁入申请ID
     * @return
     */
    @Override
    public List<ApplyMoveIn> selectApplyMoveInByCreateUserId(Long createUserId) {
        return applyMoveInMapper.selectApplyMoveInByUserId(createUserId);
    }

}
