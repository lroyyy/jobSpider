package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Position;

public interface IPositionService {
	
	/**爬取并插入MongoDB*/
	public void fetchAndInsert();
	
	public List<Position> list();
	
	public Position getByName(String name);
	
	public Position getByCode(String code);
	
	/**获取所有结构化的结果*/
	public List<Position> listStructured();
	
}
