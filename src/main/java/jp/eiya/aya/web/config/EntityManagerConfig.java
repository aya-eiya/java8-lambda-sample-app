package jp.eiya.aya.web.config;

import jp.eiya.aya.web.dao.manage.Java8LambdaSampleEntityManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

@Configuration
public class EntityManagerConfig {
    @Inject
    private DataSource java8LambdaSampleDataSource;

    @Bean
    @Singleton
    public Java8LambdaSampleEntityManagerFactoryBean Java8LambdaSampleEntityManagerFactoryBean(){
        return new Java8LambdaSampleEntityManagerFactoryBean(java8LambdaSampleDataSource);
    }

    @Bean
    @Singleton
    public EntityManager java8LambdaSampleEntityManger(){
        return Java8LambdaSampleEntityManagerFactoryBean().entityManager();
    }

    @Bean
    public PlatformTransactionManager java8LambdaSampleTxManager(){
        JpaTransactionManager tx = new JpaTransactionManager();
        tx.setEntityManagerFactory(Java8LambdaSampleEntityManagerFactoryBean().entityManagerFactory());
        return tx;
    }
}
