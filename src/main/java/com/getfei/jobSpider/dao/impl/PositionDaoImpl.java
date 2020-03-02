package com.getfei.jobSpider.dao.impl;

import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IPositionDao;
import com.getfei.jobSpider.entity.Position;

@Component
public class PositionDaoImpl extends MongoTemplateDaoImpl<Position> implements IPositionDao {

	@Override
	public Position findByName(String name) {
		return findByField("name", name);
	}

	@Override
	public Position findByCode(String code) {
		return findByField("code", code);
	}

}
