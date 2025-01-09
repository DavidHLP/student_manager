package cn.javaweb.schooldormitory.model;

import cn.javaweb.library.BaseModel;
import cn.javaweb.schooldormitory.entity.BedInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BedModel extends BaseModel <BedInfo>{
    public BedModel(Connection connection) {
        super(connection);
    }

    @Override
    public Object parseObject(ResultSet rs) {
        try {
            return BedInfo.builder()
                    .id(rs.getInt("id"))
                    .dormitoryId(rs.getInt("dormitory_id"))
                    .bedInfo(rs.getString("bed_info"))
                    .bedNumber(rs.getInt("bed_number"))
                    .userId(rs.getObject("user_id") != null ? rs.getInt("user_id") : null)
                    .status(rs.getString("status"))
                    .createdAt(rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime()
                            : null)
                    .updatedAt(rs.getTimestamp("updated_at") != null
                            ? rs.getTimestamp("updated_at").toLocalDateTime()
                            : null)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException("Error parsing ResultSet to Bed: " + e.getMessage(), e);
        }
    }

    public List<BedInfo> getBedByDormitoryId(int dormitoryId) throws SQLException {
        String sql = "SELECT * FROM bed WHERE dormitory_id = ?";
        Object[] pagingParams = {dormitoryId};
        return selectAll(sql, pagingParams);
    }

    public BedInfo getBedByUserId(Integer id) throws SQLException {
        String sql = "SELECT * FROM bed WHERE user_id = ? AND status = ? ";
        Object[] pagingParams = {id , "occupied"};
        return selectOne(sql, pagingParams);
    }

    public List<BedInfo> getNoUseAll() throws SQLException {
        String sql = "SELECT * FROM bed WHERE status = ? ";
        Object[] pagingParams = {"available"};
        return selectAll(sql,pagingParams);
    }

    public void updateStatus(Integer userId,Integer bedId, String available) throws SQLException{
        try {
            String sql = null;
            Object[] params = null;
            if (userId == null) {
                sql = "UPDATE bed SET status = ? WHERE id = ? ";
                params = new Object[]{available, bedId};
            }
            else {
                sql = "UPDATE bed SET status = ? , user_id = ? WHERE id = ? ";
                params = new Object[]{available, userId, bedId};
            }
            update(sql, params);
        } catch (SQLException e) {
            throw new SQLException("Error updating bed status", e);
        }
    }

    public Void del(int id) throws SQLException{
        try {
            String sql = "UPDATE bed set status = ? WHERE id = ?";
            Object[] params = {"available", id};
            update(sql, params);
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error deleting bed by ID", e);
        }
    }

    public Void initBeds(Integer id, Integer capacity ,Integer collegeId) throws SQLException {
        try {
            String sql = null;
            Object[] params = null;
            String partition = "-";
            for(int i = 1 ; i <= capacity ; i++){
                sql = "INSERT INTO bed (dormitory_id, bed_number, status, bed_info) VALUES (?, ?, ?, ?)";
                params = new Object[]{id,i,"available",String.valueOf(collegeId) +partition+ String.valueOf(id)+partition+String.valueOf(i)};
                update(sql, params);
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error initializing beds", e);
        }
    }

    public void delByDormitoryId(Integer id) throws SQLException{
        try {
            String sql = "DELETE FROM bed WHERE dormitory_id = ?";
            Object[] params = {id};
            update(sql, params);
        } catch (SQLException e) {
            throw new SQLException("Error deleting beds by dormitory ID", e);
        }
    }

    public Integer getBedByBedId(Integer bedId) throws SQLException {
        String sql = "SELECT * FROM bed WHERE id = ?";
        Object[] params = {bedId};
        return selectOne(sql,params).getDormitoryId();
    }

    public void updateBedInfo(int id, String s) throws SQLException {
        try {
            String sql = "UPDATE bed SET bed_info = ? WHERE id = ?";
            Object[] params = {s,id};
            update(sql, params);
        } catch (SQLException e) {
            throw new SQLException("Error updating bed info", e);
        }
    }
}
