package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyDao {
	
	List<Technology> findAll();
	
	void insert(Technology technology);
	
}
