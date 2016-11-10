package jp.eiya.aya.web.dao.manage;

import jp.eiya.aya.web.model.dao.base.DAOModel;
import jp.eiya.aya.web.util.config.PropertiesBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import org.hibernate.dialect.H2Dialect;

public class Java8LambdaSampleEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean{
    protected PropertiesBuilder jpaProperties(){
        return ()->{
            Properties props = new Properties();
            props.setProperty("hibernate.dialect",H2Dialect.class.getTypeName());
            props.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
            props.setProperty("hibernate.hbm2ddl.auto","update");
            return props;
        };
    }

    public Java8LambdaSampleEntityManagerFactoryBean(DataSource java8LambdaSampleDataSource){
        setDataSource(java8LambdaSampleDataSource);
        setPackagesToScan(DAOModel.class.getPackage().getName().replace(".base",""));
        setJpaDialect(new HibernateJpaDialect());
        setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        setJpaProperties(jpaProperties().build());
        afterPropertiesSet();
    }

    public EntityManagerFactory entityManagerFactory(){
        return createNativeEntityManagerFactory();
    }

    public EntityManager entityManager(){
        return createNativeEntityManagerFactory().createEntityManager();
    }
}
