package cn.dtransfer.system.aspect;

import cn.dtransfer.common.annotation.DataScope;
import cn.dtransfer.common.core.domain.BaseEntity;
import cn.dtransfer.common.utils.ServletUtils;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.IUserService;
import cn.dtransfer.system.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 数据过滤处理
 *
 */
@Slf4j
@Aspect
@Component
public class DataScopeAspect {

    @Autowired
    private IUserService userService;

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";


    /**
     * 配置织入点
     */
    @Pointcut("@annotation(cn.dtransfer.common.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        // 获取当前的用户
        HttpServletRequest request = ServletUtils.getRequest();
        User currentUser = null;
        Token token = SubjectUtil.getToken(request);
        if (token != null) {
            Long userId = Long.valueOf(token.getUserId());
            currentUser = userService.selectUserById(userId);
        }
        if (currentUser != null) {
            // 如果是超级管理员，则不过滤数据
            if (!User.isAdmin(currentUser.getId())) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
            }
        } else {
            log.warn("数据权限拦截失败,执行对象 currentUser is null");
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param userAlias 别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, User user, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();
        for (Role role : user.getRoles()) {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptAlias, role.getId()));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(StringUtils.format(" OR {}.id = {} ", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                String deptChild = user.getDept().getParentId() + "," + user.getDeptId();
                sqlString.append(StringUtils.format(" OR {}.id IN ( SELECT id FROM sys_dept WHERE id = {} or ancestors LIKE '%{}%' )", deptAlias, user.getDeptId(), deptChild));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(StringUtils.format(" OR {}.id = {} ", userAlias, user.getId()));
                } else {
                    sqlString.append(StringUtils.format(" OR {}.id IS NULL ", deptAlias));
                }
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())) {
            BaseEntity baseEntity = (BaseEntity) joinPoint.getArgs()[0];
            baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
}
