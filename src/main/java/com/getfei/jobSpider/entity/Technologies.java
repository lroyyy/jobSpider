package com.getfei.jobSpider.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.ITechnologyDao;

@Component
public class Technologies {

	private static Logger logger = LoggerFactory.getLogger(Technologies.class);
	public static Technologies technologies;
	public static Map<String, Technology> technologyMapping;
	private static List<String> technologyTypes;

	private static ITechnologyDao technologyDao;
	
	@Autowired
	public void setTechnologyDao(ITechnologyDao technologyDao) {
		Technologies.technologyDao=technologyDao;
	}

	@PostConstruct
	public static void init() {
		technologyMapping = getTechnologiesFromMongoDB();
		technologyTypes=getTechnologyTypesFromMongoDB();
		logger.info("technologyMapping初始化成功。");
		logger.info("technologyTypes初始化成功。");
	}

	/**构造自MongoDB*/
	public static Map<String, Technology> getTechnologiesFromMongoDB() {
		List<Technology> list = technologyDao.findAll();
		Map<String, Technology> map = list.stream().collect(Collectors.toMap(Technology::getName, technology -> technology));
		return map;
	}
	
	public static List<String> getTechnologyTypesFromMongoDB(){
		return technologyDao.findAllType();
	}

	public static List<String> getTechnologyTypes() {
		return technologyTypes;
	}

}
