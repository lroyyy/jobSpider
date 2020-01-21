package com.getfei.jobSpider.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.entity.Technology;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;

@Component
public class TechnologyDaoImpl implements ITechnologyDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	private String collectionName="technology";

	@Override
	public List<Technology> findAll() {
		return mongoTemplate.findAll(Technology.class, collectionName);
	}

	@Override
	public void insert(Technology technology) throws DuplicateKeyException {
		mongoTemplate.insert(technology, collectionName);
	}

	@Override
	public List<Technology> findByType(String type) {
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("type").is(type)),
				Technology.class, collectionName);
		return technologies;
	}

	@Override
	public List<String> findAllType() {
		DBObject obj = new BasicDBObject();
		//添加条件
		obj.put("type", true);
		Query query=new Query();
		query.fields().include("type");
		mongoTemplate.find(query,String.class,collectionName);
		return null;
	}

}
