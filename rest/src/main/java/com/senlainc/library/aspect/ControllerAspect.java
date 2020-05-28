package com.senlainc.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(ControllerAspect.class);

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void controllerClassMethods() {}

	@Before("controllerClassMethods()")
	public void logBeforeControllerCall(JoinPoint jp) {
		
		LOG.info("Call method: {} with parametrs: [{}]", jp.toString());
	}
	
	@After("controllerClassMethods()")
	public void logAfterControllerCall(JoinPoint jp) {
		LOG.info("After method {}", jp.toString());
	}
	
	@AfterThrowing(pointcut = "controllerClassMethods()", throwing = "ex")
	public void logAfterThrowingControllerCall(JoinPoint jp, Throwable ex)  throws Throwable {

		LOG.error("Method Signature: {} Exception: {}", jp.toString(), ex.getMessage());
	}


}
