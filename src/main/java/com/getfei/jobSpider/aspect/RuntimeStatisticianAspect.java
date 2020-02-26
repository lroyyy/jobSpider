package com.getfei.jobSpider.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 运行时间统计切面
 * @author lroy
 *
 */
@Aspect
@Component
public class RuntimeStatisticianAspect {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(public * com.getfei.jobSpider.service.IAnalyzerService.*(..)))")
	public void analyzerAspect() {

	}

	@Pointcut("execution(public * com.getfei.jobSpider.service.IFetcherService.*(..)))")
	public void fetcherAspect() {

	}

	@Pointcut("execution(public * com.getfei.jobSpider.controller.JobController.*(..)))")
	public void jobControllerAspect() {

	}

	@Around("jobControllerAspect()")
	public Object doAroundJobController(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("控制器启动。");
		Object o = pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("控制器关闭，运行耗时：" + (endTime - startTime) + "毫秒。");
		return o;
	}

	@Around("analyzerAspect()")
	public Object doAroundAnalyzer(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("分析器启动。");
		Object o = pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("分析器关闭，运行耗时：" + (endTime - startTime) + "毫秒。");
		return o;
	}

	@Around("fetcherAspect()")
	public Object doAroundFetcher(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("爬取器启动。");
		Object o = pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("爬取器关闭，运行耗时：" + (endTime - startTime) + "毫秒。");
		return o;
	}

}
