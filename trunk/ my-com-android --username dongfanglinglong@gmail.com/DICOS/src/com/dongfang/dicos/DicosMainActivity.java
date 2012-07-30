package com.dongfang.dicos;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * 主ACTIVITY
 * 
 * @author dongfang
 * */

public class DicosMainActivity extends TabActivity implements OnCheckedChangeListener {
	public static final String	tag			= "DicosMainActivity";

	/** 底部餐单按钮的group */
	private RadioGroup			radioGroup;
	private TabHost				tabHost;
	public static final String	TAB_ITEM_1	= "kazimeiwei";
	public static final String	TAB_ITEM_2	= "DiscountCoupon";
	public static final String	TAB_ITEM_3	= "StorSearch";
	public static final String	TAB_ITEM_4	= "MyDicos";
	public static final String	TAB_ITEM_5	= "more";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);

		tabHost = this.getTabHost();

		TabSpec tab1 = tabHost.newTabSpec(TAB_ITEM_1);
		TabSpec tab2 = tabHost.newTabSpec(TAB_ITEM_2);
		TabSpec tab3 = tabHost.newTabSpec(TAB_ITEM_3);
		TabSpec tab4 = tabHost.newTabSpec(TAB_ITEM_4);
		TabSpec tab5 = tabHost.newTabSpec(TAB_ITEM_5);

		tab1.setIndicator(TAB_ITEM_1).setContent(new Intent(this, KaZiMeiWeiActivity.class));
		tab2.setIndicator(TAB_ITEM_2).setContent(new Intent(this, DiscountCouponActivity.class));
		tab3.setIndicator(TAB_ITEM_3).setContent(new Intent(this, StoreSearchActivity.class));
		tab4.setIndicator(TAB_ITEM_4).setContent(new Intent(this, MyDicosActivity.class));
		tab5.setIndicator(TAB_ITEM_5).setContent(new Intent(this, MoreActivity.class));

		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		tabHost.addTab(tab4);
		tabHost.addTab(tab5);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// new Thread() {
		// @Override
		// public void run() {
		// // 你要执行的方法
		// // 执行完毕后给handler发送一个空消息
		// MyFunction();
		// }
		// }.start();

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button1:
			tabHost.setCurrentTabByTag(TAB_ITEM_1);
			break;
		case R.id.radio_button2:
			tabHost.setCurrentTabByTag(TAB_ITEM_2);
			break;
		case R.id.radio_button3:
			tabHost.setCurrentTabByTag(TAB_ITEM_3);
			break;
		case R.id.radio_button4:
			tabHost.setCurrentTabByTag(TAB_ITEM_4);
			break;
		case R.id.radio_button5:
			tabHost.setCurrentTabByTag(TAB_ITEM_5);
			break;
		}
	}

}
