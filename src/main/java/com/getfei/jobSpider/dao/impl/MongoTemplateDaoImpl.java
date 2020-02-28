package com.getfei.jobSpider.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.cdi.MongoRepositoryBean;

import com.getfei.jobSpider.entity.Position;

/**
 * mongoTemplate Dao层实现类
 * @author getfei
 *
 * @param <T>
 */
public class MongoTemplateDaoImpl<T> {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	private String collectionName;

	/**
	 * 获取泛型对相应的Class对象
	 * 
	 * @param index
	 * @return
	 * @author 刘朋 <br/>
	 *         date 2018-11-12
	 */
	protected Class<T> getTClass() {
		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
		return (Class) type.getActualTypeArguments()[0];// <T>
	}

	public void setCollectionName(String collectionName) {
		this.collectionName=collectionName;
	}
	
	public String getCollectionName() {
		if(collectionName==null) {//若未collection名未指定，默认实体名首字母改小写，作为collection名
			Class<T> clz=getTClass();
			String className=clz.getSimpleName();
			collectionName=className.substring(0, 1).toLowerCase()+className.substring(1);
			logger.info("getCollectionName="+collectionName);
		}
		return collectionName;
	}
	
	public List<T> findAll() {
		return mongoTemplate.findAll(getTClass(), getCollectionName());
	}
	
	public T findByField(String fieldName,String fieldValue){
		return mongoTemplate.findOne(Query.query(Criteria.where(fieldName).is(fieldValue)), getTClass(), getCollectionName());
	}
	
	public void insert(T entity) {
		mongoTemplate.insert(entity,getCollectionName());
	}

}