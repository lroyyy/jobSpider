package com.getfei.jobSpider.entity;

import lombok.Data;

@Data
public class TechnologyStack extends BaseEntity{
	
	private static final long serialVersionUID = -5330958093144756130L;

	private String name;
	
	private String[] types;

}
