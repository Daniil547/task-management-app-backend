package io.github.daniil547.common.db;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Migrator {

    @Autowired
    public void migrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                              .dataSource(dataSource)
                              .load();
        flyway.migrate();
    }
}
