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
	public boolean add(String name, String type, String[] aliases) {
		Technology technology=new Technology(name, type,aliases);
		technologyDao.save(technology);
		return false;
	}

}
