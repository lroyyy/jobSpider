package com.getfei.jobSpider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.getfei.jobSpider.service.ex.NoSuchPositionException;
import com.getfei.jobSpider.service.ex.ServiceException;
import com.getfei.jobSpider.util.ResponseResult;

/**
 * 控制器类的基类
 * @author lroy
 *
 * @since 2019年4月29日上午11:18:57
 */
public abstract class BaseController {
	/**
	 * 表示响应成功,用户的操作是正确的
	 */
	public static final Integer SUCCESS=200;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(ServiceException.class)
	public ResponseResult<Void> handleException(Throwable e){
		ResponseResult<Void> rr=new ResponseResult<>();
		rr.setMessage(e.getMessage());
		if(e instanceof NoSuchPositionException) {
			//4000-地点查找异常
			rr.setState(4000);
		}
		return rr;
	}
	
}
