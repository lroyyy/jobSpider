package com.getfei.jobSpider.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IPositionDao;
import com.getfei.jobSpider.entity.Position;

@Component
public class PositionDaoImpl extends MongoTemplateDaoImpl<Position> implements IPositionDao {

//	@Autowired
//	private MongoTemplate mongoTemplate;
//	private String collectionName = "position";

	
	@Autowired
	public PositionDaoImpl() {
//		setCollectionName("position");
	}
	
//	@Override
//	public void save(Position position) {
//		mongoTemplate.save(position, collectionName);
//	}

//	@Override
//	public List<Position> findAll() {
//		List<Position> positions = mongoTemplate.findAll(Position.class, collectionName);
//		return positions;
//	}

	@Override
	public Position findByName(String name) {
		return findByField("name", name);
	}

	@Override
	public Position findByCode(String code) {
		return findByField("code", code);
	}

}
