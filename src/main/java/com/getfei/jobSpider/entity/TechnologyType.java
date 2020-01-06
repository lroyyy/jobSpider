package com.getfei.jobSpider.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Deprecated
public class TechnologyType extends BaseEntity{
	
	private static final long serialVersionUID = -8657274845775096811L;
	private String name;
//	后端框架,后端技术,数据库,数据库框架,
//	前端技术,Web中间件,消息中间件,工具,概念,其他
	
	public TechnologyType(String name) {
		this.name=name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
