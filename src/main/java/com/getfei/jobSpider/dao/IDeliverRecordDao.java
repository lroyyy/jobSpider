package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.DeliverRecord;

public interface IDeliverRecordDao {

	void insert(DeliverRecord deliverRecord);

	List<DeliverRecord> findAll();
	
}
