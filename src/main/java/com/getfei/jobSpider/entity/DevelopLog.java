package com.getfei.jobSpider.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author getfei
 *
 */
@Getter
@Setter
public class DevelopLog extends BaseEntity{
	
	private static final long serialVersionUID = 2994314307822455814L;

	private Date time;
	
	private String content;
	
	private List<String> ul;
	
}
