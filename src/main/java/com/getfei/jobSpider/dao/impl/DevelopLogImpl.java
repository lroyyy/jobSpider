package com.getfei.jobSpider.dao.impl;

import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IDevelopLog;
import com.getfei.jobSpider.entity.DevelopLog;

@Component
public class DevelopLogImpl extends MongoTemplateDaoImpl<DevelopLog> implements IDevelopLog{

}
