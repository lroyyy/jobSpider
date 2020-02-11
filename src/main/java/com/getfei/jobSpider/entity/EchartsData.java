package com.getfei.jobSpider.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 前端JS框架Echarts图表需要的数据格式
 * @author lroy
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class EchartsData extends BaseEntity{

	private static final long serialVersionUID = -1532829381884829563L;

	private String name;
	private String value;
	
}
