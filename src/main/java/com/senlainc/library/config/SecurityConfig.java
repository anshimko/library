package com.senlainc.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.senlainc.library.security.AuthProviderImpl;
import com.senlainc.library.security.AuthenticationEntryPoint;


 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan("com.senlainc.library")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    private static String REALM="LIBRARY";
     
    @Autowired
	private AuthProviderImpl authProvider;
    
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    
    @Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  
      http.csrf().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
//        .antMatchers("/users/**").hasRole("ADMIN")
//        .antMatchers("/books/**").hasRole("ADMIN")
//        .antMatchers("/rents/**").hasRole("ADMIN")
        .and().httpBasic().realmName(REALM).authenticationEntryPoint(authEntryPoint)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    }
     
    
     
    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
    
}