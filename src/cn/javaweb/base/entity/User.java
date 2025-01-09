package cn.javaweb.base.entity;

public class User {
    private int id;
    private String token;
    private String username;
    private String realname;
    private String password;
    private int roleId;
    private int status;
    private int loginFailure;
    private long preventTime;
    private int depId;
    private String depName;
    private int grade;
    private String className;
    private String pwdQuestion;
    private String pwdAnswer;

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRealname() {
        return realname;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public int getStatus() {
        return status;
    }

    public int getLoginFailure() {
        return loginFailure;
    }

    public long getPreventTime() {
        return preventTime;
    }

    public int getDepId() {
        return depId;
    }

    public String getDepName() { return depName; }

    public int getGrade() {
        return grade;
    }

    public String getClassName() {
        return className;
    }

    public String getPwdQuestion() {
        return pwdQuestion;
    }

    public String getPwdAnswer() {
        return pwdAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLoginFailure(int loginFailure) {
        this.loginFailure = loginFailure;
    }

    public void setPreventTime(long preventTime) {
        this.preventTime = preventTime;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public void setDepName(String depName){ this.depName = depName; }
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPwdQuestion(String pwdQuestion) {
        this.pwdQuestion = pwdQuestion;
    }

    public void setPwdAnswer(String pwdAnswer) {
        this.pwdAnswer = pwdAnswer;
    }

    public User(){
        this.grade = 2024;
        this.className = "";
        this.pwdQuestion = "";
        this.pwdAnswer   = "";
        this.token       = "";
        this.depId       = 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", roleId=" + roleId +
                ", status=" + status +
                ", loginFailure=" + loginFailure +
                ", preventTime=" + preventTime +
                ", depId=" + depId +
                ", grade=" + grade +
                ", className=" + className +
                ", pwdQuestion='" + pwdQuestion + '\'' +
                ", pwdAnswer='" + pwdAnswer + '\'' +
                '}';
    }
}
