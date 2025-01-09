package cn.javaweb.course.entity;

import java.util.Date;

public class Enrollment {
    private int id;
    private int courseId;
    private int studentId;
    private Date enrollmentTime;
    private int status;
    private float score;
    private String username;
    private String realname;

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getEnrollmentTime() {
        return enrollmentTime;
    }

    public int getStatus() {
        return status;
    }

    public float getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setEnrollmentTime(Date enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
