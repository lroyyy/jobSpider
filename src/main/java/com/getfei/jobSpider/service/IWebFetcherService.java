package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.FetchedResult;

public interface IWebFetcherService {

	public FetchedResult fetchJobs(String positionName) throws Exception;
	
}
