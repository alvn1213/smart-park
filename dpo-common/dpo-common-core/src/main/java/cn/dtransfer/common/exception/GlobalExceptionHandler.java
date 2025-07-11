package cn.dtransfer.common.exception;

import cn.dtransfer.common.core.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.wf.jwtp.exception.ErrorTokenException;
import org.wf.jwtp.exception.ExpiredTokenException;
import org.wf.jwtp.exception.UnauthorizedException;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public R handleException(HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return R.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e) {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        logger.error("运行时异常:", e);
        return R.error(e.getMessage());
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(HisException.class)
    public R handleWindException(HisException e) {
        logger.error("运行时异常:", e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 数据库异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error("运行时异常:", e);
        return R.error("服务器错误，请联系管理员");
    }

    /**
     * 验证码错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ValidateCodeException.class)
    public R handleCaptcha(ValidateCodeException e) {
        logger.error("运行时异常:", e);
        return R.error(e.getMessage());
    }

    /**
     * 捕获并处理未授权异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R handleUnauthorized(UnauthorizedException e) {
        logger.error("运行时异常:", e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 捕获并处理token验证异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(ErrorTokenException.class)
    public R handleErrorToken(ErrorTokenException e) {
        logger.error("运行时异常:", e);
        return R.error(e.getCode(), e.getMessage());
    }

    /**
     * 捕获并处理token已经过期异常
     *
     * @param e 授权异常
     * @return 统一封装的结果类, 含有代码code和提示信息msg
     */
    @ExceptionHandler(ExpiredTokenException.class)
    public R handleExpiredToken(ExpiredTokenException e) {
        logger.error("运行时异常:", e);
        return R.error(e.getCode(), e.getMessage());
    }

}
