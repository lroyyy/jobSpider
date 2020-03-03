package com.getfei.jobSpider.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.getfei.jobSpider.dao.IMongoTemplateDao;
import com.getfei.jobSpider.dao.impl.MongoTemplateDaoImpl;

/**
 * Dao层基于MongoTemplateDao的Service实现类的基类<p>
 * 提供对应Dao层的CRUD操作的基本服务
 * @author lroy
 *
 * 已默认实现方法：插入、查询所有记录、清空
 */
public class MongoBaseServiceImpl<T> extends CommonServiceImpl {
	
	@Autowired
	protected IMongoTemplateDao<T> dao;
	
	/**插入*/
	public void save(T entity) {
		dao.insert(entity);
	}
	
	/**查询所有记录*/
	public List<T> list(){
		return dao.findAll();
	}
	
	/**清空*/
	public void clear() {
		dao.truncate();
	}
	
	/**统计*/
	public Integer count() {
		return dao.count();
	}
	
	public void remove(T entity) {
		dao.delete(entity);
	}

}
