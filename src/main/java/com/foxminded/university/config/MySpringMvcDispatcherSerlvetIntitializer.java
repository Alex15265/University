package com.foxminded.university.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private final Logger logger = LoggerFactory.getLogger(DriverManagerDataSourceInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        logger.info("getting SpringConfig");
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        logger.info("getting Servlet Mappings");
        return new String[]{"/"};
    }
}

