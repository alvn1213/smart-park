package cn.dtransfer.common.exception;

/**
 * 异常类
 *
 */
public class HisException extends RuntimeException {
    private static final long serialVersionUID = 3640068073161175965L;

    private String msg;

    private int code = 500;

    public HisException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public HisException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public HisException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public HisException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
