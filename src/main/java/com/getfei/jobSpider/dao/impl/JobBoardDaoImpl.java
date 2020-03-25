package com.getfei.jobSpider.dao.impl;

import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IJobBoardDao;
import com.getfei.jobSpider.entity.JobBoard;

@Component
public class JobBoardDaoImpl extends MongoTemplateDaoImpl<JobBoard> implements IJobBoardDao{

}
