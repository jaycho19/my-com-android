package com.dongfang.yzsj.bean;

import java.util.List;

public class DelAddResult {
	private boolean success = false;
	private List<String> error;
	private String command;
	private String beginTime;
	private String endTime;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

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

	public String getMsg() {
		if (null != error && error.size() > 0)
			return error.get(0);
		return "";
	}
}
