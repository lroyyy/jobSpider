package com.getfei.jobSpider.entity;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 预览结果
 * @author lroy
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PreviewResult extends BaseEntity{

	private static final long serialVersionUID = 1557061164157663080L;
	
	/**关键字*/
	private String keyword;
	
	/**位置*/
	private String position;
	
	/***/
	private AnalysisResult analysisResult;
	

}
