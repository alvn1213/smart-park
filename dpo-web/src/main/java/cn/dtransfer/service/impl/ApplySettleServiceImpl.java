package cn.dtransfer.service.impl;

import cn.dtransfer.service.IApplySettleService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplySettle;
import cn.dtransfer.admin.domain.ApplySettleFile;
import cn.dtransfer.admin.mapper.ApplySettleFileMapper;
import cn.dtransfer.admin.mapper.ApplySettleMapper;
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
@Service("applySettleAppService")
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
     * 新增入驻申请
     *
     * @param applySettle 入驻申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertApplySettle(ApplySettle applySettle) {
        int result = applySettleMapper.insert(applySettle);
        if (CollectionUtil.isNotEmpty(applySettle.getApplySettleFileList())) {
            for (ApplySettleFile applySettleFile : applySettle.getApplySettleFileList()) {
                applySettleFile.setApplySettleId(applySettle.getId());
                applySettleFileMapper.insert(applySettleFile);
            }
        }
        return result;
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
        if (CollectionUtil.isNotEmpty(applySettle.getApplySettleFileList())) {
            applySettleFileMapper.deleteByApplySettleId(applySettle.getId());
            for (ApplySettleFile applySettleFile : applySettle.getApplySettleFileList()) {
                applySettleFile.setApplySettleId(applySettle.getId());
                applySettleFileMapper.insert(applySettleFile);
            }
        }
        return result;
    }

    /**
     * 根据用户id查询入驻申请
     * @param createUserId
     * @return
     */
    @Override
    public List<ApplySettle> selectApplySettleByCurrentUser(Long createUserId) {
        return applySettleMapper.selectApplySettleByUSerId(createUserId);
    }

}
