package com.getfei.jobSpider.util;

/**
 * 操作Mongo返回的结果
 * 
 * @author Administrator
 *
 */
public class MongoResult {
	
	public static final String NAME_DUPLICATE_NAME="新名称与已有名称冲突";
	public static final String NAME_DUPLICATE_ALIAS="新名称与已有别名冲突";
	public static final String ALIAS_DUPLICATE_NAME="新别名与已有名称冲突";
	public static final String ALIAS_DUPLICATE_ALIAS="新别名与已有别名冲突";
	
	/**操作是否成功（默认成功）*/
	private boolean success=true;
	
	/**信息*/
	private StringBuilder message;
	
	public MongoResult() {
		message=new StringBuilder();
	}
	
	public void appendMessage(String str) {
		message.append(str);
	}
	
	public void setMessage(String message) {
		this.message=new StringBuilder(message);
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
