package com.next.lottery.beans;

import java.util.ArrayList;

public class CategoryBean {
	private int code;
	private String msg;
	private ArrayList<CategoryEntity> info = new ArrayList<CategoryEntity>();

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

	public ArrayList<CategoryEntity> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<CategoryEntity> info) {
		this.info = info;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, length = info.size(); i < length; i++) {
			sb.append(i).append("-->").append(info.get(i).toString());
		}
		return sb.toString();
	}
}
