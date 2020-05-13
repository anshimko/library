package com.senlainc.library.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:testPersistence.properties")
@EnableTransactionManagement
public class H2TestPersistenceConfig implements WebMvcConfigurer{
	
	@Autowired
    private Environment env;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
        .addResourceHandler("/resources/**")
        .addResourceLocations("/resources/"); 
	}
	
	@Bean(destroyMethod = "close")
	@Profile("test")
	public ComboPooledDataSource dataSource(@Value("${jdbc.driver}") String driverClass,
											@Value("${jdbc.url}") String url,
											@Value("${jdbc.user}") String user,
											@Value("${jdbc.password}") String password,
											@Value("${jdbc.minPoolSize}") String minPoolSize,
											@Value("${jdbc.maxPoolSize}") String maxPoolSize,
											@Value("${jdbc.maxIdleTime}") String maxIdleTime) throws PropertyVetoException {
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(driverClass);
		dataSource.setJdbcUrl(url);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setMinPoolSize(Integer.valueOf(minPoolSize));
		dataSource.setMaxPoolSize(Integer.valueOf(maxPoolSize));
		dataSource.setMaxIdleTime(Integer.valueOf(maxIdleTime));
		return dataSource;
	}
	
	@Bean
	@Profile("test")

	public LocalSessionFactoryBean sessionFactory(ComboPooledDataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan("com.senlainc.library.entity");
		sessionFactory.setHibernateProperties(additionalProperties());
		return sessionFactory;
	}
	
	@Bean
	@Profile("test")

	public HibernateTransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}
	
	@Bean
	@Profile("test")

	public javax.validation.Validator localValidatorFactoryBean() {
	    return new LocalValidatorFactoryBean();
	}
	
	@Bean
	@Profile("test")

	public MethodValidationPostProcessor methodValidationPostProcessor() {
	     return new MethodValidationPostProcessor();
	}
	
	final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        
    
        return hibernateProperties;
    }

}
