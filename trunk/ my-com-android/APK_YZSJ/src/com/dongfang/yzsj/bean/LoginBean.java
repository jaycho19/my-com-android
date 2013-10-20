package com.dongfang.yzsj.bean;

/**
 * 登陆返回信息
 * 
 * @author dongfang
 * 
 */
public class LoginBean {
	private boolean result = true;// token是否有效，默认有效
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

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("result  = ").append(result).append("\n");
		sb.append("success = ").append(success).append("\n");
		sb.append("token   = ").append(token);
		return super.toString();
	}

}
