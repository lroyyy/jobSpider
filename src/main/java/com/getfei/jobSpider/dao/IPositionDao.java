package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Position;

public interface IPositionDao {
	
	void insert(Position position);
	
	List<Position> findAll();
	
	Position findByName(String name);
	
	Position findByCode(String code);
	
}
