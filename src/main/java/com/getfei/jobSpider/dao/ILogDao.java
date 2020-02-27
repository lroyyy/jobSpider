package com.getfei.jobSpider.dao;

import com.getfei.jobSpider.entity.Log;

public interface ILogDao {
	
	/**写入日志*/
	void insert(Log log);
	
	/**根据类型统计*/
	long countByType(String type);
	
	/**清空日志*/
	void truncate();
	
}
