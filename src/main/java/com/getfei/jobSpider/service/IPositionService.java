package com.getfei.jobSpider.service;

import java.util.List;

import com.getfei.jobSpider.entity.Position;

public interface IPositionService {
	
	public void fetchAndInsert();
	
	public List<Position> list();
	
	public Position getByName(String positionName);
	
}
