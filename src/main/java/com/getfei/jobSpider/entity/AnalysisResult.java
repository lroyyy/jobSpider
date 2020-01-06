package com.getfei.jobSpider.entity;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisResult extends BaseEntity{

	private static final long serialVersionUID = -2205698762127429568L;
	
	private FetchedResult fetchResult;
	private List<EchartsData> technologyCounter;
	private List<EchartsData> technologyTypeCounter;
//	private Map<Technology,Integer> technologyCounter;
//	private Map<TechnologyType,Integer> technologyTypeCounter;
	
}
