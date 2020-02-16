package com.getfei.jobSpider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.service.IAnalysisResultService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("analysisResult")
public class AnalysisResultController extends BaseController{
	
	@Autowired
	private IAnalysisResultService analysisResultService;
	
	/**清空所有analysisResult*/
	@DeleteMapping()
	public ResponseResult<String> clear(){
		analysisResultService.clear();
		ResponseResult<String> rr=new ResponseResult<String>(SUCCESS);
		return rr;
	}
}
