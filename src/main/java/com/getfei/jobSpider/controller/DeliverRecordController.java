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
import com.getfei.jobSpider.util.Util;

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
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		List<DeliverRecord> deliverRecords = null;
		logger.info("status=" + status + ",jobBoard=" + jobBoard + ",companyName=" + companyName+",remark=" + remark);
		if (!Util.isValuable(status) && !Util.isValuable(jobBoard) && !Util.isValuable(companyName)
				&& !Util.isValuable(companyShorthand) && !Util.isValuable(address)&&!Util.isValuable(remark)) {//无筛选条件
			deliverRecords = deliverRecordService.list();
		} else {//复合条件
			Map<String, Object> criteriaMap = new HashMap<>();
			if (Util.isValuable(status)) {
				criteriaMap.put("status", status);
			}
			if (Util.isValuable(jobBoard)) {
				criteriaMap.put("jobBoard", jobBoard);
			}
			if (Util.isValuable(companyName)) {
				criteriaMap.put("companyName", companyName);
			}
			if (Util.isValuable(companyShorthand)) {
				criteriaMap.put("companyShorthand", companyShorthand);
			}
			if (Util.isValuable(address)) {
				criteriaMap.put("address", address);
			}
			if (Util.isValuable(remark)) {
				criteriaMap.put("remark", remark);
			}
			deliverRecords = deliverRecordService.getByMultipleCriteria(criteriaMap);
		}
		// 分页
		int totalCount = deliverRecords.size();
		if (page != null && limit != null) {
			int fromIndex = (page - 1) * limit;
//			logger.info("totalCount=" + totalCount + ",fromIndex=" + (fromIndex));
			int toIndex = fromIndex + limit < totalCount ? fromIndex + limit : totalCount;
			deliverRecords = deliverRecords.subList(fromIndex, toIndex);
		}
		ResponseResult<List<DeliverRecord>> rr = new ResponseResult<>(SUCCESS,deliverRecords);
		rr.setDataCount(totalCount);
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
		logger.info("entity="+deliverRecord);
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
