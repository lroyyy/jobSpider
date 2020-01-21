package com.getfei.jobSpider.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Document(collection = "job")
@Data
@EqualsAndHashCode(callSuper=false)
public class Job extends BaseEntity{
	
	private static final long serialVersionUID = -2596475348753784344L;
	@Id
	private int id;
	/**标题*/
    private String title;
    /**公司名称*/
    private String companyName;
    /**公司信息*/
    private String companyMessage;
    /**职位*/
    private String jobTitle;
    /**标签*/
    private String[] tag;
    /**薪资*/
    private String salary;
    /**关键字*/
    private String[] keyWords;
    /**职位描述*/
    private String jobMessage;
    /**位置*/
    private String location;
    /**技术栈*/
    private List<Technology> technologies;
    /**url*/
    private String url;
    
}
