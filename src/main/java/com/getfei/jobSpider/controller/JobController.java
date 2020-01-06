package com.getfei.jobSpider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.TechnologyType;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.service.IWebFetcherService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("job")
public class JobController extends BaseController {

	@Autowired
	private IWebFetcherService webFetcherService;
	@Autowired
	private IAnalyzerService analyzerService;

	@GetMapping("list")
	public ResponseResult<AnalysisResult> getJobs(@RequestParam("position") String positionName) throws Exception {
		FetchedResult fetchResult = webFetcherService.fetchJobs(positionName);
		AnalysisResult result = analyzerService.analyse(fetchResult);
		return new ResponseResult<>(SUCCESS, result);
	}

	@GetMapping("test1")
	public ResponseResult<Map<TechnologyType, Integer>> getT() throws Exception {
		Map<TechnologyType, Integer> m = new HashMap<>();
		m.put(new TechnologyType("类型1"), 1);
		m.put(new TechnologyType("类型2"), 2);
		m.put(new TechnologyType("类型3"), 3);
		return new ResponseResult<>(SUCCESS, m);
	}

	@GetMapping("test")
	public ResponseResult<List<Map.Entry<TechnologyType, Integer>>> getTe() throws Exception {

		Map<TechnologyType, Integer> m = new HashMap<>();
		m.put(new TechnologyType("类型1"), 1);
		m.put(new TechnologyType("类型2"), 2);
		m.put(new TechnologyType("类型3"), 3);
		List<Map.Entry<TechnologyType, Integer>> l = new ArrayList<Map.Entry<TechnologyType, Integer>>(m.entrySet());
		return new ResponseResult<>(SUCCESS, l);
	}

}
