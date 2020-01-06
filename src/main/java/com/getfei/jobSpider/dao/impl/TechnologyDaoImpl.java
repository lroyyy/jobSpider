package com.getfei.jobSpider.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Technology;
import com.mongodb.DuplicateKeyException;

@Component
public class TechnologyDaoImpl implements ITechnologyDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Technology> findAll() {
		return mongoTemplate.findAll(Technology.class, "technologystack");
	}

	@Override
	public void insert(Technology technology) throws DuplicateKeyException{
		mongoTemplate.insert(technology, "technologystack");
	}

}
