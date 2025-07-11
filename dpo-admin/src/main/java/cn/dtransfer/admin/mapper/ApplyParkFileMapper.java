package cn.dtransfer.admin.mapper;


import cn.dtransfer.admin.domain.ApplyParkFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 入园申请相关文件Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-12
 */
@Mapper
public interface ApplyParkFileMapper extends BaseMapper<ApplyParkFile> {

    /**
     * 根据主表删除相关附件
     * @param applyParkId
     * @return
     */
    int deleteByApplyParkId(Long applyParkId);

}
