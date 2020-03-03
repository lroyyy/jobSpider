package com.getfei.jobSpider.dao;

import java.util.List;

/**
 * Dao层MongoTemplate接口
 * @author getfei
 *
 * @param <T>
 */
public interface IMongoTemplateDao<T> {

	/**插入*/
	public void insert(T entity) ;
	
	/**查询所有记录*/
	public List<T> findAll();
	
	/**清空*/
	public void truncate();
	
	/**统计个数*/
	public Integer count();
	
	/**删除*/
	public void delete(T entity);
	
}
