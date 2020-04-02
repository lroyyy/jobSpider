package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.User;

public interface IUserService {
	
	User getByName(String name);
	
	List<User> list();
	
	void save(User user);
	
}
