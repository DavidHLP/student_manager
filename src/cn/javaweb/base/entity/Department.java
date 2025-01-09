package cn.javaweb.base.entity;

import java.util.List;

public class Department {
    private int id;
    private int pid;

    private String depName;
    private int status;
    private String remark;
    List<Department> children;

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public String getDepName() {
        return depName;
    }

    public int getStatus() {
        return status;
    }

    public List<Department> getChildren(){ return children; }
    public String getRemark() {
        return remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setChildren(List<Department> children) { this.children = children; }
}
