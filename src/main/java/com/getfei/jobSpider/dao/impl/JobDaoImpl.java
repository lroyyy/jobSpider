package com.getfei.jobSpider.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IJobDao;
import com.getfei.jobSpider.entity.Job;

@Component
public class JobDaoImpl implements IJobDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void save(Job job) {
		mongoTemplate.save(job,"job");
	}

}
