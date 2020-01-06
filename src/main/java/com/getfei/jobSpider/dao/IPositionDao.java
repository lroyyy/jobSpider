package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Position;

public interface IPositionDao {
	
	void save(Position position);
	
	List<Position> findAll();
	
	Position findOne(String positionName);
	
}
