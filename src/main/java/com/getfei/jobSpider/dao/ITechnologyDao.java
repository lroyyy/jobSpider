package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyDao {
	
	/**查找所有*/
	List<Technology> findAll();
	
	/**插入*/
	void insert(Technology technology);
	
	/**查找所有类型*/
	List<String> findAllType();
	
	/**根据类型查找*/
	List<Technology> findByType(String type);
	
	/**根据名称模糊查找*/
	List<Technology> findByName(String name);
	
	/**根据类型（精确）和名称（模糊）查找*/
	List<Technology> findByTypeAndNameAndAlias(String type,String name,String alias);
	
	
	
}
