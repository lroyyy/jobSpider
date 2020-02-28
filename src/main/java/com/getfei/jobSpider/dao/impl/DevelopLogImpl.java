package com.getfei.jobSpider.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IDevelopLog;
import com.getfei.jobSpider.entity.DevelopLog;

@Component
public class DevelopLogImpl extends MongoTemplateDaoImpl<DevelopLog> implements IDevelopLog{

	@Autowired
	public DevelopLogImpl() {
		setCollectionName("developLog");
	}

}
