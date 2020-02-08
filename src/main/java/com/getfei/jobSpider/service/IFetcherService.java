package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.FetchedResult;

public interface IFetcherService {

	public FetchedResult fetchJobs(String keyword,String positionCode) throws Exception;
	
}
