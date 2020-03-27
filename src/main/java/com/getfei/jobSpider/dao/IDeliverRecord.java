package com.getfei.jobSpider.dao;

import java.util.List;

import com.getfei.jobSpider.entity.DeliverRecord;

public interface IDeliverRecord {

	void insert(DeliverRecord deliverRecord);

	List<DeliverRecord> findAll();
	
}
