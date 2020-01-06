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
public class PositionDaoImpl implements IPositionDao{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void save(Position position) {
		mongoTemplate.save(position,"position");
	}

	@Override
	public List<Position> findAll() {
		List<Position> positions=mongoTemplate.findAll(Position.class,"position");
		return positions;
	}

	@Override
	public Position findOne(String positionName) {
		return mongoTemplate.findOne(Query.query(Criteria.where("name").is(positionName)), Position.class);
	}
	
}
