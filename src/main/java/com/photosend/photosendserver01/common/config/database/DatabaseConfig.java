package com.photosend.photosendserver01.common.config.database;

import com.photosend.photosendserver01.common.util.file.KeyValueFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {
    @Autowired
    private KeyValueFileLoader keyValueFileLoader;

    private String jdbcUrl = "jdbc:mysql://photosend-mysql-rds.cq2lmghf0zft.ap-northeast-2.rds.amazonaws.com:3306/photosend";
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Value("${photosend.credential.datasource.file-path}")
    private String dataBaseAccountFilePath;

    @Bean
    public DataSource getDataSource() {
        String username = keyValueFileLoader.getValueFromFile(dataBaseAccountFilePath, "username");
        String password = keyValueFileLoader.getValueFromFile(dataBaseAccountFilePath, "password");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.driverClassName(driverClass);
        return dataSourceBuilder.build();
    }

}
