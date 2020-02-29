package com.getfei.jobSpider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.service.ILogService;
import com.getfei.jobSpider.service.ITechnologyService;
import com.getfei.jobSpider.util.ResponseResult;
import com.getfei.jobSpider.util.data.Statistics;

@RestController
@RequestMapping("statistics")
public class StatisticsController extends BaseController{
	
	@Autowired
	private ILogService logService;
	
	@Autowired
	private ITechnologyService technologyService;
	
	@GetMapping
	public ResponseResult<Statistics> get(){
		ResponseResult<Statistics> rr=new ResponseResult<>();
		Integer visitCount=logService.countByType("visit");
		Integer technologyCount=technologyService.count();
		Integer analysisCount=logService.countByType("analysis");
		Statistics statistics=new Statistics();
		statistics.setVisitCount(visitCount);
		statistics.setTechnologyCount(technologyCount);
		statistics.setAnalysisCount(analysisCount);
		rr.setData(statistics);
		rr.setState(SUCCESS);
		return rr;
	}
	
}
