package cn.dtransfer.system.controller;

import cn.dtransfer.system.oss.CloudConstant;
import cn.dtransfer.system.oss.CloudStorageConfig;
import cn.dtransfer.system.oss.CloudStorageService;
import cn.dtransfer.system.oss.OSSFactory;
import cn.dtransfer.system.service.IConfigService;
import cn.dtransfer.system.service.IOssService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.exception.file.OssException;
import cn.dtransfer.common.utils.ValidatorUtils;
import cn.dtransfer.system.domain.Oss;
import cn.dtransfer.system.oss.valdator.AliyunGroup;
import cn.dtransfer.system.oss.valdator.QcloudGroup;
import cn.dtransfer.system.oss.valdator.QiniuGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.io.IOException;
import java.util.Date;

/**
 * 文件上传 提供者
 *
 */
@RestController
@RequestMapping("system/oss")
public class OssController extends BaseController {

    private final static String         KEY = CloudConstant.CLOUD_STORAGE_CONFIG_KEY;
    @Autowired
    private IOssService ossService;
    @Autowired
    private IConfigService configService;

    /**
     * 查询文件上传
     */
    @GetMapping("get/{id}")
    public Oss get(@PathVariable("id") Long id) {
        return ossService.selectSysOssById(id);
    }

    /**
     * 云存储配置信息
     */
    @RequiresPermissions("system:oss:config")
    @RequestMapping("config")
    public CloudStorageConfig config() {
        String jsonConfig = configService.selectConfigByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = JSON.parseObject(jsonConfig, CloudStorageConfig.class);
        return config;
    }

    /**
     * 查询文件上传列表
     */
    @RequiresPermissions("system:oss:list")
    @GetMapping("list")
    public R list(Oss oss) {
        startPage();
        return result(ossService.selectSysOssList(oss));
    }

    /**
     * 修改保存文件上传
     *
     * @throws IOException
     */
    @RequiresPermissions("system:oss:add")
    @PostMapping("upload")
    public R editSave(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new OssException("上传文件不能为空");
        }
        // 上传文件
        String fileName = file.getOriginalFilename();
        String              suffix  = fileName.substring(fileName.lastIndexOf("."));
        CloudStorageService storage = OSSFactory.build();
        String              url     = storage.uploadSuffix(file.getBytes(), suffix);
        // 保存文件信息
        Oss ossEntity = new Oss();
        ossEntity.setUrl(url);
        ossEntity.setFileSuffix(suffix);
        ossEntity.setCreateBy(getLoginName());
        ossEntity.setFileName(fileName);
        ossEntity.setCreateTime(new Date());
        ossEntity.setService(storage.getService());
        return toAjax(ossService.insertSysOss(ossEntity));
    }

    /**
     * 修改
     */
    @RequiresPermissions("system:oss:edit")
    @PostMapping("update")
    public R editSave(@RequestBody Oss oss) {
        return toAjax(ossService.updateSysOss(oss));
    }

    /**
     * 保存云存储配置信息
     */
    @RequiresPermissions("system:oss:config")
    @RequestMapping("saveConfig")
    public R saveConfig(CloudStorageConfig config) {
        // 校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == CloudConstant.CloudService.QINIU.getValue()) {
            // 校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == CloudConstant.CloudService.ALIYUN.getValue()) {
            // 校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == CloudConstant.CloudService.QCLOUD.getValue()) {
            // 校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }
        return toAjax(configService.updateValueByKey(KEY, new Gson().toJson(config)));
    }

    /**
     * 删除文件上传
     */
    @RequiresPermissions("system:oss:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(ossService.deleteSysOssByIds(ids));
    }
}
