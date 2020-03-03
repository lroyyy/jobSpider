package com.getfei.jobSpider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.ILogDao;
import com.getfei.jobSpider.dao.impl.LogDaoImpl;
import com.getfei.jobSpider.entity.Log;
import com.getfei.jobSpider.service.ILogService;

@Service
public class LogServiceImpl extends MongoBaseServiceImpl<Log> implements ILogService{

	@Autowired
	private ILogDao logDao;
	
	@Override
	public Integer countByType(String type) {
		return logDao.countByType(type);
	}

}
