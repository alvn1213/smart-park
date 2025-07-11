package cn.dtransfer.system.mapper;

import cn.dtransfer.system.domain.Sn;
import org.apache.ibatis.annotations.Mapper;

/**
 * 序列号Mapper接口
 *
 * @author ryuser
 * @date 2024-05-12
 */
@Mapper
public interface SnMapper {

    /**
     * 查询序列号
     *
     * @param type 序列号类型
     * @return 序列号
     */
    Sn selectSnByType(Sn.Type type);

    /**
     * 修改序列号
     *
     * @param sn 序列号
     * @return 结果
     */
    int updateSn(Sn sn);

}
