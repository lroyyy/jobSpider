package com.getfei.jobSpider.service;

import java.util.List;
import java.util.Map;

import com.getfei.jobSpider.entity.DeliverRecord;

public interface IDeliverRecordService {
	
	/**根据id查询*/
	DeliverRecord getById(String id);
	
	List<DeliverRecord> getByMultipleCriteria(Map<String,Object> criteriaMap);
	
	/**查询所有*/
	List<DeliverRecord> list();
	
	/**写入*/
	void save(DeliverRecord deliverRecord);
	
	/**清空*/
	void clear();
	
	/**删除*/
	void remove(DeliverRecord deliverRecord);
	
	/**批量删除*/
	void batchRemove(List<DeliverRecord> deliverRecords);
	
	/**修改*/
	boolean modify(DeliverRecord deliverRecord);

}
