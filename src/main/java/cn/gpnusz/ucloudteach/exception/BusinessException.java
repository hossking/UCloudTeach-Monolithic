package cn.gpnusz.ucloudteach.exception;

/**
 * @author h0ss
 * @description 自定义业务异常处理类
 * @date 2021/11/11 21:38
 */
public class BusinessException extends RuntimeException {

    private BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
