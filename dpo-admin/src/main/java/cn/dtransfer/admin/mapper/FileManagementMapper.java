package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.FileManagement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件管理Mapper接口
 *
 * @author dtransfer
 * @date 2024-05-24
 */
@Mapper
public interface FileManagementMapper extends BaseMapper<FileManagement> {
    /**
     * 查询文件管理
     *
     * @param id 文件管理ID
     * @return 文件管理
     */
    FileManagement selectFileManagementById(Long id);

    /**
     * 查询文件管理列表
     *
     * @param fileManagement 文件管理
     * @return 文件管理集合
     */
    List<FileManagement> selectFileManagementList(FileManagement fileManagement);

}
