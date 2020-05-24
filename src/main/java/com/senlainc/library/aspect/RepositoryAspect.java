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
public class RepositoryAspect {

	private static final Logger LOG = LoggerFactory.getLogger(RepositoryAspect.class);

	@Pointcut("@within(org.springframework.stereotype.Repository)")
	public void repositoryClassMethods() {}

	@Before("repositoryClassMethods()")
	public void logBeforeDaoCall(JoinPoint jp) {

		LOG.info("Call method: {} with parametrs: [{}]", jp.toString());
	}
	
	@After("repositoryClassMethods()")
	public void logAfterDaoCall(JoinPoint jp) {
		LOG.info("After method {}", jp.toString());
	}
	
	
	@AfterThrowing(pointcut = "repositoryClassMethods()", throwing = "ex")
	public void logAfterThrowingDaoCall(JoinPoint jp, Throwable ex)  throws Throwable {

		LOG.error("Method Signature: {} Exception: {}", jp.toString(), ex.getMessage());
	}

}
