package com.getfei.jobSpider.util;

import lombok.Data;

@Data
public class ResponseResult <T>{
	private Integer state;
	private T data;
	private Integer dataCount;
	private String message;
	public ResponseResult() {
		super();
	}
	public ResponseResult(Integer state) {
		super();
		this.state=state;
	}
	public ResponseResult(Integer state,T data) {
		super();
		this.state=state;
		this.data=data;
	}
}
