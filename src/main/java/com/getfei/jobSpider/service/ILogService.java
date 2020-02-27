package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.Log;

public interface ILogService {

	/**写入日志*/
	void add(Log log);
	
	/**根据类型统计*/
	long countByType(String type);
	
	/**清空日志*/
	void clear();
	
}
