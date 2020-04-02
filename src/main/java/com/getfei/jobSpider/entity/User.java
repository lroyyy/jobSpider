package com.getfei.jobSpider.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 6754252120331361740L;
	
	private String _id;
	private String name;
	private String password;
	private String salt;

}
