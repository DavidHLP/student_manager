package cn.javaweb.base.entity;

import java.util.List;

public class Menu {
    private int id;
    private String menuName;
    private int pid;
    private int orderNum;
    private String path;
    private String menuType;
    private int status;
    private String remark;
    private List<Menu> children;


    public int getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getPid() {
        return pid;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public String getPath() {
        return path;
    }

    public String getMenuType() {
        return menuType;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark(){ return remark; }
    public List<Menu> getChildren(){
        return children;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setChildren(List<Menu> children){
        this.children = children;
    }

    public void setRemark(String remark) { this.remark = remark; }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", pid=" + pid +
                ", orderNum=" + orderNum +
                ", path='" + path + '\'' +
                ", menuType='" + menuType + '\'' +
                ", status=" + status +
                '}';
    }
}
