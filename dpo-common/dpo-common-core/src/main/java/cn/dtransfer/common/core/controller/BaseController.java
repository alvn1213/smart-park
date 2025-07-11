package cn.dtransfer.common.core.controller;

import cn.dtransfer.common.constant.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.core.page.PageDomain;
import cn.dtransfer.common.core.page.TableSupport;
import cn.dtransfer.common.utils.DateUtils;
import cn.dtransfer.common.utils.ServletUtils;
import cn.dtransfer.common.utils.StringUtils;
import cn.dtransfer.common.utils.sql.SqlUtil;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web层通用数据处理
 *
 */
public class BaseController {

    private final static String ACCESS_USERID = Constants.ACCESS_USERID;

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> valueOperations;

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer    pageNum    = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (null != pageNum && null != pageSize) {
            String orderBy = pageDomain.getIsAsc() == null ? "" : SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }


    /**
     * 获取登录名Id
     */
    public long getCurrentUserId() {
        Token token = SubjectUtil.getToken(getRequest());
        String currentId = token.getUserId();
        if (StringUtils.isNotBlank(currentId)) {
            return Long.valueOf(currentId);
        }
        return 0L;
    }

    /**
     * 获取登录名
     */
    public String getLoginName() {
        Token token = SubjectUtil.getToken(getRequest());
        String value = valueOperations.get(ACCESS_USERID + ":" + token.getUserId());
        JSONObject jo = StringUtils.isEmpty(value) ? null : JSON.parseObject(value, JSONObject.class);
        if (jo != null && jo.containsKey("loginName")) {
            return jo.getString("loginName");
        }
        return null;
    }


    /**
     * 响应返回结果
     *
     * @param list 列表
     * @return 操作结果
     */
    protected R result(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo(list);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("rows", list);
        m.put("pageNum", pageInfo.getPageNum());
        m.put("total", pageInfo.getTotal());
        return R.ok(m);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R toAjax(int rows) {
        return rows > 0 ? R.ok() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R toAjax(boolean result) {
        return result ? R.ok() : R.error();
    }
}
