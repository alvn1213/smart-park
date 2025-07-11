package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ServiceSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 供应商管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Mapper
public interface ServiceSupplierMapper extends BaseMapper<ServiceSupplier> {

    /**
     * 查询供应商管理
     *
     * @param id 供应商管理ID
     * @return 供应商管理
     */
    ServiceSupplier selectServiceSupplierById(Long id);
}
