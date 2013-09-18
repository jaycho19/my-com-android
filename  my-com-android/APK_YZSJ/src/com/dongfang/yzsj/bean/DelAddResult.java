package com.dongfang.yzsj.bean;

import java.util.List;

public class DelAddResult {
	private boolean success = false;
	private List<String> msg;
	private List<String> error;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public void setMsg(List<String> msg) {
		this.msg = msg;
	}

	public String getMsg() {
		if (null != msg && msg.size() > 0)
			return msg.get(0);
		if (null != error && error.size() > 0)
			return error.get(0);
		return "";
	}
}
