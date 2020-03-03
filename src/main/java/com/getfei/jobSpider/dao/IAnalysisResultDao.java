package com.getfei.jobSpider.dao;

import com.getfei.jobSpider.entity.AnalysisResult;

public interface IAnalysisResultDao extends IMongoTemplateDao<AnalysisResult>{
	
//	/**写入*/
//	void insert(AnalysisResult analysisResult);
	
	/**根据关键字和位置查询*/
	AnalysisResult findByKeywordAndPosition(String keyword,String position);

//	/**清空所有*/
//	void truncate();
	
}
