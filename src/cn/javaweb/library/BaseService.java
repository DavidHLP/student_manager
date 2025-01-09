package cn.javaweb.library;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
@Getter
@Slf4j
public class BaseService {

    private Connection connection = null;

    public BaseService(Config config) {
        this.connection = Database.getDatabaseConnection(config);
        try {
            assert this.connection != null;
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            log.error("Failed to disable auto-commit mode: " + e.getMessage(), e);
        }
    }

    protected void finalize() throws Throwable {
        log.warn("~~~~~~~~释放数据库资源~~~~~~~~");
        connection.close();
        Database.releaseConnection();
        super.finalize();
    }

    public void commitTransaction() throws SQLException {
        try {
            connection.commit();
            log.info("Transaction committed successfully.");
        } catch (SQLException e) {
            log.error("Transaction commit error: " + e.getMessage(), e);
            throw e;
        }
    }

    public void rollbackTransaction() throws SQLException {
        try {
            connection.rollback();
            log.info("Transaction rolled back successfully.");
        } catch (SQLException e) {
            log.error("Transaction rollback error: " + e.getMessage(), e);
            throw e;
        }
    }
}
