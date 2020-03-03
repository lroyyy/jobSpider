package com.getfei.jobSpider.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IAnalysisResultDao;
import com.getfei.jobSpider.entity.AnalysisResult;

@Component
public class AnalysisResultDaoImpl extends MongoTemplateDaoImpl<AnalysisResult> implements IAnalysisResultDao{
	
	@Override
	public AnalysisResult findByKeywordAndPosition(String keyword, String position) {
		Criteria criatira = new Criteria();
		Criteria keywordCriatira=Criteria.where("keyword").is(keyword);
		Criteria positionCriatira=Criteria.where("position").is(position);
		criatira.andOperator(keywordCriatira,positionCriatira);
		return mongoTemplate.findOne(Query.query(criatira),AnalysisResult.class, getCollectionName());
	}

}
