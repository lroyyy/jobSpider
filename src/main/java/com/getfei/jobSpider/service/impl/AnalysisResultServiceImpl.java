package com.getfei.jobSpider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IAnalysisResultDao;
import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.service.IAnalysisResultService;

@Service
public class AnalysisResultServiceImpl extends MongoBaseServiceImpl<AnalysisResult> implements IAnalysisResultService{

	@Autowired
	private IAnalysisResultDao analysisResultDao;
	
	@Override
	public AnalysisResult getByKeywordAndPosition(String keyword, String position) {
		return analysisResultDao.findByKeywordAndPosition(keyword, position);
	}

	@Override
	public void remove(AnalysisResult analysisResult) {
		super.remove(analysisResult);
	}

}
