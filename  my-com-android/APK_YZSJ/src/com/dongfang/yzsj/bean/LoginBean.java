package com.dongfang.yzsj.bean;

/**
 * 登陆返回信息
 * 
 * @author dongfang
 * 
 */
public class LoginBean {
	private boolean success = false;
	private String token;

	// private String msg;
	// private String error;
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
