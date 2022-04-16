package cn.gpnusz.ucloudteach.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

public class Course {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String cover;

    private String teacher;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long subjectId;

    private Integer sort;

    private Boolean status;

    private String certificate;

    private String sendWord;

    private Integer totalMember;

    private Integer totalSection;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer totalPeriod;

    private BigDecimal price;

    private String description;

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

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Integer getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Integer totalSection) {
        this.totalSection = totalSection;
    }

    public Date getCreateTime() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createTime)) {
            BeanUtils.copyProperties(createTime, date);
        }
        return date;
    }

    public void setCreateTime(Date createTime) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createTime)) {
            BeanUtils.copyProperties(createTime, date);
        }
        this.createTime = date;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(Integer totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", cover=").append(cover);
        sb.append(", teacher=").append(teacher);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", certificate=").append(certificate);
        sb.append(", sendWord=").append(sendWord);
        sb.append(", totalMember=").append(totalMember);
        sb.append(", totalSection=").append(totalSection);
        sb.append(", createTime=").append(createTime);
        sb.append(", totalPeriod=").append(totalPeriod);
        sb.append(", price=").append(price);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}