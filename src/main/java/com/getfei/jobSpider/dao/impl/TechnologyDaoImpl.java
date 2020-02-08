package com.getfei.jobSpider.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.entity.Technology;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;

@Component
public class TechnologyDaoImpl implements ITechnologyDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	private String collectionName="technology";
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Technology> findAll() {
		return mongoTemplate.findAll(Technology.class, collectionName);
	}

	@Override
	public void insert(Technology technology) throws DuplicateKeyException {
		mongoTemplate.insert(technology, collectionName);
	}

	@Override
	public List<String> findAllType() {
		Query query=new Query();
		query.fields().include("type");
		List<Technology> technologies=mongoTemplate.find(query,Technology.class,collectionName);
		//只取type
		List<String> types=new ArrayList<>();
		technologies.forEach(t->{
			types.add(t.getType());
		});
		//去重
		List<String> distinctTypes=types.stream().distinct().collect(Collectors.toList());
		return distinctTypes;
	}
	
	@Override
	public List<Technology> findByType(String type) {
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("type").is(type)),
				Technology.class, collectionName);
		return technologies;
	}

	@Override
	public List<Technology> findByName(String name) {
		//模糊匹配
		Pattern pattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("name").regex(pattern)),
				Technology.class, collectionName);
		return technologies;
	}

	@Override
	public List<Technology> findByTypeAndNameAndAlias(String type, String name,String alias) {
		logger.info("type="+type+",name="+name+",alias="+alias);
		Criteria criatira = new Criteria();
		//筛选type
		Criteria typeCriatira="".equals(type)?new Criteria():Criteria.where("type").is(type);		
		//筛选name
		Pattern namePattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
		Criteria nameCriatira="".equals(name)?new Criteria():Criteria.where("name").regex(namePattern);
		//筛选alias
		Pattern aliasPattern = Pattern.compile("^.*"+alias+".*$", Pattern.CASE_INSENSITIVE);
		Criteria aliasCriatira="".equals(alias)?new Criteria():Criteria.where("aliases").regex(aliasPattern);
		//并联多条件
		criatira.andOperator(typeCriatira,nameCriatira,aliasCriatira);
		List<Technology> technologies = mongoTemplate.find(Query.query(criatira),Technology.class, collectionName);
		return technologies;
	}

}
