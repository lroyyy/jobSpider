package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.DevelopLog;

public interface IDevelopLog {

	List<DevelopLog> findAll();
	
	void insert(DevelopLog developLog);
	
}
