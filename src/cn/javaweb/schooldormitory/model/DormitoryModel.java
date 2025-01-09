package cn.javaweb.schooldormitory.model;

import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.library.BaseModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DormitoryModel extends BaseModel<DormitoryInfo> {

    public DormitoryModel(Connection connection) {
        super(connection);
    }

    @Override
    public Object parseObject(ResultSet rs) throws SQLException {
        try {
            return DormitoryInfo.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .building(rs.getString("building"))
                    .collegeId(rs.getInt("college_id"))
                    .capacity(rs.getInt("capacity"))
                    .occupied(rs.getInt("occupied"))
                    .createdAt(rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime()
                            : null)
                    .updatedAt(rs.getTimestamp("updated_at") != null
                            ? rs.getTimestamp("updated_at").toLocalDateTime()
                            : null)
                    .build();
        } catch (SQLException e) {
            throw new SQLException("Error parsing ResultSet to Dormitory", e);
        }
    }

    public List<DormitoryInfo> getAll() throws SQLException {
        String sql = "SELECT * FROM dormitory ORDER BY college_id, building";
        return selectAll(sql, null);
    }

    public boolean add(DormitoryInfo dormitory) throws SQLException {
        if (!checkDormitory(dormitory)) {
            throw new RuntimeException("人数错误：已入住人数不能大于容纳人数");
        }

        String sql = "INSERT INTO dormitory (name, building, college_id, capacity, occupied) VALUES (?, ?, ?, ?, ?)";
        Object[] params = {
                dormitory.getName(),
                dormitory.getBuilding(),
                dormitory.getCollegeId(),
                dormitory.getCapacity(),
                dormitory.getOccupied()
        };
        return update(sql, params) > 0;
    }

    public boolean del(int id) throws SQLException {
        String sql = "DELETE FROM dormitory WHERE id=?";
        Object[] params = {id};
        return update(sql, params) > 0;
    }

    public PageInfo<DormitoryInfo> getPaginatedList(int page, int limit, String dormitoryName) throws SQLException {
        int offset = (page - 1) * limit;

        String where = "";
        Object[] params = null;
        if (dormitoryName != null && !dormitoryName.trim().isEmpty()) {
            where = "WHERE name LIKE ?";
            params = new Object[]{"%" + dormitoryName + "%"};
        }

        String countSql = "SELECT COUNT(*) as cnt FROM dormitory " + where;
        int total = getCount(countSql, params);

        String sql = "SELECT * FROM dormitory " + where + " ORDER BY college_id, building LIMIT ?, ?";
        Object[] pagingParams = params == null
                ? new Object[]{offset, limit}
                : new Object[]{params[0], offset, limit};
        List<DormitoryInfo> result = selectAll(sql, pagingParams);

        return PageInfo.<DormitoryInfo>builder()
                .total(total)
                .pages((int) Math.ceil((double) total / limit))
                .rows(Math.min(limit, total))
                .list(result)
                .build();
    }

    public DormitoryInfo updateDormitory(DormitoryInfo dormitory) throws SQLException {
        if (!checkDormitory(dormitory)) {
            throw new RuntimeException("人数错误：已入住人数不能大于容纳人数");
        }

        String sql = "UPDATE dormitory SET name=?, building=?, college_id=?, capacity=?, occupied=? WHERE id=?";
        Object[] params = {
                dormitory.getName(),
                dormitory.getBuilding(),
                dormitory.getCollegeId(),
                dormitory.getCapacity(),
                dormitory.getOccupied(),
                dormitory.getId()
        };
        update(sql, params);

        String querySql = "SELECT * FROM dormitory WHERE id=?";
        return selectOne(querySql, new Object[]{dormitory.getId()});
    }

    private Boolean checkDormitory(DormitoryInfo dormitory) {
        return dormitory.getCapacity() >= dormitory.getOccupied();
    }

    public DormitoryInfo getDormitoryBydDormitoryId(int dormitoryId) throws SQLException {
        String sql = "SELECT * FROM dormitory WHERE id = ?";
        Object[] params = {dormitoryId};
        return selectOne(sql, params);
    }

    public String getDormitoryNameBydDormitoryId(int i) throws SQLException {
        String sql = "SELECT * FROM dormitory WHERE id = ?";
        Object[] params = {i};
        DormitoryInfo result = selectOne(sql, params);
        return result.getBuilding() + result.getName();
    }

    public Integer getDormitoryId(String name) throws SQLException {
        String sql = "SELECT * FROM dormitory WHERE name = ?";
        Object[] params = {name};
        return selectOne(sql, params).getId();
    }

    public void updateOccupied(Integer bedByBedId, String action) throws SQLException {
        String sql = "SELECT * FROM dormitory WHERE id = ?";
        Object[] params = {bedByBedId};
        DormitoryInfo info = selectOne(sql, params);
        sql = "UPDATE dormitory SET occupied=? WHERE id=?";
        Object[] newParams = {action.equals("available")
                ? info.getOccupied() - 1
                : info.getOccupied() + 1,
                bedByBedId};
        update(sql, newParams);
    }
}
