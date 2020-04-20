package com.senlainc.library.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableTransactionManagement
public class PersistenceConfig implements WebMvcConfigurer{
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
        .addResourceHandler("/resources/**")
        .addResourceLocations("/resources/"); 
	}
	
	@Bean(destroyMethod = "close")
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
	public LocalSessionFactoryBean sessionFactory(ComboPooledDataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan("com.senlainc.library.entity");
		sessionFactory.setHibernateProperties(new Properties() {{
			put("hibernate.show_sql", true);
			put("hibernate.dialect", org.hibernate.dialect.MySQLDialect.class);
		}});
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory.getObject());
		return transactionManager;
	}
	
	@Bean
	public javax.validation.Validator localValidatorFactoryBean() {
	    return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
	     return new MethodValidationPostProcessor();
	}

}
