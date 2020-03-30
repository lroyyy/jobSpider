package com.getfei.jobSpider.common;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getfei.jobSpider.dao.IPositionDao;
import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Position;
import com.getfei.jobSpider.entity.Technology;

/**
 * 数据中心
 * @author getfei
 *
 */
@Component
public class DataCenter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IPositionDao positionDao;

	private static ITechnologyDao technologyDao;

	private static List<Position> positions;

	private static List<String> technologyTypes;

	private static Map<String, Technology> technologyMapping;
	
	@Autowired
	public void setTechnologyDao(ITechnologyDao technologyDao) {
		DataCenter.technologyDao = technologyDao;
	}

	@PostConstruct
	public void init() {
		//服务器刚启动，先查一次库，数据载入DataCenter
		try {
			positions = new CopyOnWriteArrayList<>(positionDao.findAll());
		} catch (Exception e) {
			logger.error("初始化postions失败。");
		}
		logger.info("初始化postions成功，个数为" + positions.size());
		//
//		try {
//			technologies=new CopyOnWriteArrayList<>(technologyDao.findAll());
//		} catch (Exception e) {
//			logger.error("初始化postions失败。");
//		}
//		logger.info("初始化technologies成功，个数为"+technologies.size());
		//
		try {
			technologyTypes = new CopyOnWriteArrayList<>(technologyDao.findAllType());
		} catch (Exception e) {
			logger.error("初始化technologyTypes失败。");
		}
		logger.info("初始化technologyTypes成功，个数为" + technologyTypes.size());
		//
		try {
			technologyMapping = getTechnologiesFromMongoDB();
		} catch (Exception e) {
			logger.error("初始化technologyMapping失败。");
		}
		logger.info("初始化technologyMapping成功，个数为" + technologyMapping.size());
	}

	public static List<Position> getPositions() {
		return positions;
	}

	public static Map<String, Technology> getTechnologyMapping() {
		return technologyMapping;
	}
	
	public static List<String> getTechnologyTypes(){
		return technologyTypes;
	}
	
	/** 构造自MongoDB */
	private static Map<String, Technology> getTechnologiesFromMongoDB() {
		List<Technology> technologies = new CopyOnWriteArrayList<>(technologyDao.findAll());
		Map<String, Technology> map = technologies.stream()
				.collect(Collectors.toMap(Technology::getName, technology -> technology));
		return map;
	}

}
