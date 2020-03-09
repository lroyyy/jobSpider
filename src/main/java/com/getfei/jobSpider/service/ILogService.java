package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Log;

public interface ILogService {

	/**查询所有日志*/
	List<Log> list();
	
	/**写入日志*/
	void save(Log log);
	
	/**根据类型统计*/
	Integer countByType(String type);
	
	/**根据类型统计今天的日志数*/
	Integer countTodayByType(String type);
	
	/**清空日志*/
	void clear();
	
}
