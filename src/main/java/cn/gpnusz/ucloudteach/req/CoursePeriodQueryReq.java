package cn.gpnusz.ucloudteach.req;

import cn.gpnusz.ucloudteach.common.PageReq;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装查询课时的信息
 * @date 2021/11/14 23:59
 */
public class CoursePeriodQueryReq extends PageReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long sectionId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "CoursePeriodQueryReq{" +
                "courseId=" + courseId +
                ", sectionId=" + sectionId +
                "} " + super.toString();
    }
}