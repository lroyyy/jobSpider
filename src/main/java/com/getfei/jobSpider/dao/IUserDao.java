package com.getfei.jobSpider.dao;

import com.getfei.jobSpider.entity.User;

public interface IUserDao extends IMongoTemplateDao<User>{

	/**根据用户名查询*/
	User findByName(String name);
	
}
