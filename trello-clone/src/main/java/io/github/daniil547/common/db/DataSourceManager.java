package io.github.daniil547.common.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.daniil547.common.util.PropertyLoader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceManager {
    public static DataSource createDataSource() {
        Properties properties = PropertyLoader.loadProperties();

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));

        int maxConnections = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnections"));
        cfg.setMaximumPoolSize(maxConnections);

        return new HikariDataSource(cfg);
    }

    public static Connection getConnection() throws SQLException {
        return createDataSource().getConnection();
    }
}