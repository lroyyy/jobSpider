package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyService {

	List<Technology> list();
	
	List<Technology> getByType(String type);
	
	List<Technology> getByName(String name);
	
	boolean add(String name,String type,String[] aliases);
	
	List<String> ListType();
	
	/**根据类型（精确）和名称（模糊）和别名（模糊）查找*/
	List<Technology> getByTypeAndNameAndAlias(String type,String name,String alias);
	
}
