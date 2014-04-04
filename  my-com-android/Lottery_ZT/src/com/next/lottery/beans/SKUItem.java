package com.next.lottery.beans;

public class SKUItem {
	public SKUItem(String name) {
		vid = "";
		this.vname = name;
	}

	public SKUItem(String id, String name) {
		this.vid = id;
		this.vname = name;
	}

	private String vid;   // 编号
	private String vname; // 编号说明，不如 蓝色，M号等

	public String getId() {
		return vid;
	}

	public void setId(String id) {
		this.vid = id;
	}

	public String getName() {
		return vname;
	}

	public void setName(String name) {
		name = name;
	}

}
