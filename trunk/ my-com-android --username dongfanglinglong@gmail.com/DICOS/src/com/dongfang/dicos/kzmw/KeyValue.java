package com.dongfang.dicos.kzmw;

/**
 * 没一个二级菜单元素的信息
 * 
 * @author dongfang
 * 
 */
public class KeyValue {
	public String	key;
	public String	name;
	public String	focus_img;
	public String	blur_img;

	public KeyValue(String key, String name, String focus_img, String blur_img) {
		this.key = key;
		this.name = name;
		this.focus_img = focus_img;
		this.blur_img = blur_img;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("key = ").append(key).append(" | ");
		sb.append("name = ").append(name).append(" | ");
		sb.append("focus_img = ").append(focus_img).append(" | ");
		sb.append("blur_img = ").append(blur_img);

		return sb.toString();
	}

}