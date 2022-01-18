package io.github.daniil547;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static HikariDataSource dataSource;

    @BeforeAll
    public static void initTests() {
        HikariConfig cfg = new HikariConfig();
        cfg.setUsername("test");
        cfg.setPassword("test");
        cfg.setJdbcUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSql");
        cfg.setDriverClassName("org.h2.Driver");
        cfg.setMaximumPoolSize(50);
        dataSource = new HikariDataSource(cfg);
        Flyway flyway = Flyway.configure()
                              .dataSource(dataSource)
                              .load();
        flyway.migrate();
    }
}
