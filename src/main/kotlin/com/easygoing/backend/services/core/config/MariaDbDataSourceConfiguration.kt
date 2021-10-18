package com.easygoing.backend.services.core.config

import com.easygoing.backend.services.BackendApplication
import com.easygoing.backend.services.core.annotations.MariaDbDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackageClasses = [BackendApplication::class],
    transactionManagerRef = "mariaDbTransactionManager",
    entityManagerFactoryRef = "mariaDbEntityManagerFactory",
    includeFilters = [ComponentScan.Filter(MariaDbDataSource::class)]
)
@ConfigurationProperties(prefix = "datasource.mariadb")
@EnableTransactionManagement
class MariaDbDataSourceConfiguration {

    lateinit var url : String
    lateinit var username: String
    lateinit var password: String
    lateinit var driverClassName: String
    lateinit var dialect: String


    @Bean
    fun mariaDbDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build()
    }

    @Bean
    fun mariaDbEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("mariaDbDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages(BackendApplication::class.java)
            .persistenceUnit("mariaDb")
            .properties(mapOf(Pair("hibernate.dialect", dialect)))
            .build()
    }

    @Bean
    fun mariaDbTransactionManager(
        @Qualifier("mariaDbEntityManagerFactory") mariaDbEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(mariaDbEntityManagerFactory)
    }

}