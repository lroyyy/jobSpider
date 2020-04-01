package com.getfei.jobSpider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.common.DataCenter;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("dataCenter")
public class DataCenterController extends BaseController{

	@GetMapping("statuses")
	public ResponseResult<String[]> listStatuses(){
		return new ResponseResult<String[]>(SUCCESS, DataCenter.getStatuses());
	}
	
	@GetMapping("jobBoards")
	public ResponseResult<String[]> listJobBoards(){
		return new ResponseResult<String[]>(SUCCESS, DataCenter.getJobBoards());
	}
	
}
