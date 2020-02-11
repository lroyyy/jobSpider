package com.getfei.jobSpider.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "positions")
@Data
@EqualsAndHashCode(callSuper=false)
public class Position extends BaseEntity{

	private static final long serialVersionUID = -6693631360258569312L;

	private String name;
	
	private String code;
	
	private List<Position> positions=new ArrayList<>();
	
	public Position(String name,String code) {
		this.name=name;
		this.code=code;
	}
	
	public void addPosition(Position position) {
		positions.add(position);
	}
	
}
