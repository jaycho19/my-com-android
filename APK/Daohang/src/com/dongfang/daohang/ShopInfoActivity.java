package com.dongfang.daohang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/***
 * 商户详情
 * 
 * @author dongfang
 *
 */
public class ShopInfoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopinfo);
	}

	@OnClick({ R.id.activity_shopinfo_tv_discount })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_shopinfo_tv_discount:
			startActivity(new Intent(this, ShopWabActivity.class));
			break;

		default:
			break;
		}

	}

}
