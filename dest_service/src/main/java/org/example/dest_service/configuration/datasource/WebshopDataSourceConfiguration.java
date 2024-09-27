package org.example.dest_service.configuration.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"org.example.dest_service.repository.webshop"},
        entityManagerFactoryRef = "webshopEntityManagerFactory",
        transactionManagerRef = "webshopTransactionManager"
)
public class WebshopDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("webshop.datasource")
    public DataSourceProperties webshopDatasourceProperties(){
        return new DataSourceProperties();
    }
    @Bean
    public DataSource webshopDataSource(@Qualifier("webshopDatasourceProperties") DataSourceProperties webshopDatasourceProperties) {
        return webshopDatasourceProperties.initializeDataSourceBuilder().build();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean webshopEntityManagerFactory(
            @Qualifier("webshopDataSource") DataSource webshopDataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(webshopDataSource)
                .packages(
                        "org.example.dest_service.entity.webshop",
                        //"org.example.api.entity",
                        "org.example.dest_service.entity.common") // Package containing JPA entities
                .persistenceUnit("webshop")
                .build();
    }
    @Bean
    public PlatformTransactionManager webshopTransactionManager(
            @Qualifier("webshopEntityManagerFactory") EntityManagerFactory webshopEntityManagerFactory) {
        return new JpaTransactionManager(webshopEntityManagerFactory);
    }
}
