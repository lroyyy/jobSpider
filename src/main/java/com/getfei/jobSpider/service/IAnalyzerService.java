package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;

public interface IAnalyzerService {
	
	public AnalysisResult analyse(FetchedResult fetchedResult);
	
}
