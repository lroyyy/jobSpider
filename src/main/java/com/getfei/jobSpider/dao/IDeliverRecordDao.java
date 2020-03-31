package com.getfei.jobSpider.dao;

import java.util.List;
import java.util.Map;

import com.getfei.jobSpider.entity.DeliverRecord;

public interface IDeliverRecordDao {

	void insert(DeliverRecord deliverRecord);

	List<DeliverRecord> findByMultipleCriteria(Map<String,Object> criteriaMap);
	
	DeliverRecord findById(String id);
	
	List<DeliverRecord> findAll();
	
	void delete(DeliverRecord deliverRecord);
	
	void batchDelete(List<DeliverRecord> deliverRecords);
	
	boolean update(DeliverRecord deliverRecord);
	
}
