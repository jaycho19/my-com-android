package com.next.lottery.beans;

public class SearchBaseBean {
	private int				code;
	private String			msg;
	private SearchGoosBean	info	= new SearchGoosBean();

	public SearchGoosBean getInfo() {
		return info;
	}

	public void setInfo(SearchGoosBean info) {
		this.info = info;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-->").append(info.toString());
		return sb.toString();
	}
}
