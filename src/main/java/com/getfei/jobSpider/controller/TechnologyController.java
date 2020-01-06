package com.getfei.jobSpider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.ITechnologyService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("technology")
public class TechnologyController extends BaseController {

	@Autowired
	private ITechnologyService technologyService;

	@GetMapping("/")
	public ResponseResult<List<Technology>> list() {
		ResponseResult<List<Technology>> rs = new ResponseResult<>(SUCCESS, technologyService.list());
		return rs;
	}

	@PostMapping("/")
	public ResponseResult<List<Technology>> add(@RequestParam("name") String name, 
			@RequestParam("type") String type,@RequestParam("aliases") String[] aliases) {
		technologyService.add(name,type,aliases);
		ResponseResult<List<Technology>> rs = new ResponseResult<>(SUCCESS);
		return rs;
	}

}
