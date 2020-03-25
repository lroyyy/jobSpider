package com.getfei.jobSpider.service.impl;

import org.springframework.stereotype.Service;

import com.getfei.jobSpider.entity.JobBoard;
import com.getfei.jobSpider.service.IJobBoardService;

@Service
public class JobBoardServiceImpl extends MongoBaseServiceImpl<JobBoard> implements IJobBoardService{

}
