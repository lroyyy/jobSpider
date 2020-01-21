package com.getfei.jobSpider.entity;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IPositionDao;

//@Component
public class Positions {

	@Autowired
	private IPositionDao positionDao;
	
	@PostConstruct
	public void init() {
		
	}
	
}
