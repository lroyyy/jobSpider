package com.getfei.jobSpider.util;

import lombok.Getter;
import lombok.Setter;

public class MongoResult {
	
	public static final String NAME_DUPLICATE_NAME="新名称与已有名称冲突";
	public static final String NAME_DUPLICATE_ALIAS="新名称与已有别名冲突";
	public static final String ALIAS_DUPLICATE_NAME="新别名与已有名称冲突";
	public static final String ALIAS_DUPLICATE_ALIAS="新别名与已有别名冲突";
	
	private boolean success=true;
	private StringBuilder message;
	
	public MongoResult() {
		message=new StringBuilder();
	}
	
	public void appendMessage(String str) {
		message.append(str);
	}
	
	public String getMessage() {
		return message.toString();
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
