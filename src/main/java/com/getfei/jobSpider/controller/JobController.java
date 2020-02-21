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
import com.getfei.jobSpider.service.ex.MongoDeleteException;
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
				if(historicalAnalysisResult.getTechnologyCounter()!=null) {
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

	/** 预览结果 
	 * @throws Exception */
	private AnalysisResult preview(String keyword, String position) throws Exception {
		// 在mongodb里查看历史查询
		AnalysisResult analysisResult = analysisResultService.getByKeywordAndPosition(keyword, position);
		if (analysisResult != null) {
			// 已有历史结果
			analysisResult.setIfNew(false);
		}else {
			//找不到历史结果,先爬
			analysisResult=new AnalysisResult();
			int totalPage=webFetcherService.getTotalPage(keyword, position);
			analysisResult.setTotalPage(totalPage);//设置总数
		}
		return analysisResult;
	}

	/** 获取最新结果 */
	private AnalysisResult getNew(String keyword, String position) throws Exception{
		AnalysisResult analysisResult = null;
		try {
			// 爬取
			FetchedResult fetchResult = webFetcherService.fetchJobs(keyword, position);
			// 分析
			analysisResult = analyzerService.analyse(fetchResult);
			if(analysisResult.getTechnologyCounter().size()==0) {
				throw new Exception("匹配不到任何技术，请编辑技术栈后再试。");
			}
			// 分析结果存入数据库
			analysisResultService.insert(analysisResult);
		} catch (Exception e) {
			throw e;
		}
		return analysisResult;
	}
}
