package com.foxminded.university.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ResourceBundle;

public class DriverManagerDataSourceInitializer {
    private final String url;
    private final String user;
    private final String password;

    public DriverManagerDataSourceInitializer(String propertyFile) {
        ResourceBundle rb = ResourceBundle.getBundle(propertyFile);
        url = rb.getString("url");
        user = rb.getString("user");
        password = rb.getString("password");
    }

    public DriverManagerDataSource initialize() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
