package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.AnalysisResult;

public interface IAnalysisResultService {

	void insert(AnalysisResult analysisResult);
	
	AnalysisResult getByKeywordAndPosition(String keyword, String position);

	void delete(AnalysisResult analysisResult);
	
	void clear();
	
}
