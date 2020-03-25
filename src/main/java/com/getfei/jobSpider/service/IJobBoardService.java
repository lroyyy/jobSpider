package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.JobBoard;

public interface IJobBoardService {

	/**查询所有*/
	List<JobBoard> list();
	
	/**写入*/
	void save(JobBoard jobBoard);
	
	/**清空*/
	void clear();
	
}
