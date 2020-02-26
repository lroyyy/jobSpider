package com.getfei.jobSpider.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 缓存切面
 * @author lroy
 *
 */
@Aspect
//@Component
public class CacheAspect {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(public * com.getfei.jobSpider.dao.ITechnologyDao.findAll(..)))")
	public void findAllTechnologyAspect() {

	}
	
	@Around("findAllTechnologyAspect()")
	public Object doAroundJobController(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("控制器启动。");
		Object o = pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("控制器关闭，运行耗时：" + (endTime - startTime) + "毫秒。");
		return o;
	}
	
}
