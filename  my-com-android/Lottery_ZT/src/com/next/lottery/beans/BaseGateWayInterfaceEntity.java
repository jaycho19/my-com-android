package com.next.lottery.beans;

/**
 * 门户接口基本类型
 * 
 */
public class BaseGateWayInterfaceEntity<T> {

	int		code;
	String	msg;
	T		info;	// 活动对象

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

}
