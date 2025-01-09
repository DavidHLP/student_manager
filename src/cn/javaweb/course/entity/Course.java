package cn.javaweb.course.entity;

import java.util.Date;

public class Course {
    private int id;
    private String courseName;
    private float credit;
    private int status;
    private String remark;
    private int teacherId;
    private int capacity;
    private String location;
    private String courseTime;
    private String realname;
    private int estatus;
    private float score;
    private Date etime;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    private String term;

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public float getCredit() {
        return credit;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return location;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getTerm() {
        return term;
    }

    public int getEstatus() {
        return estatus;
    }

    public float getScore() {
        return score;
    }

    public Date getEtime() {
        return etime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", teacherId=" + teacherId +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", realname='" + realname + '\'' +
                ", estatus=" + estatus +
                ", score=" + score +
                ", etime=" + etime +
                ", term='" + term + '\'' +
                '}';
    }
}
