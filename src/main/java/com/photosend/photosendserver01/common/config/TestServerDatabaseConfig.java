package com.photosend.photosendserver01.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class TestServerDatabaseConfig {
    private String jdbcUrl = "jdbc:mysql://ec2-13-124-205-33.ap-northeast-2.compute.amazonaws.com:3306/testdb";
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
