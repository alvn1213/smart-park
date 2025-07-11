package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ApplyMoveInFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 注册迁入申请上传文件Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Mapper
public interface ApplyMoveInFileMapper extends BaseMapper<ApplyMoveInFile> {

    /**
     * 根据迁入申请id删除
     * @param moveInId
     * @return
     */
    int deleteByApplyMoveInId(Long moveInId);

}
