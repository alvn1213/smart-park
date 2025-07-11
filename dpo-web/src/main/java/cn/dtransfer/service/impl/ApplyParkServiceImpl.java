package cn.dtransfer.service.impl;

import cn.dtransfer.service.IApplyParkService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.ApplyPark;
import cn.dtransfer.admin.domain.ApplyParkFile;
import cn.dtransfer.admin.mapper.ApplyParkFileMapper;
import cn.dtransfer.admin.mapper.ApplyParkMapper;
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
@Service("applyParkAppService")
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
     * 新增入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertApplyPark(ApplyPark applyPark) {
        int result = applyParkMapper.insert(applyPark);
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
     * 修改入园申请
     *
     * @param applyPark 入园申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateApplyPark(ApplyPark applyPark) {
        int result = applyParkMapper.updateById(applyPark);
        List<ApplyParkFile> applyParkFileList = applyPark.getApplyParkFileList();
        if (CollectionUtil.isNotEmpty(applyParkFileList)) {
            applyParkFileMapper.deleteByApplyParkId(applyPark.getId());
            for (ApplyParkFile applyParkFile : applyParkFileList) {
                applyParkFile.setApplyParkId(applyPark.getId());
                applyParkFileMapper.insert(applyParkFile);
            }
        }
        return result;
    }

    /**
     * 根据用户id查询入园申请
     * @param createUserId
     * @return
     */
    @Override
    public List<ApplyPark> selectApplyParkByCurrentUser(Long createUserId) {
        return applyParkMapper.selectApplyParkByUSerId(createUserId);
    }

}
