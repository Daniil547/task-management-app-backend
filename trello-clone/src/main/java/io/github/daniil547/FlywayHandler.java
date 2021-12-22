package io.github.daniil547;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FlywayHandler {

    public static void main(String[] args) throws IOException {
        Flyway flyway = createFlyway(createDataSource());
        flyway.migrate();
    }

    private static Flyway createFlyway(DataSource dataSource) throws IOException {
        return Flyway.configure()
                     .dataSource(createDataSource())
                     .load();
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = loadProperties();

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));

        int maxConnections = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnections"));
        cfg.setMaximumPoolSize(maxConnections);

        return new HikariDataSource(cfg);
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = Main.class.getClassLoader()
                                   .getResourceAsStream("application.properties");

        Properties properties = new Properties();
        properties.load(in);

        return properties;

    }
}
