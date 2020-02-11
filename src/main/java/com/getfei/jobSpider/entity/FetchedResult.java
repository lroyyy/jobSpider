package com.getfei.jobSpider.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 爬取的结果
 * 
 * @author lroy
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FetchedResult extends BaseEntity{

	private static final long serialVersionUID = 3765226446691669424L;

	/** 总数 */
	private int total=0;
	
	/** 成功数 */
	private int successCount = 0;
	
	/** 失败数 */
	private int failureCount = 0;
	
	/** 工作集 */
	private List<Job> jobs;
	
	/**关键字*/
	private String keyword;
	
	/**位置*/
	private String position;
	
	public void totalIncrease1() {
		total++;
	}
	
	public void successCountIncrease1() {
		successCount++;
	}

	public void failureCountIncrease1() {
		failureCount++;
	}
	
}
