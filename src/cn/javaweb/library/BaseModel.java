package cn.javaweb.library;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class BaseModel <T>{

    protected Connection connection = null;

    public BaseModel(Config config){

        this.connection = Database.getDatabaseConnection(config);
    }

    public Object parseObject(ResultSet rs) throws SQLException {
        return null;

    }

    public BaseModel(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void finalize() throws Throwable {
        log.warn("~~~~~~~~释放数据库资源~~~~~~~~");
        Database.releaseConnection();
        super.finalize();
    }

    public List<T> selectAll(String sql, Object[] params) {
        List<T> list = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add((T)this.parseObject(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("BaseModel.selectAll error: " + e.getMessage());
        }
        return list;
    }


    public T selectOne(String sql, Object[] params) {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            // 执行查询
            try (ResultSet rs = pst.executeQuery()) {
                System.out.println(pst.toString());
                if (rs.next()) {
                    // 解析结果并返回
                    return (T)this.parseObject(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseModel.selectOne error: " + e.getMessage());
        }
        return null;
    }



    public int getCount(String sql, Object[] params) {
        int count = 0;
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }
            // 执行查询
            try (ResultSet rs = pst.executeQuery()) {
                System.out.println(pst.toString());
                if (rs.next()) {
                    count = rs.getInt("cnt");  // 假设查询结果有 cnt 字段
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("BaseModel.getCount error: " + e.getMessage());
        }
        return count;
    }


    public int update(String sql, Object[] params) throws SQLException {
        int affectedRows = 0;

        // 使用 try-with-resources 来自动关闭 PreparedStatement
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            // 执行更新操作
            affectedRows = pst.executeUpdate();
            log.info("SQL executed: " + pst.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            log.error("BaseModel.update error: " + e.getMessage());
        }

        return affectedRows;
    }

    public Object getValue(String sql, Object[] params, String field, String dataType) {
        // 使用 try-with-resources 来自动关闭 PreparedStatement 和 ResultSet
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pst.setObject(i + 1, params[i]);
                }
            }

            // 执行查询
            try (ResultSet rs = pst.executeQuery()) {
                log.info("Executing SQL: " + pst.toString());

                if (rs.next()) {
                    // 根据 dataType 返回不同类型的值
                    switch (dataType.toLowerCase()) {
                        case "string":
                            return rs.getString(field);
                        case "int":
                            return rs.getInt(field);
                        case "long":
                            return rs.getLong(field);
                        case "float":
                            return rs.getFloat(field);
                        default:
                            // 返回其他数据类型
                            return rs.getObject(field);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("BaseModel.getValue error: " + e.getMessage());
        }

        return null;  // 如果没有找到数据或发生错误，返回 null
    }

}
