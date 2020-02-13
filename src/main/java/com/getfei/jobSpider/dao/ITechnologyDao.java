package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyDao {
	
	/**查找所有*/
	List<Technology> findAll();
	
	/**插入*/
	void insert(Technology technology);
	
	/**查询所有类型*/
	List<String> findAllType();
	
	/**根据类型查询*/
	List<Technology> findByType(String type);
	
	/**根据名称查询*/
	List<Technology> findByName(String name);
	
	/**根据名称模糊查询*/
	List<Technology> findByNameLike(String name);
	
	/**根据类型（精确）和名称（模糊）和别名（模糊）查询*/
	List<Technology> findByTypeAndNameLikeAndAliasLike(String type,String name,String alias);
	
	/**根据类型和名称查询*/
	List<Technology> findByTypeAndName(String type,String name);
	
	/**根据别名查询*/
	List<Technology> findByAlias(String alias);
	
}
