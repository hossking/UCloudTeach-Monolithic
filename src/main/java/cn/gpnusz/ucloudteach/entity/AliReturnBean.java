package cn.gpnusz.ucloudteach.entity;

/**
 * @author h0ss
 * @description 验签信息实体类
 * @date 2021/11/29 - 18:34
 */
public class AliReturnBean {
    private String charset;

    private String out_trade_no;

    private String method;

    private String total_amount;

    private String sign;

    private String trade_no;

    private String auth_app_id;

    private String version;

    private String app_id;

    private String sign_type;

    private String seller_id;

    private String timestamp;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getAuth_app_id() {
        return auth_app_id;
    }

    public void setAuth_app_id(String auth_app_id) {
        this.auth_app_id = auth_app_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "app_id=" + app_id +
                "&auth_app_id=" + auth_app_id +
                "&charset=" + charset +
                "&method=" + method +
                "&out_trade_no=" + out_trade_no +
                "&seller_id=" + seller_id +
                "&timestamp=" + timestamp+
                "&total_amount=" + total_amount +
                "&trade_no=" + trade_no +
                "&version=" + version;

    }
}
