package cn.javaweb.course.entity;

public class Term {
    private int id;
    private String termName;
    private String remark;

    public int getId() {
        return id;
    }

    public String getTermName() {
        return termName;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
