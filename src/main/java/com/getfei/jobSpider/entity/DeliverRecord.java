package com.getfei.jobSpider.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 投递记录
 * @author getfei
 *
 */
@Getter
@Setter
public class DeliverRecord extends BaseEntity{

	private static final long serialVersionUID = 1150653755441523833L;

	private Date date;
	
	private String jobBoard;
	
	private String companyName;
	
	private String companyShorthand;
	
	private String address;
	
	private Double salary;
	
	private String status;
	
	private String url;
	
}
