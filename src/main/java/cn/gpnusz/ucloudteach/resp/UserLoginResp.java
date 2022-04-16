package cn.gpnusz.ucloudteach.resp;

/**
 * @author h0ss
 * @description 用户登录成功后返回信息实体类
 * @date 2021/11/26 10:59
 */
public class UserLoginResp {
    private String phone;

    private String token;

    private String name;

    private Boolean gender;

    private String headPic;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserLoginResp{" +
                "phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}