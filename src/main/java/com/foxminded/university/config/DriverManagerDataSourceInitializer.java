package com.foxminded.university.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DriverManagerDataSourceInitializer {
    private final Logger logger = LoggerFactory.getLogger(DriverManagerDataSourceInitializer.class);
    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://127.0.0.1:5432/university";
    private String user = "universityadmin";
    private String password = "1234";

    @Bean (name = "jdbcTemplate")
    public JdbcTemplate initialize() {
        logger.info("initialization");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
    }
}
