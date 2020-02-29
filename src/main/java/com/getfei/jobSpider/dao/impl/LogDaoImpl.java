package com.getfei.jobSpider.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ILogDao;
import com.getfei.jobSpider.entity.Log;

@Component
public class LogDaoImpl implements ILogDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private String collectionName="log";
	
	@Override
	public void insert(Log log) {
		mongoTemplate.insert(log,collectionName);
	}

	@Override
	public Integer countByType(String type) {
		Long count=mongoTemplate.count(Query.query(Criteria.where("type").is(type)),Log.class, collectionName);
		Integer returnValue=count.intValue();
		return returnValue;
	}

	@Override
	public void truncate() {
		mongoTemplate.dropCollection(collectionName);
		mongoTemplate.createCollection(collectionName);
	}

}
