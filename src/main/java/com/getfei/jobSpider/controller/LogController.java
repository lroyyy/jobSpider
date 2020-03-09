package com.getfei.jobSpider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.entity.Log;
import com.getfei.jobSpider.service.ILogService;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("logs")
public class LogController extends BaseController{
	
	@Autowired
	private ILogService logService;
	
	@GetMapping()
	public ResponseResult<List<Log>> list(){
		return new ResponseResult<>(SUCCESS,logService.list());
	}
	
	/**新增访问日志*/
	@PostMapping("visitLog")
	public ResponseResult<String> addVisitLog(@RequestParam("ip") String ip){
		ResponseResult<String> rr=new ResponseResult<>();
		try {
			Log log=new Log(ip);
			log.setType("visit");
			logService.save(log);
			rr.setState(SUCCESS);
		} catch (Exception e) {
			rr.setState(ERROR);
			rr.setMessage(e.getMessage());
		}
		return rr;
	}
	
	/**查询访问次数*/
	@GetMapping("visitCount")
	public ResponseResult<Long> getVisitCount(@RequestParam(name="option",required=false) String option){
		ResponseResult<Long> rr=new ResponseResult<>();
		try {
			long count;
			if(option!=null&&"today".equals(option)) {
				count=logService.countTodayByType("visit");
			}else {
				count=logService.countByType("visit");
			}
			rr.setState(SUCCESS);
			rr.setData(Long.valueOf(count));
		} catch (Exception e) {
			rr.setState(ERROR);
			rr.setMessage(e.getMessage());
		}
		return rr;
	}
	
	/**清空所有log*/
	@DeleteMapping()
	public ResponseResult<String> clear(){
		logService.clear();
		ResponseResult<String> rr=new ResponseResult<String>(SUCCESS);
		return rr;
	}
	
}
