package com.getfei.jobSpider.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IAnalysisResultDao;
import com.getfei.jobSpider.entity.AnalysisResult;
import com.getfei.jobSpider.entity.Technology;

@Component
public class AnalysisResultDaoImpl implements IAnalysisResultDao{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	private String collectionName="analysisResult";
	
	@Override
	public void save(AnalysisResult analysisResult) {
		mongoTemplate.save(analysisResult,collectionName);
	}

	@Override
	public AnalysisResult findByKeywordAndPosition(String keyword, String position) {
		Criteria criatira = new Criteria();
		Criteria keywordCriatira=Criteria.where("keyword").is(keyword);
		Criteria positionCriatira=Criteria.where("position").is(position);
		criatira.andOperator(keywordCriatira,positionCriatira);
		return mongoTemplate.findOne(Query.query(criatira),AnalysisResult.class, collectionName);
	}

	@Override
	public void delete(AnalysisResult analysisResult) {
		mongoTemplate.remove(analysisResult,collectionName);
	}

}
