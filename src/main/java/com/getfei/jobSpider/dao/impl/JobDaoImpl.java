package com.getfei.jobSpider.dao.impl;

import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IJobDao;
import com.getfei.jobSpider.entity.Job;

@Component
public class JobDaoImpl extends MongoTemplateDaoImpl<Job> implements IJobDao{

}
