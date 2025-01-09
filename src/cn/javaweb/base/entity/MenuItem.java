package cn.javaweb.base.entity;

import java.util.List;

public class MenuItem {
    protected int id;
    protected String name;
    protected String path;
    protected String menu_type;
    protected List<MenuItem> children;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", menu_type='" + menu_type +
                '}';
    }
}
