package com.photosend.photosendserver01.common.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class LocalTestDatabaseConfig {
    private String jdbcUrl = "jdbc:mysql://localhost:3306/testdb";
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Bean
    public DataSource getDataSource() {
        String username = "root";
        String password = "crew1207!";

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.driverClassName(driverClass);
        return dataSourceBuilder.build();
    }

}
