package com.photosend.photosendserver01.common.config.database;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("local")
public class LocalTestDatabaseConfig {
    private String jdbcUrl = "jdbc:mysql://localhost:3306/testdb?useSSL=false&?characterEncoding=UTF-8";
    private String driverClass = "com.mysql.jdbc.Driver";

    @Bean
    public DataSource getDataSource() {
        String username = "root";
        String password = "crew1207";

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.driverClassName(driverClass);
        return dataSourceBuilder.build();
    }

}
