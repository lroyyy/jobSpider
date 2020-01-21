package com.getfei.jobSpider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.FetchedResult;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.entity.TechnologyType;
import com.getfei.jobSpider.service.IAnalyzerService;
import com.getfei.jobSpider.service.IPositionService;
import com.getfei.jobSpider.service.IFetcherService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("positions")
public class PositionController extends BaseController{

	@Autowired
	private IPositionService positionService;
	
	@PostMapping("/fetch")
	public void fetchAndInsert() {
		positionService.fetchAndInsert();
	}
	
	@GetMapping()
	public ResponseResult<List<Position>> list() {
		return new ResponseResult<>(SUCCESS,positionService.list());
	}
	
	@GetMapping("/byname")
	public ResponseResult<Position> get(@RequestParam("name") String positionName) throws Exception {
		return new ResponseResult<>(SUCCESS,positionService.getByName(positionName));
	}
}
