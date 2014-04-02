package com.next.lottery.beans;

public class SKUItem {
	public SKUItem(String name) {
		id = "";
		this.name = name;
	}

	public SKUItem(String id, String name) {
		this.id = id;
		this.name = name;
	}

	private String id;   // 编号
	private String name; // 编号说明，不如 蓝色，M号等

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name;
	}

}
