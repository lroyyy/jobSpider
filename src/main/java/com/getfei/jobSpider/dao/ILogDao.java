package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.Log;

public interface ILogDao {
	
	/**查询所有日志*/
	List<Log> findAll();
	
	/**写入日志*/
	void insert(Log log);
	
	/**根据类型统计日志数*/
	Integer countByType(String type);
	
	/**根据类型统计今天的日志数*/
	Integer countTodayByType(String type);
	
	/**清空日志*/
	void truncate();
	
}
