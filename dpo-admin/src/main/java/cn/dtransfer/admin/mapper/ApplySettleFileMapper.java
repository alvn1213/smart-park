package cn.dtransfer.admin.mapper;


import cn.dtransfer.admin.domain.ApplySettleFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * 入驻申请相关文件Mapper接口
 *
 * @author dtransfer
 * @date 2024-04-13
 */
@Mapper
public interface ApplySettleFileMapper extends BaseMapper<ApplySettleFile> {

    /**
     * 根据主表删除相关附件
     * @param applySettleId
     * @return
     */
    int deleteByApplySettleId(Long applySettleId);

}
