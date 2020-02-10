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
			@RequestParam("position") String position, @RequestParam(name = "way", required = false) String way) {
		AnalysisResult analysisResult = null;
		ResponseResult<AnalysisResult> rr = new ResponseResult<>();
		try {
			//预览
			AnalysisResult historicalAnalysisResult = preview(keyword, position);
			if("getNew".equals(way)) {//强制获取最新结果
				logger.info("强制获取最新结果。");
				analysisResult=getNew(keyword, position);
				//删除旧结果
				if(historicalAnalysisResult != null) {
					analysisResultService.delete(historicalAnalysisResult);
				}
			}else {//优先获取已有结果
				if (historicalAnalysisResult != null) {//返回已有结果
					logger.info("尝试查找旧结果，找到了");
					analysisResult = historicalAnalysisResult;
				} else {//第一次
					logger.info("尝试查找旧结果，没找到");
					analysisResult=getNew(keyword, position);
				}
			}
		} catch (Exception e) {
			logger.warn("报错！信息："+e.getMessage());
			e.printStackTrace();
			rr.setState(ERROR);
			rr.setMessage(e.getMessage());
			return rr;
		}
		rr.setData(analysisResult);
		rr.setState(SUCCESS);
		return rr;
	}

	/** 预览结果 */
	private AnalysisResult preview(String keyword, String position) {
		// 在mongodb里查看历史查询
		AnalysisResult analysisResult = analysisResultService.getByKeywordAndPosition(keyword, position);
		if (analysisResult != null) {
			// 已有历史结果
			analysisResult.setIfNew(false);
		}
		return analysisResult;
	}

	/** 获取最新结果 */
	private AnalysisResult getNew(String keyword, String position) throws Exception{
		AnalysisResult analysisResult = null;
		try {
			// 爬取
			FetchedResult fetchResult = webFetcherService.fetchJobs(keyword, position);
			if (fetchResult == null) {
				logger.debug("fetchResult=null");
				throw new Exception("爬取结果为空");
			}
			// 分析
			analysisResult = analyzerService.analyse(fetchResult);
			// 分析结果存入数据库
			analysisResultService.insert(analysisResult);
//			//删除旧结果
//			if(oldResult!=null) {
//				logger.info("oldId="+oldResult.getId());
//				analysisResultService.delete(oldResult);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return analysisResult;
	}
}
