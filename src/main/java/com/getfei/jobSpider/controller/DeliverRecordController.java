package com.getfei.jobSpider.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
public class DeliverRecordController extends BaseController {

	@Autowired
	private IDeliverRecordService deliverRecordService;

	@GetMapping
	public ResponseResult<List<DeliverRecord>> list(@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "jobBoard", required = false) String jobBoard,
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "companyShorthand", required = false) String companyShorthand,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		ResponseResult<List<DeliverRecord>> rr = new ResponseResult<>();
		List<DeliverRecord> deliverRecords = null;
		if(status==null&&jobBoard==null&&companyName==null&&companyShorthand==null&&address==null) {
			deliverRecords=deliverRecordService.list();
		}else {
			Map<String,Object> criteriaMap=new HashMap<>();
			if(status!=null) {
				criteriaMap.put("status", status);
			}
			if(jobBoard!=null) {
				criteriaMap.put("jobBoard", jobBoard);
			}
			if(companyName!=null) {
				criteriaMap.put("companyName", companyName);
			}
			if(companyShorthand!=null) {
				criteriaMap.put("companyShorthand", companyShorthand);
			}
			if(address!=null) {
				criteriaMap.put("address", address);
			}
			deliverRecords=deliverRecordService.getByMultipleCriteria(criteriaMap);
		}
		rr.setState(SUCCESS);
		rr.setData(deliverRecords);
		return rr;
	}

	@GetMapping("/{id}")
	public ResponseResult<DeliverRecord> get(@PathVariable String id) {
		ResponseResult<DeliverRecord> rr = new ResponseResult<>();
		DeliverRecord deliverRecord = deliverRecordService.getById(id);
		rr.setState(SUCCESS);
		rr.setData(deliverRecord);
		return rr;
	}

	@PostMapping
	public ResponseResult<String> add(@RequestBody DeliverRecord deliverRecord) {
		ResponseResult<String> rr = new ResponseResult<>();
		deliverRecordService.save(deliverRecord);
		rr.setState(SUCCESS);
		return rr;
	}

	@DeleteMapping
	public ResponseResult<String> delete(@RequestBody DeliverRecord deliverRecord) {
		ResponseResult<String> rr = new ResponseResult<>();
		deliverRecordService.remove(deliverRecord);
		rr.setState(SUCCESS);
		return rr;
	}

	@DeleteMapping("batch")
	public ResponseResult<String> batchDelete(@RequestBody List<DeliverRecord> deliverRecords) {
		ResponseResult<String> rr = new ResponseResult<>();
		deliverRecordService.batchRemove(deliverRecords);
		rr.setState(SUCCESS);
		return rr;
	}

	@PatchMapping
	public ResponseResult<String> modify(@RequestBody DeliverRecord deliverRecord) {
		ResponseResult<String> rr = new ResponseResult<>();
		int state = deliverRecordService.modify(deliverRecord) ? SUCCESS : ERROR;
		rr.setState(state);
		return rr;
	}

}
