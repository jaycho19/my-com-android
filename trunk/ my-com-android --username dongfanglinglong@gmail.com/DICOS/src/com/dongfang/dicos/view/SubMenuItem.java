package com.dongfang.dicos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SubMenuItem extends TextView {

	public SubMenuItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SubMenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SubMenuItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private String	name;
	private String	imageUrl[];

	public void init(String name, String[] imageUrl, int id) {
		this.name = name;
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the imageUrl
	 */
	public String[] getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String[] imageUrl) {
		this.imageUrl = imageUrl;
	}

}
