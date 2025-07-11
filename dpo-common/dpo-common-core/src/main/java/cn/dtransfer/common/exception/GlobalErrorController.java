package cn.dtransfer.common.exception;

import cn.dtransfer.common.core.domain.R;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.Ignore;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GlobalErrorController extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Ignore
    @RequestMapping(value = ERROR_PATH)
    public R error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return R.error(status.value(), status.getReasonPhrase());
    }

    @Ignore
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
