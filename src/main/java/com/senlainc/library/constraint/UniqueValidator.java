package com.senlainc.library.constraint;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.senlainc.library.dao.impl.ValidationManager;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {
	  
		@Autowired
	    private ValidationManager manager;
	    
	    private String[] names;
	     
	    @Override
	    public void initialize(Unique annotation) {
	    	SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	        names = annotation.names();
	    }
	  
	    @Override
	    public boolean isValid(Object object, ConstraintValidatorContext context) {
	         
	        boolean result = true;
            
	        if (manager == null || object == null) return true;
	         
	        Class<?> c = object.getClass(); 
	        Object property = null;
	         
	        ConstraintViolationBuilder builder;
	        for (String name : names) {
	         
	        	builder = context.buildConstraintViolationWithTemplate("{Value of " + name +" must be unique}");
	            try {   
	                String m = "get" + String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);        
	                Method method = c.getDeclaredMethod(m);
	                property = method.invoke(object);           
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	                return false;
	            }
	            boolean r = manager.validateUnique(c, name, property);
	            if (!r) {   
	                builder.addNode(name).addConstraintViolation();
	            }
	            result = r;
	         
	        }
	        return result;
	    }
	  
	}
