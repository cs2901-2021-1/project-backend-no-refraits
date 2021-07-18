package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceManager {

    @Bean("ds2")
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSource secondDs(){
        return DataSourceBuilder.create().build();
    }
}
