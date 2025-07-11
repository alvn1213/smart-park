package cn.dtransfer.system.handlers;

import cn.dtransfer.system.service.ICurrentUserService;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import cn.dtransfer.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 填充器
 *
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private ICurrentUserService userOnlineService;

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        if (!metaObject.hasGetter("jobGroup") ) {
            if (Objects.isNull(metaObject.getValue("createBy"))) {
                metaObject.setValue("createBy", userOnlineService.getLoginName());
            }
            if (Objects.isNull(metaObject.getValue("createTime"))) {
                metaObject.setValue("createTime", DateUtils.getNowDate());
            }
            if (Objects.isNull(metaObject.getValue("deleteFlag"))) {
                metaObject.setValue("deleteFlag", 0);
            }
            if (Objects.isNull(metaObject.getValue("version"))) {
                metaObject.setValue("version", 0);
            }
            if (metaObject.hasGetter("tenantId") && Objects.isNull(metaObject.getValue("tenantId"))) {
                metaObject.setValue("tenantId", userOnlineService.getTenantId());
            }
            if (metaObject.hasGetter("parkId") && Objects.isNull(metaObject.getValue("parkId"))) {
                metaObject.setValue("parkId", userOnlineService.getParkId());
            }
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        if (!metaObject.hasGetter("jobGroup") ) {
            metaObject.setValue("updateBy", userOnlineService.getLoginName());
            metaObject.setValue("updateTime", DateUtils.getNowDate());
        }
    }

}
