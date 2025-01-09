package cn.javaweb.base.model;

import cn.javaweb.base.entity.*;
import cn.javaweb.library.Config;
import cn.javaweb.library.BaseModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuModel  extends BaseModel {
    public MenuModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){

        Menu menu = new Menu();

        try{
            menu.setId(rs.getInt("id"));
            menu.setPid(rs.getInt("pid"));
            menu.setMenuName(rs.getString("menu_name"));
            menu.setMenuType(rs.getString("menu_type"));
            menu.setStatus(rs.getInt("status"));
            menu.setPath(rs.getString("path"));
            menu.setOrderNum(rs.getInt("order_num"));
            menu.setRemark(rs.getString("remark"));

            return menu;
        }catch (Exception e){

        }
        return null;
    }

    /**
     * 获取所有功能权限列表
     * @return
     */
    public List<PermissionNode> getPermissions(){
        List<PermissionNode> result = new ArrayList<>();

        String sql = "select * from menu where status=1 and menu_type='F' order by pid";
        List<Object> nodes = selectAll(sql, null);

        for(Object n : nodes){
            Menu node = (Menu) n;

            PermissionNode item = new PermissionNode();
            item.setName(node.getMenuName());
            item.setPath(node.getPath());
            result.add(item);

        }

        return result;

    }

    /**
     * 递归查询下级节点
     * @param nodes
     * @param pid
     * @return
     */
    public List<MenuItem> getChildren(List<Object> nodes, Integer pid){
        List<MenuItem> menus = new ArrayList<>();

        for(Object n : nodes){
            Menu node = (Menu) n;
            if (node.getPid() != pid)
                continue;
            MenuItem item = new MenuItem();
            item.setId(node.getId());
            item.setName(node.getMenuName());
            item.setPath(node.getPath());
            item.setMenu_type(node.getMenuType());

            List<MenuItem> childs = getChildren(nodes, node.getId());

            if(childs.size()>0){
                item.setChildren(childs);
            }
            menus.add(item);

        }

        return menus;
    }

    public List<Menu> getChildMenu(List<Object> nodes, int pid){
        List<Menu> menus = new ArrayList<>();

        for(Object n : nodes){
            Menu node = (Menu)n;
            if (node.getPid() != pid)
                continue;

            List<Menu> childs = getChildMenu(nodes, node.getId());
            if(childs.size()>0){
                node.setChildren(childs);
            }

            menus.add(node);

        }

        return menus;
    }

    /**
     * 获取用户菜单
     * @return
     */
    public List<MenuItem> getMenuNav(User user){
        String sql = "select rules from role where id = " + user.getId();

        String rules = (String)getValue(sql, null, "rules", "string");

        if(rules == null){
            return null;
        }

        sql = "select * from menu where status=1 and menu_type!='F' and id in (" + rules + ") order by pid";
        System.out.println(sql);
        List<Object> nodes = selectAll(sql, null);

        if(nodes.size()<1){
            return null;
        }
        return getChildren(nodes,0);
    }

    public Object getSelectList(){
        String sql = "select * from menu where status=1 and menu_type!='F' order by pid";
        List<Object> nodes = selectAll(sql, null);
        if(nodes.size()<1){
            return null;
        }
        return getChildren(nodes,0);
    }

    public List<Menu> getAll(){
        String sql = "select * from menu where 1 order by pid";
        List<Object> nodes = selectAll(sql, null);
        if(nodes.size()<1){
            return null;
        }
        return getChildMenu(nodes, 0);
    }

    public boolean add(Menu menu) throws SQLException{
        String sql = "insert into menu (menu_name, pid, order_num, path, menu_type, status, remark) values (?, ?, ?, ?, ?, ?, ?)";
        Object [] params = {menu.getMenuName(), menu.getPid(), menu.getOrderNum(), menu.getPath(), menu.getMenuType(), menu.getStatus(), menu.getRemark()};
        return update(sql, params)>0;
    }

    public boolean edit(Menu menu)throws SQLException{
        String sql = "update menu set menu_name=?, pid=?, order_num=?, path=?, menu_type=?, status=?, remark=? where id=?";
        Object [] params = {menu.getMenuName(), menu.getPid(), menu.getOrderNum(), menu.getPath(), menu.getMenuType(), menu.getStatus(), menu.getRemark(), menu.getId()};
        return update(sql, params)>0;
    }

    public boolean del(IDInfo idInfo)throws SQLException{
        String sql = "delete from menu where id=?";
        Object [] params = {idInfo.getId()};
        return update(sql, params)>0;
    }
}
