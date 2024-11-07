package global.goit.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import global.goit.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    public static HikariDataSource createDataSource() {
        try {
            String url = PropertiesUtil.get("db.url");
            String username = PropertiesUtil.get("db.username");
            String password = PropertiesUtil.get("db.password");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            return new HikariDataSource(config);
        } catch (Exception e) {
            logger.error("Failed to create Hikari DataSource", e);
            throw new RuntimeException(e);
        }
    }
}

