package com.foxminded.university.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class SessionFactoryInitializer {
    private final Logger logger = LoggerFactory.getLogger(SessionFactoryInitializer.class);

    @Bean (name = "sessionFactory")
    public SessionFactory initialize() {
        logger.info("initialization");
        return new Configuration().configure().buildSessionFactory();
    }
}
