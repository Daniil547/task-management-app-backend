package io.github.daniil547;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("io.github.daniil547")
public class Config {

    @Bean
    public Properties applicationProperties() throws IOException {
        InputStream in = Config.class.getClassLoader()
                                     .getResourceAsStream("application.properties");

        Properties properties = new Properties();
        properties.load(in);
        return properties;
    }

    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = applicationProperties();

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));

        int maxConnections = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnections"));
        cfg.setMaximumPoolSize(maxConnections);
        return new HikariDataSource(cfg);
    }


}
