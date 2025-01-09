package cn.javaweb.base.model;

import cn.javaweb.base.entity.*;
import cn.javaweb.library.Config;
import cn.javaweb.library.BaseModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel extends BaseModel {

    public UserModel(Config conf){
        super(conf);
    }

    public UserModel(Connection connection){
        super(connection);
    }

    public Object parseObject(ResultSet rs) throws SQLException {

        User user = new User();
        user.setId(rs.getInt("id"));
        user.setRealname(rs.getString("realname"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setStatus(rs.getInt("status"));
        user.setRoleId(rs.getInt("role_id"));
        user.setToken(rs.getString("token"));
        user.setLoginFailure(rs.getInt("login_failure"));
        user.setPreventTime(rs.getLong("prevent_time"));
        user.setDepId(rs.getInt("dep_id"));
        user.setGrade(rs.getInt("grade"));
        user.setClassName(rs.getString("class_name"));
        user.setPwdQuestion(rs.getString("pwd_question"));
        user.setPwdAnswer(rs.getString("pwd_answer"));
        user.setDepName(rs.getString("dep_name"));
        return user;
    }

    public User getUserByName(String username) {
        Object[] params = {username};
        return (User) selectOne("select user.*, dept.dep_name from user left join dept on user.dep_id=dept.id where username=?", params);
    }

    public User getUserByToken(String token){
        Object[] params = {token};
        return (User) selectOne("select user.*, dept.dep_name from user left join dept on user.dep_id=dept.id where token=?", params);
    }

    public boolean updateUserToken(User user) throws SQLException {
        String sql = "update user set token=?, login_failure=?, prevent_time=? where id=?";
        Object [] params = {user.getToken(), user.getLoginFailure(), user.getPreventTime(), user.getId()};
        return update(sql, params) > 0;
    }

    public boolean logout(User user) throws SQLException {
        String sql = "update user set token='' where id=?";
        Object[] params = {user.getId()};
        return update(sql, params) > 0;
    }

    public boolean resetpassword(Integer userId) throws SQLException {
        String sql = "update user set password=? where id=?";
        Object [] params = {"123456", userId};
        return update(sql, params) > 0;
    }

    public boolean updatePassword(ResetPassword resetPassword, Integer userId) throws SQLException {
        String sql = "update user set password=? where id=?";
        Object [] params = {resetPassword.getConfirmPassword(), userId};
        return update(sql, params) > 0;
    }

    public boolean unlock(Integer userId) throws SQLException {
        String sql = "update user set login_failure=? where id=?";
        Object [] params = {0, userId};
        return update(sql, params) > 0;
    }

    public HashMap<String,Object> getPageData(PageInfo pi, HashMap<String, Object> params) throws SQLException {
        HashMap<String,Object> result = new HashMap<>();
        if(params.size()<1){
            result.put("total", getCount("select count(*) as cnt from user where 1", null));
            result.put("rows", selectAll("select user.*, dept.dep_name from user left join dept on user.dep_id=dept.id where 1 limit " + pi.getOffset() + " , " + pi.getLimit(), null));
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
            String sql = "select count(*) as cnt from user where " + where;
            result.put("total", getCount(sql, p.toArray()));
            sql = "select user.*, dept.dep_name from user left join dept on user.dep_id=dept.id where " + where;
            result.put("rows", selectAll(sql, p.toArray()));
        }
        return result;
    }

    public boolean add(User user) throws SQLException {
        String sql = "insert into user (role_id, username, realname, password, status, grade, class_name, dep_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        Object [] params = {user.getRoleId(), user.getUsername(), user.getRealname(), "123456", user.getStatus(), user.getGrade(), user.getClassName(), user.getDepId()};
        return update(sql, params) > 0;
    }

    public boolean edit(User user) throws SQLException {
        String sql = "update user set role_id=?, username=?, realname=?, status=?, grade=?, class_name=?, dep_id=? where id=?";
        Object [] params = {user.getRoleId(), user.getUsername(), user.getRealname(), user.getStatus(), user.getGrade(), user.getClassName(), user.getDepId(), user.getId()};
        return update(sql, params) > 0;
    }

    public boolean del(IDInfo idInfo) throws SQLException {
        String sql = "delete from user where id=?";
        Object [] params = {idInfo.getId()};
        return update(sql, params) > 0;
    }

    public User getUserByUserId(int userId) throws SQLException {
        Object[] params = {userId};
        return (User) selectOne("select user.*, dept.dep_name from user left join dept on user.dep_id=dept.id where user.id=?", params);
    }
}
