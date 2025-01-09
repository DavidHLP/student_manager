package cn.javaweb.base.model;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;
import cn.javaweb.base.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleModel  extends BaseModel {
    public RoleModel(Config conf){
        super(conf);
    }

    public Object parseObject(ResultSet rs){
        Role role = new Role();
        try{
            role.setId(rs.getInt("id"));
            role.setRoleName(rs.getString("role_name"));
            role.setRemark(rs.getString("remark"));
            role.setRules(rs.getString("rules"));
            role.setStatus(rs.getInt("status"));
            return role;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return role;

    }

    public int edit(Role role)throws SQLException {
        String sql = "update role set rules=?, remark=?, status=? where id=?";
        Object [] params = {role.getRules(), role.getRemark(), role.getStatus(), role.getId()};
        return update(sql, params);
    }

    public HashMap<String,Object> getPageData(PageInfo pi, HashMap<String, Object> params){
        HashMap<String,Object> result = new HashMap<>();
        if(params.size()<1){
            result.put("total", getCount("select count(*) as cnt from role where 1", null));
            result.put("rows", selectAll("select * from role where 1 limit " + pi.getOffset() + " , " + pi.getLimit(), null));
        }else{
            String where = "";
            List<Object> p = new ArrayList<>();
            for(Map.Entry<String, Object> entry: params.entrySet()){
                if(where.length()==0){
                    where = entry.getKey() + " = ? ";
                }else{
                    where += " and " + entry.getKey() + " = ? ";
                }
                p.add(entry.getValue());
            }
            String sql = "select count(*) as cnt from role where " + where;
            result.put("total", getCount(sql, p.toArray()));
            sql = "select * from role where " + where;
            result.put("rows", selectAll(sql, p.toArray()));
        }

        return result;
    }
}
