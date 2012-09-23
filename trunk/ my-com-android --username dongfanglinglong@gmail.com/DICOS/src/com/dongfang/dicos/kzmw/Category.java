package com.dongfang.dicos.kzmw;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private List<KeyValue>	menulist;
	private List<String>	imgUrls;

	public Category() {
		imgUrls = new ArrayList<String>();
		menulist = new ArrayList<KeyValue>();
	}

	public void add2MenuList(String key, String value) {
		menulist.add(new KeyValue(key, value));
	}

	public List<KeyValue> getMenuList() {
		return menulist;
	}

	public void setList(List<KeyValue> list) {
		this.menulist = list;
	}

	public List<String> getImgUrls() {
		return imgUrls;
	}

	public void add2ImgUrls(String imgUrl) {
		imgUrls.add(imgUrl);
	}



}
