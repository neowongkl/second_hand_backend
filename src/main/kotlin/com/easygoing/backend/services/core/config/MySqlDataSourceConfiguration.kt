package com.easygoing.backend.services.core.config

import com.easygoing.backend.services.BackendApplication
import com.easygoing.backend.services.core.annotations.MySqlDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
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
@ConditionalOnProperty(
    value = ["datasource.mysql.enable"],
    havingValue = "true",
    matchIfMissing = false
)
@EnableJpaRepositories(
    basePackageClasses = [BackendApplication::class],
    transactionManagerRef = "mySqlTransactionManager",
    entityManagerFactoryRef = "mySqlEntityManagerFactory",
    includeFilters = [ComponentScan.Filter(MySqlDataSource::class)],
)
@ConfigurationProperties(prefix = "datasource.mysql")
@EnableTransactionManagement
class MySqlDataSourceConfiguration {

    lateinit var url : String
    lateinit var username: String
    lateinit var password: String
    lateinit var driverClassName: String
    lateinit var dialect: String

    @Bean
    fun mySqlDataSource(): DataSource {
        return DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build()
    }

    @Bean
    fun mySqlEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("mySqlDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages(BackendApplication::class.java)
            .persistenceUnit("mySql")
            .properties(mapOf(Pair("hibernate.dialect", dialect)))
            .build()
    }

    @Bean
    fun mySqlTransactionManager(
        @Qualifier("mySqlEntityManagerFactory") mySqlEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(mySqlEntityManagerFactory)
    }

}