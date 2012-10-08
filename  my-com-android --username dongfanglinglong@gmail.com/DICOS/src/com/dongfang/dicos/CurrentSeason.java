package com.dongfang.dicos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.adapter.ImgAdapter;
import com.dongfang.dicos.kzmw.LoginActivity;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.Util;

/**
 * 当季活动
 * 
 * @author dongfang
 * 
 */
public class CurrentSeason extends Activity implements OnClickListener {

	public ListView		lvCurrentSeasonImg;
	public ImgAdapter	imgAdater;
	public String[]		aImgUrl;

	private TextView	tvTitle;

	/** 登录按钮 */
	private Button		bLogin;
	/** 签到按钮 */
	private Button		bSigne;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason);

		if (null != getIntent() && getIntent().hasExtra(ComParams.CURRENT_SEASON_IMG_ARRARY)) {
			aImgUrl = getIntent().getStringArrayExtra(ComParams.CURRENT_SEASON_IMG_ARRARY);
		} else {
			aImgUrl = new String[] {};
		}

		tvTitle = (TextView) findViewById(R.id.textview_currentseason_top_title);
		tvTitle.setOnClickListener(this);

		bLogin = (Button) findViewById(R.id.button_currentseason_login);
		bLogin.setOnClickListener(this);

		bSigne = (Button) findViewById(R.id.button_currentseason_signe);
		bSigne.setOnClickListener(this);

		// imgList.add("http://www.dicos.com.cn/images/app/action/9_1348134247.jpg");

		lvCurrentSeasonImg = (ListView) findViewById(R.id.lv_current_season_list_img);
		imgAdater = new ImgAdapter(this, aImgUrl);
		lvCurrentSeasonImg.setAdapter(imgAdater);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Util.isLogin(this)) {
			bLogin.setVisibility(View.GONE);
		} else {
			bLogin.setVisibility(View.VISIBLE);
		}

		if (TextUtils.isEmpty(ComParams.IPAREA)) {
			Util.iniIPArea(this);
		}

		if (ComParams.IPAREA.length() > 5)
			tvTitle.setText(ComParams.IPAREA.substring(0, 5) + "***");
		else if (ComParams.IPAREA.length() > 0)
			tvTitle.setText(ComParams.IPAREA);

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_currentseason_login:
			if (!Util.isNetworkAvailable(CurrentSeason.this)) {
				Util.showDialogSetNetWork(CurrentSeason.this);
			} else if (Util.isLogin(CurrentSeason.this)) {
				Toast.makeText(CurrentSeason.this, "您已登录", Toast.LENGTH_LONG).show();
			} else {
				intent = new Intent(CurrentSeason.this, LoginActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.button_currentseason_signe:
			if (!Util.isLogin(CurrentSeason.this)) {
				Util.showDialogLogin(CurrentSeason.this);
			} else {
				intent = new Intent(CurrentSeason.this, StoreSearchActivity.class);
				intent.putExtra("visibility", true);
				startActivity(intent);

			}
			break;
		}

	}
}
