package cn.dtransfer.service.impl;

import cn.dtransfer.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.Clue;
import cn.dtransfer.admin.mapper.ApplyRoomMapper;
import cn.dtransfer.admin.mapper.ClueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 线索管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-03-23
 */
@Service("clueAppService")
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ApplyRoomMapper applyRoomMapper;



    /**
     * 新增线索管理
     *
     * @param clue 线索管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertClue(Clue clue) {
        clue.setAssignmentStatus("0");
        clue.setCustomerStatus("0");
        if(clue.getApplyRoom()!=null) {
            applyRoomMapper.insert(clue.getApplyRoom());
        }
        return clueMapper.insert(clue);
    }


}
