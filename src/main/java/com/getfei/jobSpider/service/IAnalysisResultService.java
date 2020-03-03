package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.AnalysisResult;

public interface IAnalysisResultService {

	void save(AnalysisResult analysisResult);
	
	AnalysisResult getByKeywordAndPosition(String keyword, String position);

	void clear();
	
	void remove(AnalysisResult analysisResult);
	
}
