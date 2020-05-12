package com.senlainc.library.aspect;

import java.util.Arrays;
import java.util.stream.Collectors;

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
	public void logBeforeDaoCall(JoinPoint jp) {
		String args = Arrays.stream(jp.getArgs()).map(a -> a.toString()).collect(Collectors.joining(","));

		LOG.info("Call method: {} with parametrs: [{}]", jp.toString(), args);
	}
	
	@After("controllerClassMethods()")
	public void logAfterDaoCall(JoinPoint jp) {
		LOG.info("After method {}", jp.toString());
	}
	
	@AfterThrowing(pointcut = "controllerClassMethods()", throwing = "ex")
	public void logAfterThrowingDaoCall(JoinPoint jp, Throwable ex)  throws Throwable {

		LOG.error("Method Signature: {} Exception: {}", jp.toString(), ex.getMessage());
	}


}
