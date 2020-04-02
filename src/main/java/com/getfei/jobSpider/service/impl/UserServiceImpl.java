package com.getfei.jobSpider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.IUserDao;
import com.getfei.jobSpider.entity.User;
import com.getfei.jobSpider.service.IUserService;

@Service
public class UserServiceImpl extends MongoBaseServiceImpl<User> implements IUserService{

	@Autowired
	private IUserDao userDao;
	
	@Override
	public User getByName(String name) {
		return userDao.findByName(name);
	}

}
