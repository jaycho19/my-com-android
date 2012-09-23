package com.dongfang.dicos.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.dongfang.dicos.R;
import com.dongfang.dicos.kzmw.KeyValue;

public class SubMenuLayout extends LinearLayout {

	private final String			TAG				= "SubMenuLayout";

	private Context					context;
	/** TextView 对象列表 */
	private SubMenuItem[]			tvList;
	/** 显示的文字 */
	private List<KeyValue>			menulist;

	private int						length			= 0;

	private static SubMenuLayout	submenuLayout	= null;

	public static SubMenuLayout getSubMenuLayout(Context context) {
		if (null == submenuLayout)
			submenuLayout = new SubMenuLayout(context);
		return submenuLayout;

	}

	public SubMenuLayout(Context context) {
		super(context);
		this.context = context;
		// initSubMenuLayout();
	}

	public SubMenuLayout(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
		this.context = context;
		// initSubMenuLayout();
	}

	private void initSubMenuLayout() {
		String[] tvListText = getResources().getStringArray(R.array.sub_manu);
		length = tvListText.length;
		tvList = new SubMenuItem[length];

		for (int i = 0; i < length; i++) {
			tvList[i] = (SubMenuItem) LayoutInflater.from(context).inflate(R.layout.textview_sub, null);
			tvList[i].setText(tvListText[i]);
			tvList[i].setid(3 + "");
			this.addView(tvList[i]);

		}
	}

	public void setTVListText(List<KeyValue> list) {
		menulist = list;
		length = list.size();
		refresh();
	}

	public void refresh() {
		removeAllViews();
		tvList = new SubMenuItem[length];

		for (int i = 0; i < length; i++) {
			tvList[i] = (SubMenuItem) LayoutInflater.from(context).inflate(R.layout.textview_sub, null);
			tvList[i].setText(menulist.get(i).value);
			tvList[i].setid(menulist.get(i).key);
			this.addView(tvList[i]);
		}
	}

	public void setOnClickListener(OnClickListener[] clickListener) {
		int l = Math.min(length, clickListener.length);
		for (int i = 0; i < l; i++) {
			tvList[i].setOnClickListener(clickListener[i]);
		}
	}

}