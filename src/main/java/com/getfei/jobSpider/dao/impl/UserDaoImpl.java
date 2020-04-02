package com.getfei.jobSpider.dao.impl;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IUserDao;
import com.getfei.jobSpider.entity.User;

@Component
public class UserDaoImpl extends MongoTemplateDaoImpl<User> implements IUserDao{

	@Override
	public User findByName(String name) {
		return mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), User.class, getCollectionName());
	}

}
