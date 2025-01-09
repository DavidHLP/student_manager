package cn.javaweb.schooldormitory.model;

import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentModel extends BaseModel {
    public StudentModel(Connection connection) {
        super(connection);
    }

    @Override
    public Object parseObject(ResultSet rs) {
        try {
            return Student.builder()
                    .id(rs.getInt("id"))
                    .username(rs.getString("username"))
                    .realname(rs.getString("realname"))
                    .grade(rs.getInt("grade"))
                    .className(rs.getString("class_name"))
                    .status(rs.getString("status"))
                    .depId(rs.getInt("dep_id"))
                    .build();
        } catch (SQLException e) {
            System.err.println("Error parsing ResultSet to Student: " + e.getMessage());
            return null;
        }
    }

    public PageInfo<Student> getStudentList(int page, int limit, String studentName) {
        int offset = (page - 1) * limit;

        // 构建条件查询
        String where = "WHERE role_id = 3"; // 始终包含 role_id = 3 的条件
        List<Object> paramsList = new ArrayList<>();
        if (studentName != null && !studentName.trim().isEmpty()) {
            where += " AND realname LIKE ?";
            paramsList.add("%" + studentName + "%");
        }

        // 查询总记录数
        String countSql = "SELECT COUNT(*) as cnt FROM user " + where;
        int total = getCount(countSql, paramsList.toArray());

        // 查询当前页数据
        String sql = "SELECT * FROM user " + where + " ORDER BY realname LIMIT ?, ?";
        paramsList.add(offset);
        paramsList.add(limit);
        List<Object> result = selectAll(sql, paramsList.toArray());

        // 转换为实体列表
        List<Student> students = new ArrayList<>();
        for (Object obj : result) {
            students.add((Student) obj);
        }

        // 构建分页信息
        return PageInfo.<Student>builder()
                .total(total)
                .pages((int) Math.ceil((double) total / limit))
                .rows(Math.min(limit, total))
                .list(students)
                .build();
    }

}
