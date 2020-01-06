package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Technology;

public interface ITechnologyService {

	List<Technology> list();
	
	boolean add(String name,String type,String[] aliases);
	
}
