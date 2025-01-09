package cn.javaweb.base.entity;

public class Role {
    private int id;
    private String roleName;
    private String remark;
    private String rules;
    private int status;

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getRemark() {
        return remark;
    }

    public String getRules() {
        return rules;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", remark='" + remark + '\'' +
                ", rules='" + rules + '\'' +
                ", status=" + status +
                '}';
    }
}
