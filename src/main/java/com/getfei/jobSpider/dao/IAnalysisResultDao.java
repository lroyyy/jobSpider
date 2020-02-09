package com.getfei.jobSpider.dao;

import com.getfei.jobSpider.entity.AnalysisResult;

public interface IAnalysisResultDao {
	
	/**写入*/
	void save(AnalysisResult analysisResult);
	
	/**根据关键字和位置查询*/
	AnalysisResult findByKeywordAndPosition(String keyword,String position);

}
