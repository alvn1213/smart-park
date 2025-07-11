package cn.dtransfer.admin.service.impl;

import cn.dtransfer.admin.service.IFileManagementService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.dtransfer.admin.domain.FileManagement;
import cn.dtransfer.admin.mapper.FileManagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件管理Service业务层处理
 *
 * @author dtransfer
 * @date 2024-05-24
 */
@Service
public class FileManagementServiceImpl extends ServiceImpl<FileManagementMapper, FileManagement> implements IFileManagementService {
    @Autowired
    private FileManagementMapper fileManagementMapper;

    /**
     * 查询文件管理
     *
     * @param id 文件管理ID
     * @return 文件管理
     */
    @Override
    public FileManagement selectFileManagementById(Long id) {
        return fileManagementMapper.selectById(id);
    }

    /**
     * 查询文件管理列表
     *
     * @param fileManagement 文件管理
     * @return 文件管理
     */
    @Override
    public List<FileManagement> selectFileManagementList(FileManagement fileManagement) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (fileManagement.getFileName() != null) {
            queryWrapper.eq("file_name", fileManagement.getFileName());
        }
        return fileManagementMapper.selectList(queryWrapper);
    }

    /**
     * 新增文件管理
     *
     * @param fileManagement 文件管理
     * @return 结果
     */
    @Override
    public int insertFileManagement(FileManagement fileManagement) {
        return fileManagementMapper.insert(fileManagement);
    }

    /**
     * 修改文件管理
     *
     * @param fileManagement 文件管理
     * @return 结果
     */
    @Override
    public int updateFileManagement(FileManagement fileManagement) {
        return fileManagementMapper.updateById(fileManagement);
    }

    /**
     * 删除文件管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileManagementByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return fileManagementMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

    /**
     * 删除文件管理信息
     *
     * @param id 文件管理ID
     * @return 结果
     */
    @Override
    public int deleteFileManagementById(Long id) {
        return fileManagementMapper.deleteById(id);
    }
}
