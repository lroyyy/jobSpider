package com.getfei.jobSpider.service.impl;

import java.util.Arrays;
import java.util.List;

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
		return technologyDao.findAll();
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
		MongoResult mr = new MongoResult();
		logger.info("preAdd:name=" + name + ",type=" + type + ",aliases=" + Arrays.toString(aliases));
		// 新增技术的名称和已有技术的名称相同
		List<Technology> technologies = technologyDao.findByName(name);
		if (!technologies.isEmpty()) {
			mr.appendMessage(MongoResult.NAME_DUPLICATE_NAME + ": " + technologies+"\n");
			mr.setSuccess(false);
		}
		// 新增技术的名称和已有技术的某个别名相同
		technologies = technologyDao.findByAlias(name);
		if (!technologies.isEmpty()) {
			mr.appendMessage(MongoResult.NAME_DUPLICATE_ALIAS + ": " + technologies+"\n");
			mr.setSuccess(false);
		}
		// 新增技术的别名和已有技术的名称相同
		if (aliases != null) {
			for (int i = 0, len = aliases.length; i < len; i++) {
				technologies = technologyDao.findByName(aliases[i]);
				if (!technologies.isEmpty()) {
					mr.appendMessage(MongoResult.ALIAS_DUPLICATE_NAME + ": " + technologies);
					mr.setSuccess(false);
				}
			}
			// 新增技术的别名和已有技术的别名相同
			for (int i = 0, len = aliases.length; i < len; i++) {
				technologies = technologyDao.findByAlias(aliases[i]);
				if (!technologies.isEmpty()) {
					mr.appendMessage(MongoResult.ALIAS_DUPLICATE_ALIAS + ": " + technologies);
					mr.setSuccess(false);
				}
			}
		}
		// 插入数据库
		if (mr.isSuccess()) {
			Technology newTechnology = new Technology(name, type, aliases == null ? new String[0] : aliases);
			technologyDao.insert(newTechnology);
			logger.info("成功新增："+newTechnology);
			Technologies.init();
		}
		return mr;
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

}
