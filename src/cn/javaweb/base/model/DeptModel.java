package cn.javaweb.base.model;

import cn.javaweb.base.entity.Department;
import cn.javaweb.base.entity.IDInfo;
import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeptModel  extends BaseModel {
    public DeptModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){
        Department department = new Department();
        try{
            department.setId(rs.getInt("id"));
            department.setPid(rs.getInt("pid"));
            department.setDepName(rs.getString("dep_name"));
            department.setStatus(rs.getInt("status"));
            department.setRemark(rs.getString("remark"));
            return department;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return department;

    }


    public List<Department> getChildMenu(List<Object> nodes, int pid){
        List<Department> menus = new ArrayList<>();

        for(Object n : nodes){
            Department node = (Department)n;
            if (node.getPid() != pid)
                continue;

            List<Department> childs = getChildMenu(nodes, node.getId());
            if(childs.size()>0){
                node.setChildren(childs);
            }

            menus.add(node);

        }

        return menus;
    }
    public List<Department> getAll(){
        String sql = "select * from dept where 1 order by pid";
        List<Object> nodes = selectAll(sql, null);
        if(nodes.size()<1){
            return null;
        }
        return getChildMenu(nodes, 0);
    }

    public boolean add(Department department)throws SQLException {
        String sql = "insert into dept (dep_name, pid, status, remark) values (?, ?, ?, ?)";
        Object [] params = {department.getDepName(), department.getPid(), department.getStatus(), department.getRemark()};
        return update(sql, params)>0;
    }

    public boolean edit(Department department)throws SQLException{
        String sql = "update dept set dep_name=?, pid=?, status=?, remark=? where id=?";
        Object [] params = {department.getDepName(), department.getPid(), department.getStatus(), department.getRemark(), department.getId()};
        return update(sql, params)>0;
    }

    public boolean del(IDInfo idInfo)throws SQLException{
        String sql = "delete from dept where id=?";
        Object [] params = {idInfo.getId()};
        return update(sql, params)>0;
    }

    public List<Department> getCollegeAll() {
        String sql = "select * from dept";
        List<Object> nodes = selectAll(sql, null);
        List<Department> departments = new ArrayList<>();
        for (Object n : nodes) {
            Department department = (Department)n;
            departments.add(department);
        }
        return departments;
    }
}
