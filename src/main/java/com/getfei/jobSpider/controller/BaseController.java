package com.getfei.jobSpider.controller;

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
	
//	@ExceptionHandler(ServiceException.class)
	public ResponseResult<Void> handleException(Throwable e){
		ResponseResult<Void> rr=new ResponseResult<>();
		rr.setMessage(e.getMessage());
		return rr;
	}
	
}
