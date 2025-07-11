package cn.dtransfer.system.oss;

import cn.dtransfer.system.service.IConfigService;
import com.alibaba.fastjson.JSON;
import cn.dtransfer.common.utils.spring.SpringUtils;

/**
 * 文件上传Factory
 *
 */
public final class OSSFactory {
    private static IConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (IConfigService) SpringUtils.getBean(IConfigService.class);
    }

    public static CloudStorageService build() {
        String jsonConfig = sysConfigService.selectConfigByKey(CloudConstant.CLOUD_STORAGE_CONFIG_KEY);
        // 获取云存储配置信息
        CloudStorageConfig config = JSON.parseObject(jsonConfig, CloudStorageConfig.class);
        if (config.getType() == CloudConstant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == CloudConstant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == CloudConstant.CloudService.QCLOUD.getValue()) {
            return new QcloudCloudStorageService(config);
        }
        return null;
    }
}
