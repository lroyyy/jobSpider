package com.getfei.jobSpider.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	/**录入日期*/
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	/**状态*/
	private String status;
	/**网址*/
	private String url;
	/**招聘平台*/
	private String jobBoard;
	/**公司名称*/
	private String companyName;
	/**公司简称*/
	private String companyShorthand;
	/**地址*/
	private String address;
	/**薪资*/
	private Double salary;
	
}
