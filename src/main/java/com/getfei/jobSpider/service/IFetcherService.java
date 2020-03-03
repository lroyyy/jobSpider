package com.getfei.jobSpider.service;

import com.getfei.jobSpider.entity.FetchedResult;

/**
 * 爬取服务接口
 * @author getfei
 *
 */
public interface IFetcherService {

	/**爬取工作*/
	FetchedResult fetchJobs(String keyword,String positionCode) throws Exception;
	
	/**爬取预览*/
	int getTotalPage(String keyword,String positionCode) throws Exception;
	
}
