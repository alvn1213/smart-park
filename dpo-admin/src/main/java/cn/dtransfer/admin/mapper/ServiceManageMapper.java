package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.ServiceManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@Mapper
public interface ServiceManageMapper extends BaseMapper<ServiceManage> {
    /**
     * 查询服务管理
     *
     * @param id 服务管理ID
     * @return 服务管理
     */
    ServiceManage selectServiceManageById(@Param("id") Long id);

    /**
     * 查询服务管理列表
     *
     * @param serviceManage 服务管理
     * @return 服务管理集合
     */
    List<ServiceManage> selectServiceManageList(ServiceManage serviceManage);


}
