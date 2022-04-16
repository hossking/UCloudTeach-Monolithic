package cn.gpnusz.ucloudteach.resp;

import cn.gpnusz.ucloudteach.entity.CourseMember;

import java.math.BigDecimal;

/**
 * @author h0ss
 * @description 自定义课程-学员信息响应实体
 * @date 2021/11/30 - 12:32
 */
public class UserCourseResp extends CourseMember {
    private String name;

    private String cover;

    private BigDecimal price;

    private String teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "CourseMemberCustResp{" +
                "name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", price=" + price +
                ", teacher='" + teacher + '\'' +
                "} " + super.toString();
    }
}
