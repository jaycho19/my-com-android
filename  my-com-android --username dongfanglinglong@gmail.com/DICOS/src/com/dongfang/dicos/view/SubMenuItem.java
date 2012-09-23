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
	private String	id;

	public void init(String name, String id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

}
