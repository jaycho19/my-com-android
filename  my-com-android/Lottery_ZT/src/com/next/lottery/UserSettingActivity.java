package com.next.lottery;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 设置
 * 
 * @author dongfang
 * 
 */

public class UserSettingActivity extends BaseActivity {

	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_setting);
		ViewUtils.inject(this);
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			finish();
			break;

		default:
			break;
		}

	}

}