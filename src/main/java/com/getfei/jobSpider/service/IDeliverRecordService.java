package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.DeliverRecord;

public interface IDeliverRecordService {
	
	/**查询所有*/
	List<DeliverRecord> list();
	
	/**写入*/
	void save(DeliverRecord deliverRecord);
	
	/**清空*/
	void clear();
	
	void remove(DeliverRecord deliverRecord);

}
