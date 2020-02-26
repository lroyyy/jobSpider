package com.getfei.jobSpider.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.getfei.jobSpider.util.data.EchartsData;

import lombok.Getter;
import lombok.Setter;

/**
 * 分析的结果
 * 
 * @author lroy
 * @see FetchedResult
 *
 */
@Getter
@Setter
public class AnalysisResult extends BaseEntity{

	private static final long serialVersionUID = -2205698762127429568L;
	
	@Id
	private String id;
	
	/**关键字*/
	private String keyword;
	
	/**位置*/
	private String position;
	
	/**分析日期*/
	private Date date;
	
	/**总页数*/
	private int totalPage;
	
	/**是否是全新的*/
	@Transient
	private boolean ifNew=true;
	
	private List<EchartsData> technologyCounter;
	private List<EchartsData> technologyTypeCounter;
	
}
