package com.getfei.jobSpider.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.Log;
import com.getfei.jobSpider.service.ILogService;
import com.getfei.jobSpider.util.ResponseResult;

/**
 * 数据统计切面
 * @author lroy
 *
 */
@Aspect
@Component
public class DataStatisticianAspect {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ILogService logService;

	@Pointcut("execution(public * com.getfei.jobSpider.controller.JobController.list(..)))")
	public void analyzerAspect() {

	}

	@Around("analyzerAspect()")
	public Object doAroundAnalyzer(ProceedingJoinPoint pjp) throws Throwable {
		String ip = (String) pjp.getArgs()[0];
		ResponseResult<AnalysisResult> rr = (ResponseResult<AnalysisResult>)pjp.proceed();
		String keyword=rr.getData().getKeyword();
		String position=rr.getData().getPosition();
		Log log=new Log(ip);
		log.setType("analysis");
		log.setKeyword(keyword);
		log.setPosition(position);
		logService.save(log);
		return rr;
	}

}
