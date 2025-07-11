package cn.dtransfer.system.service.impl;

import cn.dtransfer.system.mapper.OssMapper;
import cn.dtransfer.system.service.IOssService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.Oss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件上传 服务层实现
 *
 */
@Service
public class OssServiceImpl implements IOssService {

    @Autowired
    private OssMapper ossMapper;

    /**
     * 查询文件上传信息
     *
     * @param id 文件上传ID
     * @return 文件上传信息
     */
    @Override
    public Oss selectSysOssById(Long id) {
        return ossMapper.selectById(id);
    }

    /**
     * 查询文件上传列表
     *
     * @param oss 文件上传信息
     * @return 文件上传集合
     */
    @Override
    public List<Oss> selectSysOssList(Oss oss) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(oss.getFileName())) {
            queryWrapper.like("fileName", "%" + oss.getFileName() + "%");
        }
        if (StringUtils.isNotBlank(oss.getFileSuffix())) {
            queryWrapper.eq("fileSuffix", oss.getFileSuffix());
        }
        if (StringUtils.isNotBlank(oss.getCreateBy())) {
            queryWrapper.like("createBy", oss.getCreateBy());
        }
        return ossMapper.selectList(queryWrapper);
    }

    /**
     * 新增文件上传
     *
     * @param oss 文件上传信息
     * @return 结果
     */
    @Override
    public int insertSysOss(Oss oss) {
        return ossMapper.insert(oss);
    }

    /**
     * 修改文件上传
     *
     * @param oss 文件上传信息
     * @return 结果
     */
    @Override
    public int updateSysOss(Oss oss) {
        return ossMapper.updateById(oss);
    }

    /**
     * 删除文件上传对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysOssByIds(String ids) {
        String[] idsArray = StrUtil.split(ids,",");
        return ossMapper.deleteBatchIds(CollUtil.toList(idsArray));
    }

}
