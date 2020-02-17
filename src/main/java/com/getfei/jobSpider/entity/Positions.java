package com.getfei.jobSpider.entity;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IPositionDao;

@Component
public class Positions {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static List<Position> list;
	
	@Autowired
	private IPositionDao positionDao;
	
	@PostConstruct
	public void init() {
		try {
			list=positionDao.findAll();
		} catch (Exception e) {
			logger.error("初始化postions失败。");
		}
		logger.info("初始化postions成功，个数为"+list.size());
	}
	
	public static List<Position> getList(){
		return list;
	}
}
