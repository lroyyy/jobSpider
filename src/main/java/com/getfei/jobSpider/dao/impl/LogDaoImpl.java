package com.getfei.jobSpider.dao.impl;

import java.util.Date;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ILogDao;
import com.getfei.jobSpider.entity.Log;

@Component
public class LogDaoImpl extends MongoTemplateDaoImpl<Log> implements ILogDao{

	@Override
	public Integer countByType(String type) {
		Long count=mongoTemplate.count(Query.query(Criteria.where("type").is(type)),Log.class, getCollectionName());
		return count.intValue();
	}

	@Override
	public Integer countTodayByType(String type) {
		Criteria criatira = new Criteria();
		Criteria typeCriteria=Criteria.where("type").is(type);
		Criteria dateCriteria=Criteria.where("date").is(new Date());
		criatira.andOperator(typeCriteria,dateCriteria);
		Long count=mongoTemplate.count(Query.query(criatira),Log.class, getCollectionName());
		return count.intValue();
	}

}
