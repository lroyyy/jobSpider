package com.getfei.jobSpider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.TechnologyType;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.service.IPositionService;
import com.getfei.jobSpider.service.IWebFetcherService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("position")
public class PositionController extends BaseController{

	@Autowired
	private IPositionService positionService;
	
	@GetMapping("/")
	public void position() {
		positionService.getPosition();
	}
}
