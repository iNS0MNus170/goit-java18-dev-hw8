package global.goit.database;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class Database {

    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static HikariDataSource dataSource;

    public static void initialize() {
        if (dataSource == null) {
            dataSource = DataSourceFactory.createDataSource();
            DatabaseMigrator.migrateDatabase(dataSource);
        }
    }

    public static Connection getConnection() {
        if (dataSource == null) {
            String message = "DataSource is not initialized";
            logger.error(message);
            throw new RuntimeException(message);
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error receiving connection", e);
            throw new RuntimeException(e);
        }
    }
}
