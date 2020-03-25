package com.getfei.jobSpider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.service.impl.MongoBaseServiceImpl;
import com.getfei.jobSpider.util.ResponseResult;

public class MongoBaseController<E,I extends MongoBaseServiceImpl<E>> extends BaseController{

	@Autowired
	protected I i;
	
	@GetMapping
	public ResponseResult<List<E>> list(){
		ResponseResult<List<E>> rr=new ResponseResult<>();
		List<E> es=i.list();
		rr.setData(es);
		rr.setState(SUCCESS);
		return rr;
	}
	
	/**清空所有*/
	@DeleteMapping()
	public ResponseResult<String> clear(){
		i.clear();
		ResponseResult<String> rr=new ResponseResult<String>(SUCCESS);
		return rr;
	}
}
