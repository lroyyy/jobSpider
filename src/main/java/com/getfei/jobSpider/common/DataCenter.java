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

	private static final Logger LOGGER = LoggerFactory.getLogger(DataCenter.class);

	private static IPositionDao positionDao;
	
	@Autowired
	public void setPositionDao(IPositionDao positionDao) {
		DataCenter.positionDao = positionDao;
	}

	private static ITechnologyDao technologyDao;
	
	@Autowired
	public void setTechnologyDao(ITechnologyDao technologyDao) {
		DataCenter.technologyDao = technologyDao;
	}

	private static List<Position> positions;

	private static List<String> technologyTypes;

	private static Map<String, Technology> technologyMapping;
	
	private static String[] statuses= {"关注","已发起沟通","已投递","收到面试通知","等待面试结果","收到offer","已被拒绝"};
	
	private static String[] jobBoards= {"智联","Boss直聘","前程无忧","拉勾","58同城"};

	@PostConstruct
	public void init() {
		//服务器刚启动，先查一次库，数据载入DataCenter
		initPositionsFromDB();
		initTechnologyTypesFromDB();
		initTechnologyMappingFromDB();
	}

	public static void initPositionsFromDB() {
		try {
			positions = new CopyOnWriteArrayList<>(positionDao.findAll());
		} catch (Exception e) {
			LOGGER.error("初始化postions失败。");
		}
		LOGGER.info("初始化postions成功，个数为" + positions.size());
	}
	
	public static void initTechnologyTypesFromDB() {
		try {
			technologyTypes = new CopyOnWriteArrayList<>(technologyDao.findAllType());
		} catch (Exception e) {
			LOGGER.error("初始化technologyTypes失败。");
		}
		LOGGER.info("初始化technologyTypes成功，个数为" + technologyTypes.size());
	}
	
	public static void initTechnologyMappingFromDB() {
		try {
			technologyMapping = getTechnologyMappingFromMongoDB();
		} catch (Exception e) {
			LOGGER.error("初始化technologyMapping失败。");
		}
		LOGGER.info("初始化technologyMapping成功，个数为" + technologyMapping.size());
	}
	
	public static List<Position> getPositions() {
		if(positions==null) {
			initPositionsFromDB();
		}
		return positions;
	}

	public static Map<String, Technology> getTechnologyMapping() {
		if(technologyMapping==null) {
			initTechnologyMappingFromDB();
		}
		return technologyMapping;
	}
	
	public static List<String> getTechnologyTypes(){
		if(technologyTypes==null) {
			initTechnologyTypesFromDB();
		}
		return technologyTypes;
	}
	
	/** 构造自MongoDB */
	private static Map<String, Technology> getTechnologyMappingFromMongoDB() {
		List<Technology> technologies = new CopyOnWriteArrayList<>(technologyDao.findAll());
		Map<String, Technology> map = technologies.stream()
				.collect(Collectors.toMap(Technology::getName, technology -> technology));
		return map;
	}

	public static String[] getStatuses() {
		return statuses;
	}

	public static String[] getJobBoards() {
		return jobBoards;
	}

}
