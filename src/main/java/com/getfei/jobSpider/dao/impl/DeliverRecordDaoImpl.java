package com.getfei.jobSpider.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IDeliverRecordDao;
import com.getfei.jobSpider.entity.DeliverRecord;
import com.mongodb.client.result.DeleteResult;

@Component
public class DeliverRecordDaoImpl extends MongoTemplateDaoImpl<DeliverRecord> implements IDeliverRecordDao{

}
