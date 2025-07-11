package cn.dtransfer.system.handlers;

import cn.dtransfer.system.service.ICurrentUserService;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import cn.dtransfer.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class MyTenantLineHandler implements TenantLineHandler {

    /**
     * 排除过滤的表
     */
    private static final String[] tableList = {"tables", "columns", "sys_tenant", "sys_config", "sys_role", "sys_dict_type", "sys_dict_data", "sys_districts",
            "sys_job", "sys_job_log", "sys_login_info", "sys_menu", "sys_notice", "sys_oper_log", "sys_oss", "sys_role_dept", "sys_role_menu",
            "sys_sn", "sys_user_role", "dpo_customer_contract_room", "dpo_apply_room", "dpo_customer_contract_refund_room", "dpo_apply_park_file",
            "dpo_apply_settle_file", "dpo_apply_move_in_file", "dpo_activity"};

    /**
     * 多租户标识
     */
    private static final String TENANT_ID = "tenant_id";

    /**
     * 排除过滤的表前缀
     */
    private static final String[] tablePrefix = {"qrtz", "gen"};

    @Autowired
    private ICurrentUserService userOnlineService;

    /**
     * 返回当前用户的租户ID
     */
    @Override
    public Expression getTenantId() {
        // 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。
        Long tenantId = userOnlineService.getTenantId();
        if (tenantId == null) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    /**
     * 租户字段名
     *
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return TENANT_ID;
    }

    /**
     * 跳过不需要加多租户的表
     */
    @Override
    public boolean ignoreTable(String tableName) {
        String prefix = StringUtils.substringBefore(tableName, "_");
        if (Arrays.asList(tableList).contains(tableName) || Arrays.asList(tablePrefix).contains(prefix) || getCurrentTenantId() == null) {
            //无租户模式
            return true;
        }
        return false;
    }

    /**
     * 当前租户Id
     */
    private Long getCurrentTenantId() {
        return userOnlineService.getTenantId();
    }

}
