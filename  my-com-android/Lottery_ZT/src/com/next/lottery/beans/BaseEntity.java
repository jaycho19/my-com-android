package com.next.lottery.beans;

/**
 * 门户接口基本类型
 * 
 */
public class BaseEntity<T> {

	int code;
	String msg;
	T info; // 活动对象

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("code = ").append(code).append("\n");
		sb.append("msg  = ").append(msg).append("\n");
		sb.append("info = ").append(info.toString()).append("\n");
		return sb.toString();
	}

}
