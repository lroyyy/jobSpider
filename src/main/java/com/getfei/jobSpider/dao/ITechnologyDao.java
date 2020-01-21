package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyDao {
	
	/**查找所有*/
	List<Technology> findAll();
	
	/**插入*/
	void insert(Technology technology);
	
	/**根据类型查找*/
	List<Technology> findByType(String type);
	
	/**查找所有类型*/
	List<String> findAllType();
	
}
