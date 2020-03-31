package com.getfei.jobSpider.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IDeliverRecordDao;
import com.getfei.jobSpider.dao.impl.MongoTemplateDaoImpl;
import com.getfei.jobSpider.entity.DeliverRecord;
import com.getfei.jobSpider.service.IDeliverRecordService;

@Service
public class DeliverRecordServiceImpl extends MongoBaseServiceImpl<DeliverRecord> implements IDeliverRecordService{

	@Autowired
	private IDeliverRecordDao deliverRecordDao;
	
	@Override
	public List<DeliverRecord> getByMultipleCriteria(Map<String, Object> criteriaMap) {
		return deliverRecordDao.findByMultipleCriteria(criteriaMap);
	}
	
	@Override
	public boolean modify(DeliverRecord deliverRecord) {
		return deliverRecordDao.update(deliverRecord);
	}

}
