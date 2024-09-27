package org.example.dest_service.configuration.datasource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"org.example.dest_service.repository.pos"},
        entityManagerFactoryRef = "posEntityManagerFactory",
        transactionManagerRef = "posTransactionManager"
)
public class PosDataSourceConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties("pos.datasource")
    public DataSourceProperties posDatasourceProperties(){
        return new DataSourceProperties();
    }
    @Primary
    @Bean
    public DataSource posDataSource(@Qualifier("posDatasourceProperties") DataSourceProperties posDatasourceProperties) {
        return posDatasourceProperties.initializeDataSourceBuilder().build();
    }
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean posEntityManagerFactory(
            @Qualifier("posDataSource") DataSource posDataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(posDataSource)
                .packages(
                        "org.example.dest_service.entity.pos",
                        //"org.example.api.entity",
                        "org.example.dest_service.entity.common") // Package containing JPA entities
                .persistenceUnit("pos") // Specify persistence unit
                .build();
    }
    @Primary
    @Bean
    public PlatformTransactionManager posTransactionManager(
            @Qualifier("posEntityManagerFactory") EntityManagerFactory posEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(posEntityManagerFactory);
        return transactionManager;
    }
}
