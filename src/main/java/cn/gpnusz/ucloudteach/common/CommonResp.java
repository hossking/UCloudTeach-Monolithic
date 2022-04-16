package cn.gpnusz.ucloudteach.common;

/**
 * @author h0ss
 * @description 用于系统业务响应数据的统一封装
 * @date 2021/11/11 21:36
 */
public class CommonResp<T> {
    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型的消息体数据
     */
    private T content;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ResponseDto{" + "success=" + success +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
