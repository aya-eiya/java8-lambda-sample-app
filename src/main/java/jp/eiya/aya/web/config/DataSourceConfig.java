package jp.eiya.aya.web.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig{

    @Bean
    @Singleton
    @Primary
    public DataSource java8LambdaSampleDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(org.h2.Driver.class.getName())
                .type(JdbcDataSource.class)
                .url("jdbc:h2:mem:javaLambdaSample;DB_CLOSE_DELAY=-1")
                .username("sa")
                .build();
    }

}
