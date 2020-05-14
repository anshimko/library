package com.senlainc.library.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
//@PropertySource("classpath:test-persistence.properties")
@EnableTransactionManagement
public class H2PersistanceConfig {
	
	@Autowired
    private Environment env;

	@Bean
	@Profile("test")
	public DataSource testDataSource() throws PropertyVetoException {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		return dataSource;
	}
	
	@Bean
	@Profile("test")
	public LocalSessionFactoryBean testSessionFactory(DataSource testDataSource) {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(testDataSource);
		sessionFactory.setPackagesToScan("com.senlainc.library.entity");
		sessionFactory.setHibernateProperties(additionalProperties());
		return sessionFactory;
	}
	
	
	@Bean
	@Profile("test")
	public HibernateTransactionManager transactionManager(@Qualifier("testSessionFactory")LocalSessionFactoryBean sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}
	
	final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        
    
        return hibernateProperties;
    }
}
