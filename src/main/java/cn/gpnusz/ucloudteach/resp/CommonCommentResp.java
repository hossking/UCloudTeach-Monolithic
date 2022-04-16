package cn.gpnusz.ucloudteach.resp;

import cn.gpnusz.ucloudteach.entity.CourseComment;

/**
 * @author h0ss
 * @description 返回的评论实体类
 * @date 2021/11/30 - 1:53
 */
public class CommonCommentResp extends CourseComment {

    private String name;

    private String headPic;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "CommonCommentResp{" +
                "name='" + name + '\'' +
                ", headPic='" + headPic + '\'' +
                ", phone='" + phone + '\'' +
                "} " + super.toString();
    }
}
