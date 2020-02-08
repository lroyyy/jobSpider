package com.getfei.jobSpider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.ITechnologyService;

@Service
public class TechnologyServiceImpl implements ITechnologyService{

	@Autowired
	private ITechnologyDao technologyDao;
	
	@Override
	public List<Technology> list() {
		return technologyDao.findAll();
	}

	@Override
	public List<String> ListType() {
		List<String> types=technologyDao.findAllType();
		return types;
	}
	
	@Override
	public boolean add(String name, String type, String[] aliases) {
		Technology technology=new Technology(name, type,aliases);
		technologyDao.insert(technology);
		return false;
	}

	@Override
	public List<Technology> getByType(String type) {
		List<Technology> technologies=technologyDao.findByType(type);
		return technologies;
	}
	
	@Override
	public List<Technology> getByName(String name) {
		List<Technology> technologies=technologyDao.findByName(name);
		return technologies;
	}

	@Override
	public List<Technology> getByTypeAndNameAndAlias(String type,String name,String alias) {
		List<Technology> technologies=technologyDao.findByTypeAndNameAndAlias(type,name,alias);
		return technologies;
	}
	
}
