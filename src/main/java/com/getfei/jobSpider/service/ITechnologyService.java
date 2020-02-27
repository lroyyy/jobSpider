package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.util.MongoResult;

public interface ITechnologyService {

	/**查询所有*/
	List<Technology> list();
	
	/**根据类型查询*/
	List<Technology> getByType(String type);
	
	/**根据名称模糊查询*/
	List<Technology> getByNameLike(String name);
	
	/** 新增	 */
	MongoResult add(String name,String type,String[] aliases);
	
	/**查询所有类型*/
	List<String> ListType();
	
	/**根据类型（精确）和名称（模糊）和别名（模糊）查找*/
	List<Technology> getByTypeAndNameLikeAndAliasLike(String type,String name,String alias);
	
	/**新增别名*/
	MongoResult addAlias(String name,String type,String alias);
	
	long count();
	
}
