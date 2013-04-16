package com.dongfang.dicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.kzmw.LoginActivity;
import com.dongfang.dicos.more.CityListActivity;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.Util;
import com.dongfang.dicos.view.FlingAdapter;
import com.dongfang.dicos.view.FlingGallery;

/**
 * 当季活动
 * 
 * @author dongfang
 * 
 */
public class CurrentSeason extends Activity implements OnClickListener, OnTouchListener {

	// public ListView lvCurrentSeasonImg;
	// public ImgAdapter imgAdater;
	public String[]			aImgUrl;

	private TextView		tvTitle;

	/** 登录按钮 */
	private Button			bLogin;
	/** 签到按钮 */
	private Button			bSigne;

	private FlingGallery	mGallery			= null;
	private LinearLayout	ll_fling_view		= null;
	private LinearLayout	ll_fling_desc_image	= null;
	public List<String>		flingViewList		= new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason);

		if (null != getIntent() && getIntent().hasExtra(ComParams.CURRENT_SEASON_IMG_ARRARY)) {
			aImgUrl = getIntent().getStringArrayExtra(ComParams.CURRENT_SEASON_IMG_ARRARY);
		}
		else {
			aImgUrl = new String[] {};
		}

		tvTitle = (TextView) findViewById(R.id.textview_currentseason_top_title);
		tvTitle.setOnClickListener(this);

		bLogin = (Button) findViewById(R.id.button_currentseason_login);
		bLogin.setOnClickListener(this);

		bSigne = (Button) findViewById(R.id.button_currentseason_signe);
		bSigne.setOnClickListener(this);

		// imgList.add("http://www.dicos.com.cn/images/app/action/9_1348134247.jpg");

		// lvCurrentSeasonImg = (ListView)
		// findViewById(R.id.lv_current_season_list_img);
		// imgAdater = new ImgAdapter(this, aImgUrl);
		// lvCurrentSeasonImg.setAdapter(imgAdater);

		initFlingView(Arrays.asList(aImgUrl));

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Util.isLogin(this)) {
			bLogin.setVisibility(View.GONE);
		}
		else {
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
		case R.id.textview_currentseason_top_title:
			intent = new Intent(CurrentSeason.this, CityListActivity.class);
			startActivity(intent);
			break;
		case R.id.button_currentseason_login:
			if (!Util.isNetworkAvailable(CurrentSeason.this)) {
				Util.showDialogSetNetWork(CurrentSeason.this);
			}
			else if (Util.isLogin(CurrentSeason.this)) {
				Toast.makeText(CurrentSeason.this, "您已登录", Toast.LENGTH_LONG).show();
			}
			else {
				intent = new Intent(CurrentSeason.this, LoginActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.button_currentseason_signe:
			if (!Util.isLogin(CurrentSeason.this)) {
				Util.showDialogLogin(CurrentSeason.this);
			}
			else {
				intent = new Intent(CurrentSeason.this, StoreSearchActivity.class);
				intent.putExtra("visibility", true);
				startActivity(intent);

			}
			break;
		}
	}

	public void initFlingView(final List<String> flingViewList) {
		ll_fling_view = (LinearLayout) findViewById(R.id.ll_fling_view);
		ll_fling_desc_image = (LinearLayout) findViewById(R.id.ll_fling_desc_image);

		int size = flingViewList.size();
		ll_fling_desc_image.removeAllViews();
		for (int i = 0; i < size; i++) {
			ImageView image = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
			params.setMargins(5, 0, 5, 0);
			ll_fling_desc_image.addView(image, params);
			if (i == 0) {
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_focsed_0));
			}
			else {
				image.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.fling_point_unfocsed_0));
			}
		}

		if (mGallery != null) {
			mGallery.setAdapter(new FlingAdapter(this, flingViewList));
			mGallery.refreshDrawableState();
		}
		else {
			mGallery = new FlingGallery(this, null, ll_fling_desc_image);
			mGallery.setPaddingWidth(0);
			mGallery.setAdapter(new FlingAdapter(this, flingViewList));
			mGallery.setIsGalleryCircular(false);
		}

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		ll_fling_view.setOrientation(LinearLayout.HORIZONTAL);
		ll_fling_view.removeAllViews();
		ll_fling_view.addView(mGallery, layoutParams);
		ll_fling_view.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.ll_fling_view:
			return mGallery.onGalleryTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Util.showExitDialog(this);

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
	
}
