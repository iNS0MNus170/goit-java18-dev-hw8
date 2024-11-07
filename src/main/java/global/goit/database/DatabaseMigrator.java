package global.goit.database;

import com.zaxxer.hikari.HikariDataSource;
import global.goit.util.PropertiesUtil;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DatabaseMigrator {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrator.class);

    public static void migrateDatabase(HikariDataSource dataSource) {
        try {
            String locations = PropertiesUtil.get("flyway.locations");
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations(locations)
                    .load();
            flyway.migrate();
        } catch (Exception e) {
            logger.error("Failed to migrate the database", e);
            throw new RuntimeException(e);
        }
    }
}

