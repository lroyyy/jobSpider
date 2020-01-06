package com.getfei.jobSpider.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Counter <T> extends BaseEntity{

	private static final long serialVersionUID = -7365387043005616733L;
	private T obj;
	private Integer count;
	
	public Counter() {
		count=0;
	}
	
	public Counter(T obj) {
		this.obj=obj;
		count=0;
	}
	
	public void increaseCount() {
		count++;
	}
	
}
