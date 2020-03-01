package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Job;

public interface IJobService {

	/**爬取网页，返回爬取后的结果*/
	FetchedResult fetchJobs(String keyword,String positionCode) throws Exception;
	
	/**获取总页数*/
	int getTotalPage(String keyword,String positionCode) throws Exception;
	
	/**分析工作*/
	AnalysisResult analyseJobs(FetchedResult fetchedResult);
	
	/**筛选工作*/
	List<Job> filterJob(List<Job> jobs);
	
}
