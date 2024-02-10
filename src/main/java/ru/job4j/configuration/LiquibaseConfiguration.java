package ru.job4j.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Конфиг для инициализации liquibase
 */
@Configuration
@PropertySource(value = "classpath:db/liquibase.properties")
@RequiredArgsConstructor
public class LiquibaseConfiguration {
    private final DataSource dataSource;
    @Value("${changeLogFile}")
    private String defaultLiquibaseChangelog;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(defaultLiquibaseChangelog);
        return liquibase;
    }
}
