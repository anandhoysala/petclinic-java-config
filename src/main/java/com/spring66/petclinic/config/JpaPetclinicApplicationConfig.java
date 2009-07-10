package com.spring66.petclinic.config;

import com.spring66.petclinic.service.Clinic;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.spring66.petclinic.jpa.EntityManagerClinic;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.ExternalBean;
import org.springframework.config.java.annotation.ExternalValue;
import org.springframework.config.java.annotation.Import;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;
import org.springframework.config.java.support.ConfigurationSupport;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(ExternalDataSourceConfig.class)
public abstract class JpaPetclinicApplicationConfig extends ConfigurationSupport {

    // Each of the following @ExternalValues are provided by db/jdbc.properties
    protected abstract 
    @ExternalValue("jpa.showSql")
    boolean showJpaSql();

    protected abstract 
    @ExternalValue("jpa.database")
    String databaseType();

    protected abstract 
    @ExternalValue("hibernate.show_sql")
    boolean showSql();

    protected abstract 
    @ExternalValue("hibernate.hbm2ddl.auto")
    String hbm2ddlAuto();

    protected abstract 
    @ExternalValue("hibernate.generate_statistics")
    boolean generateStatistics();

    protected abstract 
    @ExternalValue("hibernate.dialect")
    String databasePlatform();

    protected abstract 
    @ExternalValue("jpa.database")
    Database database();

    /** Provided by {@link EmbeddedDataSourceConfig#dataSource()} */
    protected abstract 
    @ExternalBean
    DataSource dataSource();

    /**
     * PetClinic's central data access object using JPA EntityManager
     * <p/>
     * Note that {@link EntityManagerClinic} uses {@code @PersistenceContext} to
     * inject an {@link EntityManager} instance created by {@link #entityManagerFactory()}.
     */
    public 
    @Bean
    Clinic clinic() {
        return new EntityManagerClinic();
    }

    /**
     * FactoryBean that creates a JPA EntityManagerFactory according to JPA's standard
     * container bootstrap contract.
     * 
     * @see ConfigurationSupport#getObject(FactoryBean)
     */
    public 
    @Bean
    EntityManagerFactory entityManagerFactory() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", databasePlatform());
        jpaProperties.put("hibernate.show_sql", showSql());
        jpaProperties.put("hibernate.generate_statistics", generateStatistics());
        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto());

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJpaProperties(jpaProperties);
        em.setDataSource(dataSource());
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setLoadTimeWeaver(loadTimeWeaver());
        return this.getObject(em, EntityManagerFactory.class);
    }

    /**
     * JpaVendorAdapter implementation for Hibernate EntityManager.
     */
    public 
    @Bean
    JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(showJpaSql());
        adapter.setDatabasePlatform(databasePlatform());
        adapter.setDatabase(database());
        return adapter;
    }

    /**
     * LoadTimeWeaver relying on VM Instrumentation.
     */
    public 
    @Bean
    LoadTimeWeaver loadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    /**
     * PlatformTransactionManager implementation for a single JPA EntityManagerFactory.
     */
    public 
    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
