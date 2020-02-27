package com.getfei.jobSpider.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志记录
 * @author getfei
 *
 */
@Getter
@Setter
public class Log extends BaseEntity{

	private static final long serialVersionUID = 8430889804777983387L;

	/**日期*/
	private Date date;
	
	/**ip*/
	private String ip;
	
	/**类型*/
	private String type;
	
	/**关键字*/
	private String keyword;
	
	/**位置 */
	private String position;
	
	public Log(String ip) {
		this.ip=ip;
		date=new Date();
	}
	
}
