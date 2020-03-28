package com.getfei.jobSpider.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IDeliverRecordDao;
import com.getfei.jobSpider.entity.DeliverRecord;

@Component
public class DeliverRecordDaoImpl extends MongoTemplateDaoImpl<DeliverRecord> implements IDeliverRecordDao{

}
