package cn.gpnusz.ucloudteach.req;

/**
 * @author h0ss
 * @description 封装用户编辑信息的请求
 * @date 2021/11/27 14:26
 */
public class UserDataSaveReq {
    private String name;

    private Boolean gender;

    private String headPic;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getHeadPic() {
        return headPic;
    }

    @Override
    public String toString() {
        return "UserDataSaveResp{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}