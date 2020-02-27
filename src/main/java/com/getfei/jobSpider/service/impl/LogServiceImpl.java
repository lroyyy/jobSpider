package com.getfei.jobSpider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.ILogDao;
import com.getfei.jobSpider.entity.Log;
import com.getfei.jobSpider.service.ILogService;

@Service
public class LogServiceImpl implements ILogService{

	@Autowired
	private ILogDao logDao;
	
	@Override
	public void add(Log log) {
		logDao.insert(log);
	}

	@Override
	public long countByType(String type) {
		return logDao.countByType(type);
	}

	@Override
	public void clear() {
		logDao.truncate();
	}

}
