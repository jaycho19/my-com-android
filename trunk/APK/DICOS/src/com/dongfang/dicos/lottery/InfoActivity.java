package com.dongfang.dicos.lottery;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.dongfang.dicos.R;
import com.dongfang.dicos.lottery.info.MyInfoActivity;
import com.dongfang.dicos.lottery.info.ProbabilityListActivity;
import com.dongfang.dicos.util.ULog;

/**
 * 抽奖信息
 * 
 * @author dongfang
 * */
public class InfoActivity extends TabActivity implements OnClickListener, OnCheckedChangeListener {

	public static final String	tag			= "InfoActivity";

	/** 顶部两个信息按钮的group */
	private RadioGroup			radioGroup;
	private TabHost				tabHost;
	public static final String	TAB_ITEM_1	= "MyInfo";
	public static final String	TAB_ITEM_2	= "probabilitylist";

	/** 返回按钮 */
	private Button				bBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery_info);

		bBack = (Button) findViewById(R.id.button_lottery_info_back);
		bBack.setOnClickListener(this);

		radioGroup = (RadioGroup) findViewById(R.id.radiogroup_lottery_info_main);
		radioGroup.setOnCheckedChangeListener(this);

		tabHost = this.getTabHost();

		TabSpec tab1 = tabHost.newTabSpec(TAB_ITEM_1);
		TabSpec tab2 = tabHost.newTabSpec(TAB_ITEM_2);

		tab1.setIndicator(TAB_ITEM_1).setContent(new Intent(this, MyInfoActivity.class));
		tab2.setIndicator(TAB_ITEM_2).setContent(new Intent(this, ProbabilityListActivity.class));

		tabHost.addTab(tab1);
		tabHost.addTab(tab2);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_lottery_info_back:
			finish();
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radiobutton_lottery_info_myinfo:
			tabHost.setCurrentTabByTag(TAB_ITEM_1);
			break;
		case R.id.radiobutton_lottery_info_probabilitylist:
			tabHost.setCurrentTabByTag(TAB_ITEM_2);
			break;

		}
	}

}
