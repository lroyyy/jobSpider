package com.getfei.jobSpider.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 投递记录
 * @author getfei
 *
 */
@Getter
@Setter
@ToString
public class DeliverRecord extends BaseEntity{

	private static final long serialVersionUID = 1150653755441523833L;

//	@Id
	private String _id;
	/**录入日期*/
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
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
	private String salary;
	/**面试时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private LocalDateTime interviewDateTime;
	/**面试地点*/
	private String interviewAddress;
	/**备注*/
	private String remark;
	
}
