package com.getfei.jobSpider.service.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getfei.jobSpider.dao.ITechnologyDao;
import com.getfei.jobSpider.entity.Technologies;
import com.getfei.jobSpider.entity.Technology;
import com.getfei.jobSpider.service.ITechnologyService;
import com.getfei.jobSpider.util.MongoResult;

@Service
public class TechnologyServiceImpl implements ITechnologyService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ITechnologyDao technologyDao;

	@Override
	public List<Technology> list() {
		List<Technology> technologies=technologyDao.findAll();
		//排序，先看type后看name
		technologies=technologies.stream().sorted(
				Comparator.comparing(Technology::getType).thenComparing(Technology::getName)).collect(Collectors.toList());
		return technologies;
	}

	@Override
	public List<String> ListType() {
		List<String> types=Technologies.getTechnologyTypes();
		if(types==null) {
			logger.warn("获取technologyTypes时，找不到technologies.technologyTypes，去库里找。");
			types = technologyDao.findAllType();
		}
		return types;
	}

	@Override
	public MongoResult add(String name, String type, String[] aliases) {
		MongoResult mongoResult = new MongoResult();
		logger.info("preAdd:name=" + name + ",type=" + type + ",aliases=" + Arrays.toString(aliases));
		// 新增技术的名称和已有技术的名称相同
		List<Technology> technologies = technologyDao.findByName(name);
		if (!technologies.isEmpty()) {
			mongoResult.appendMessage(MongoResult.NAME_DUPLICATE_NAME + ": " + technologies+"\n");
			mongoResult.setSuccess(false);
		}
		// 新增技术的名称和已有技术的某个别名相同
		technologies = technologyDao.findByAlias(name);
		if (!technologies.isEmpty()) {
			mongoResult.appendMessage(MongoResult.NAME_DUPLICATE_ALIAS + ": " + technologies+"\n");
			mongoResult.setSuccess(false);
		}
		// 新增技术的别名和已有技术的名称相同
		if (aliases != null) {
			for (int i = 0, len = aliases.length; i < len; i++) {
				technologies = technologyDao.findByName(aliases[i]);
				if (!technologies.isEmpty()) {
					mongoResult.appendMessage(MongoResult.ALIAS_DUPLICATE_NAME + ": " + technologies+"\n");
					mongoResult.setSuccess(false);
				}
			}
			// 新增技术的别名和已有技术的别名相同
			for (int i = 0, len = aliases.length; i < len; i++) {
				technologies = technologyDao.findByAlias(aliases[i]);
				if (!technologies.isEmpty()) {
					mongoResult.appendMessage(MongoResult.ALIAS_DUPLICATE_ALIAS + ": " + technologies+"\n");
					mongoResult.setSuccess(false);
				}
			}
		}
		// 插入数据库
		if (mongoResult.isSuccess()) {
			Technology newTechnology = new Technology(name, type, aliases == null ? new String[0] : aliases);
			technologyDao.insert(newTechnology);
			logger.info("成功新增："+newTechnology);
			Technologies.init();
		}
		return mongoResult;
	}

	@Override
	public List<Technology> getByType(String type) {
		List<Technology> technologies = technologyDao.findByType(type);
		return technologies;
	}

	@Override
	public List<Technology> getByNameLike(String name) {
		List<Technology> technologies = technologyDao.findByNameLike(name);
		return technologies;
	}

	@Override
	public List<Technology> getByTypeAndNameLikeAndAliasLike(String type, String name, String alias) {
		List<Technology> technologies = technologyDao.findByTypeAndNameLikeAndAliasLike(type, name, alias);
		return technologies;
	}

	@Override
	public MongoResult addAlias(String name, String type, String alias) {
		MongoResult mongoResult = new MongoResult();
		//检查待新增的别名是否与已有技术的名称相同
		List<Technology> technologies=technologyDao.findByName(alias);
		if(!technologies.isEmpty()) {
			mongoResult.appendMessage(MongoResult.ALIAS_DUPLICATE_NAME + ": " + technologies+"\n");
			mongoResult.setSuccess(false);
		}
		//检查待新增的别名是否与已有别名的名称相同
		technologies = technologyDao.findByAlias(alias);
		if (!technologies.isEmpty()) {
			mongoResult.appendMessage(MongoResult.ALIAS_DUPLICATE_ALIAS + ": " + technologies+"\n");
			mongoResult.setSuccess(false);
		}
		//执行新增
		if (mongoResult.isSuccess()) {
			boolean success=technologyDao.insertAlias(type, name, alias);
			mongoResult.setSuccess(success);
		}
		return mongoResult;
	}

	@Override
	public long count() {
		return technologyDao.count();
	}

}
