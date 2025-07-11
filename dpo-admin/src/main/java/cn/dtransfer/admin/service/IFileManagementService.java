package cn.dtransfer.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dtransfer.admin.domain.FileManagement;

import java.util.List;

/**
 * 文件管理Service接口
 *
 * @author dtransfer
 * @date 2024-05-24
 */
public interface IFileManagementService extends IService<FileManagement> {
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

    /**
     * 新增文件管理
     *
     * @param fileManagement 文件管理
     * @return 结果
     */
    int insertFileManagement(FileManagement fileManagement);

    /**
     * 修改文件管理
     *
     * @param fileManagement 文件管理
     * @return 结果
     */
    int updateFileManagement(FileManagement fileManagement);

    /**
     * 批量删除文件管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteFileManagementByIds(String ids);

    /**
     * 删除文件管理信息
     *
     * @param id 文件管理ID
     * @return 结果
     */
    int deleteFileManagementById(Long id);
}
