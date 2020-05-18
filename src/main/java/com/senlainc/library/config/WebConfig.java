package com.senlainc.library.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.senlainc.library")
public class WebConfig implements WebMvcConfigurer{
	
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setContentType("text/html;charset=UTF-8");
		viewResolver.setCache(false);
		return viewResolver;
	}
	
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		   RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
		   requestMappingHandlerAdapter.setSupportedMethods(
		       "POST", "GET", "PUT", "DELETE", "OPTIONS", "HEAD");
		   final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		   converter.getObjectMapper().setSerializationInclusion(Include.NON_EMPTY);
		   List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		   messageConverters.add(converter);
		   requestMappingHandlerAdapter.setMessageConverters(messageConverters);
		   return requestMappingHandlerAdapter;
		 }
		
}
