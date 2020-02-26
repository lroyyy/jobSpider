package com.getfei.jobSpider.common;

import com.getfei.jobSpider.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

public class DataCenter extends BaseEntity{

	private static final long serialVersionUID = 8430889804777983387L;

	private Integer visitCount;
	private Integer technologyCount;
	private Integer analysisCount;
	
}
