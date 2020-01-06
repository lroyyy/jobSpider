package com.getfei.jobSpider.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EchartsData extends BaseEntity{

	private static final long serialVersionUID = -1532829381884829563L;

	private String name;
	private String value;
	
}
