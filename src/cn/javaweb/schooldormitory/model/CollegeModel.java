package cn.javaweb.schooldormitory.model;

import cn.javaweb.library.BaseModel;
import cn.javaweb.library.Config;
import cn.javaweb.schooldormitory.entity.College;
import cn.javaweb.schooldormitory.entity.PageInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CollegeModel extends BaseModel <College> {

    public CollegeModel(Config config) {
        super(config);
    }

    public CollegeModel(Connection connection) {
        super(connection);
    }

    @Override
    public Object parseObject(ResultSet rs) {
        try {
            return College.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .departmentId(rs.getString("department_id"))
                    .description(rs.getString("description"))
                    .createdAt(rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime()
                            : null)
                    .updatedAt(rs.getTimestamp("updated_at") != null
                            ? rs.getTimestamp("updated_at").toLocalDateTime()
                            : null)
                    .build();
        } catch (SQLException e) {
            System.err.println("Error parsing ResultSet to College: " + e.getMessage());
            return null;
        }
    }

    public List<College> getCollegeAll() {
        String sql = "SELECT * FROM college";
        return selectAll(sql, null);
    }

    public PageInfo<College> getPaginatedList(int page, int limit, String collegeName) {
        int offset = (page - 1) * limit;

        // 构建条件查询
        String where = "";
        Object[] params = null;
        if (collegeName != null && !collegeName.trim().isEmpty()) {
            where = "WHERE name LIKE ?";
            params = new Object[]{"%" + collegeName + "%"};
        }

        // 查询总记录数
        String countSql = "SELECT COUNT(*) as cnt FROM college " + where;
        int total = getCount(countSql, params);

        // 查询当前页数据
        String sql = "SELECT * FROM college " + where + " ORDER BY name LIMIT ?, ?";
        Object[] pagingParams = params == null
                ? new Object[]{offset, limit}
                : new Object[]{params[0], offset, limit};
        List<College> colleges = selectAll(sql, pagingParams);

        // 构建分页信息
        return PageInfo.<College>builder()
                .total(total)
                .pages((int) Math.ceil((double) total / limit))
                .rows(Math.min(limit, total))
                .list(colleges)
                .build();
    }

    public boolean del(int id) throws SQLException{
        String sql = "DELETE FROM college WHERE id=?";
        Object[] params = { id };
        return update(sql, params) > 0;
    }

    public Boolean updateCollege(College college) throws SQLException{
        String updateSql = "UPDATE college SET name = ?, description = ?, department_id = ?, updated_at = ? WHERE id = ?";
        Object[] params = {
                college.getName(),
                college.getDescription(),
                college.getDepartmentId(),
                college.getUpdatedAt(),
                college.getId()
        };
        return update(updateSql, params) > 0;
    }

    public String getCollegeNameByCollegeId(Integer id) {
        String sql = "SELECT * FROM college WHERE id = ?";
        Object[] params = {id};
        return selectOne(sql,params).getName();
    }

    public Void add(College college) throws SQLException{
        String sql = "INSERT INTO college (id, name, description, department_id, created_at) VALUES (?, ?, ?, ?, ?)";
        Object[] params = {
                college.getId(),
                college.getName(),
                college.getDescription(),
                college.getDepartmentId(),
                college.getCreatedAt(),
        };
        update(sql, params);
        return null;
    }
}