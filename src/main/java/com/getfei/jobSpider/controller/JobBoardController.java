package com.getfei.jobSpider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.JobBoard;
import com.getfei.jobSpider.service.IJobBoardService;
import com.getfei.jobSpider.service.impl.JobBoardServiceImpl;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("jobBoards")
public class JobBoardController extends MongoBaseController<JobBoard,JobBoardServiceImpl>{

	@Autowired
	private IJobBoardService jobBoardService;
	
//	@GetMapping
//	public ResponseResult<List<JobBoard>> list(){
//		ResponseResult<List<JobBoard>> rr=new ResponseResult<>();
//		List<JobBoard> jobBoards=jobBoardService.list();
//		rr.setData(jobBoards);
//		rr.setState(SUCCESS);
//		return rr;
//	}
	
	@PostMapping
	public ResponseResult<String> add(@RequestParam(name="name") String name){
		ResponseResult<String> rr=new ResponseResult<>();
		JobBoard jobBoard=new JobBoard();
		jobBoard.setName(name);
		jobBoardService.save(jobBoard);
		rr.setState(SUCCESS);
		return rr;
	}
	
	/**清空所有*/
//	@DeleteMapping()
//	public ResponseResult<String> clear(){
//		jobBoardService.clear();
//		ResponseResult<String> rr=new ResponseResult<String>(SUCCESS);
//		return rr;
//	}
}
