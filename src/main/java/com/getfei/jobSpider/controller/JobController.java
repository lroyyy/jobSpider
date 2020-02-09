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
import com.getfei.jobSpider.service.IAnalysisResultService;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.service.IFetcherService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("job")
public class JobController extends BaseController {

	@Autowired
	private IFetcherService webFetcherService;
	@Autowired
	private IAnalyzerService analyzerService;
	@Autowired
	private IAnalysisResultService analysisResultService;

	@GetMapping()
	public ResponseResult<AnalysisResult> list(@RequestParam("keyword") String keyword,
			@RequestParam("position") String position) {
		AnalysisResult analysisResult = null;
		ResponseResult<AnalysisResult> rr = new ResponseResult<>();
		try {
			// 在mongodb里查看历史查询
			AnalysisResult historicalAnalysisResult = analysisResultService.getByKeywordAndPosition(keyword, position);
			if (historicalAnalysisResult != null) {
				analysisResult = historicalAnalysisResult;
				rr.setMessage("返回历史分析。");
			} else {
				//爬取
				FetchedResult fetchResult = webFetcherService.fetchJobs(keyword, position);
				if (fetchResult == null) {
					logger.debug("fetchResult=null");
				}
				//分析
				analysisResult = analyzerService.analyse(fetchResult);
				//分析结果存入数据库
				analysisResultService.insert(analysisResult);
				rr.setMessage("一次全新的分析！");
			}
		} catch (Exception e) {
			rr.setMessage(e.getMessage());
			rr.setState(ERROR);
			return rr;
		}
		rr.setState(SUCCESS);
		rr.setData(analysisResult);
		return rr;
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
