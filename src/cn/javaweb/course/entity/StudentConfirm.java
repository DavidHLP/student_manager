package cn.javaweb.course.entity;

public class StudentConfirm {
    private int studentId;
    private int courseId;
    private int action;

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getAction() {
        return action;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
