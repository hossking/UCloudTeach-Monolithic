package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author h0ss
 * @description 封装保存课程信息
 * @date 2021/11/14 20:01
 */
public class CourseSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【课程名称】不能为空")
    private String name;

    private String cover;

    @NotBlank(message = "【讲师名称】不能为空")
    private String teacher;

    @NotNull(message = "【所属科目】不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    @NotNull(message = "【排序权重】不能为空")
    private Integer sort;

    private Boolean status;

    private String certificate;

    private String sendWord;

    @NotBlank(message = "【课程简介】不能为空")
    private String description;

    private Integer totalMember;

    private Integer totalSection;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Integer totalPeriod;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getSendWord() {
        return sendWord;
    }

    public void setSendWord(String sendWord) {
        this.sendWord = sendWord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Integer totalSection) {
        this.totalSection = totalSection;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public void setCreateTime(Date createTime) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createTime)) {
            BeanUtils.copyProperties(createTime,date);
        }
        this.createTime = date;
    }

    public Date getCreateTime() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createTime)) {
            BeanUtils.copyProperties(createTime,date);
        }
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CourseSaveReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", teacher='" + teacher + '\'' +
                ", subjectId=" + subjectId +
                ", sort=" + sort +
                ", status=" + status +
                ", certificate='" + certificate + '\'' +
                ", sendWord='" + sendWord + '\'' +
                ", description='" + description + '\'' +
                ", totalMember=" + totalMember +
                ", totalSection=" + totalSection +
                ", createTime=" + createTime +
                ", totalPeriod=" + totalPeriod +
                ", price=" + price +
                '}';
    }
}