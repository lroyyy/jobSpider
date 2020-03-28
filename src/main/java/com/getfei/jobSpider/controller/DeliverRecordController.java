package com.getfei.jobSpider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getfei.jobSpider.dao.IDeliverRecordDao;
import com.getfei.jobSpider.entity.DeliverRecord;
import com.getfei.jobSpider.entity.JobBoard;
import com.getfei.jobSpider.service.IDeliverRecordService;
import com.getfei.jobSpider.service.impl.MongoBaseServiceImpl;
import com.getfei.jobSpider.util.ResponseResult;

@RestController
@RequestMapping("deliverRecords")
public class DeliverRecordController extends BaseController{

	@Autowired
	private IDeliverRecordService deliverRecordService;
	
	@GetMapping
	public ResponseResult<List<DeliverRecord>> list(){
		ResponseResult<List<DeliverRecord>> rr=new ResponseResult<>();
		List<DeliverRecord> deliverRecords=deliverRecordService.list();
		rr.setState(SUCCESS);
		rr.setData(deliverRecords);
		return rr;
	}
	
	@PostMapping
	public ResponseResult<String> add(@RequestBody DeliverRecord deliverRecord){
		ResponseResult<String> rr=new ResponseResult<>();
		deliverRecordService.save(deliverRecord);
		rr.setState(SUCCESS);
		return rr;
	}
	
}
