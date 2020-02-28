package com.getfei.jobSpider.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.entity.Technology;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.client.result.UpdateResult;

/**
 * technologyDao的mongoTemplate实现
 * @author getfei
 *
 */
@Component
public class TechnologyDaoImpl extends MongoTemplateDaoImpl<Technology> implements ITechnologyDao {


//	@Override
//	public void insert(Technology technology) throws DuplicateKeyException {
//		mongoTemplate.insert(technology, collectionName);
//	}

	@Override
	public List<String> findAllType() {
		Query query=new Query();
		query.fields().include("type");
		List<Technology> technologies=mongoTemplate.find(query,Technology.class,getCollectionName());
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
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("type").regex("^"+type+"$","i")),
				Technology.class, getCollectionName());
		return technologies;
	}
	
	@Override
	public List<Technology> findByName(String name) {
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("name").regex("^"+name+"$","i")),
				Technology.class, getCollectionName());
		return technologies;
	}

	@Override
	public List<Technology> findByNameLike(String name) {
		//模糊匹配，不区分大小写
		Pattern pattern = Pattern.compile("^.*"+name+".*$", Pattern.CASE_INSENSITIVE);
		List<Technology> technologies = mongoTemplate.find(Query.query(Criteria.where("name").regex(pattern)),
				Technology.class, getCollectionName());
		return technologies;
	}

	@Override
	public List<Technology> findByTypeAndNameLikeAndAliasLike(String type, String name,String alias) {
		logger.info("type="+type+",name="+name+",alias="+alias);
		Criteria criatira = new Criteria();
		//筛选type
		Criteria typeCriatira=type==null||"".equals(type)?new Criteria():Criteria.where("type").is(type);		
		//筛选name
		Criteria nameCriatira=name==null||"".equals(name)?new Criteria():Criteria.where("name").regex("^.*"+name+".*$","i");
		//筛选alias
		Criteria aliasCriatira=alias==null||"".equals(alias)?new Criteria():Criteria.where("aliases").regex("^.*"+alias+".*$","i");
		//并联多条件
		criatira.andOperator(typeCriatira,nameCriatira,aliasCriatira);
		List<Technology> technologies = mongoTemplate.find(Query.query(criatira),Technology.class, getCollectionName());
		return technologies;
	}

	@Override
	public List<Technology> findByTypeAndName(String type, String name) {
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("type").regex(type,"i"),Criteria.where("name").regex(name,"i"));
		List<Technology> technologies = mongoTemplate.find(Query.query(criatira),Technology.class, getCollectionName());
		return technologies;
	}

	@Override
	public List<Technology> findByAlias(String alias) {
		return mongoTemplate.find(Query.query(Criteria.where("aliases").regex("^"+alias+"$","i")),Technology.class, getCollectionName());
	}

	@Override
	public boolean insertAlias(String type,String name,String alias) {
		Criteria criatira = new Criteria();
		Update update=new Update().push("aliases", alias);
		criatira.andOperator(Criteria.where("type").is(type),Criteria.where("name").is(name));
		Query query=new Query(criatira);
		UpdateResult updateResult=mongoTemplate.updateFirst(query,update,getCollectionName());
		return updateResult.wasAcknowledged();
	}

	@Override
	public long count() {
		return findAll().size();
	}

}
