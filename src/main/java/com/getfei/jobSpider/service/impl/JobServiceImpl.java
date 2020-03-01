package com.getfei.jobSpider.service.impl;

import java.util.List;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Job;
import com.getfei.jobSpider.service.IJobService;

public class JobServiceImpl implements IJobService{

	@Override
	public FetchedResult fetchJobs(String keyword, String positionCode) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPage(String keyword, String positionCode) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AnalysisResult analyseJobs(FetchedResult fetchedResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> filterJob(List<Job> jobs) {
		// TODO Auto-generated method stub
		return null;
	}

}
