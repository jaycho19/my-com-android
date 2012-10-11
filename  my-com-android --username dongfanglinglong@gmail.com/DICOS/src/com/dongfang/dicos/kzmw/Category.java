package com.dongfang.dicos.kzmw;

import java.util.ArrayList;
import java.util.List;

/**
 * ≤Àµ•∑÷¿‡
 * 
 * @author dongfang
 * 
 */
public class Category {
	private List<KeyValue>	menulist;
	private List<String>	imgUrls;

	public Category() {
		imgUrls = new ArrayList<String>();
		menulist = new ArrayList<KeyValue>();
	}

	public void add2MenuList(String key, String name, String focus_img, String blur_img) {
		menulist.add(new KeyValue(key, name, focus_img, blur_img));
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

	public void setImageUrls(List<String> list) {
		imgUrls = list;
	}

	public void add2ImgUrls(String imgUrl) {
		imgUrls.add(imgUrl);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String s : imgUrls)
			sb.append("imgUrls = ").append(s).append("\n");
		for (KeyValue kv : menulist)
			sb.append("menulist keyvalue = ").append(kv.toString()).append("\n");
		return sb.toString();
	}

}
