package io.github.daniil547.common.db;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;

public class FlywayHandler {

    public static void main(String[] args) throws IOException {
        Flyway flyway = createFlyway(DataSourceManager.createHikariDataSource());
        flyway.migrate();
    }

    private static Flyway createFlyway(DataSource dataSource) throws IOException {
        return Flyway.configure()
                     .dataSource(DataSourceManager.createHikariDataSource())
                     .load();
    }

}
