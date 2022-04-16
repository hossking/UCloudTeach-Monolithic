package cn.gpnusz.ucloudteach.req;

/**
 * @author h0ss
 * @description 验证码校验的请求参数
 * @date 2021/12/5 - 14:47
 */
public class CheckCodeReq {
    private String ticket;

    private String randStr;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getRandStr() {
        return randStr;
    }

    public void setRandStr(String randStr) {
        this.randStr = randStr;
    }

    @Override
    public String toString() {
        return "CheckCodeReq{" +
                "ticket='" + ticket + '\'' +
                ", randStr='" + randStr + '\'' +
                '}';
    }
}
