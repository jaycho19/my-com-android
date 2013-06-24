package com.dongfang.apad;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.broadcast.CloseAppReceiver;
import com.dongfang.apad.broadcast.UpdateDataReceiver;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.service.DFService;
import com.dongfang.apad.util.ULog;

/**
 * 测试开始页面
 * 
 * @author dongfang
 * 
 */
public class StartActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void setBaseValues() {
		TAG = StartActivity.class.getSimpleName();
	}

	/** 返回按钮 */
	private TextView			btnBack;
	/** 测试页面名称 */
	private TextView			btnPageName;
	private TextView			tvTitle;
	/** 用户信息数据 */
	private TextView			tvUserInfo;
	/** 测试中数据 */
	private TextView			tvTestingResult;
	/** 测试类型图片 */
	private ImageView			imageView;

	/** 读卡器连接状态 */
	private TextView			tvCardSocketInfo;
	/** 中控板连接状态 */
	private TextView			tvTestZKTSocketInfo;

	private Intent				intent;

	private UpdateDataReceiver	updateDataReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		btnBack = (TextView) findViewById(R.id.tv_back);
		btnBack.setOnClickListener(this);
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);
		btnPageName = (TextView) findViewById(R.id.tv_page_name);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		imageView = (ImageView) findViewById(R.id.imageView);
		tvTestingResult = (TextView) findViewById(R.id.tv_testing_result);
		tvCardSocketInfo = (TextView) findViewById(R.id.tv_cardSocketInfo);
		tvTestZKTSocketInfo = (TextView) findViewById(R.id.tv_testZKTSocketInfo);

		updateDataReceiver = new UpdateDataReceiver();
		updateDataReceiver.setOnUpdateDataListener(new MyOnUpdateDataListener());

		intent = getIntent();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName().toString() + "." + UpdateDataReceiver.TAG);
		registerReceiver(updateDataReceiver, filter);

		Intent intentService = new Intent(this, DFService.class);
		intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_CARD
		// , ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT

				});
		startService(intentService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(updateDataReceiver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		ULog.d(TAG, v.toString());
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化测试界面；<br>
	 * 初始化用户信息；<br>
	 * 尝试获取测试数据；
	 */
	private void initData(UserInfo userInfo) {
		btnPageName.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		tvTitle.setText(intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name));
		imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.change_image));
		imageView.setImageResource(intent.getIntExtra(ComParams.ACTIVITY_IMAGE_SRC_ID, R.drawable.card_notice));

		ULog.d(TAG, userInfo.toString());
		tvUserInfo.setText(userInfo.getUserShowInfo());

	}

	class MyOnUpdateDataListener implements OnUpdateDataListener {

		@Override
		public void onSocketConnectCard(String des, Bundle data) {
			tvCardSocketInfo.setText(des);

			Intent intentService = new Intent(StartActivity.this, DFService.class);
			intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_CARD_ID });
			startService(intentService);
		}

		@Override
		public void onGetCardId(String des, Bundle data) {
			ULog.d(TAG, "cardId = " + des);
		}

		@Override
		public void onGetUserInfo(String des, Bundle data) {
			ULog.d(TAG, des);
			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_USERINFO))
				initData((UserInfo) data.getParcelable(ComParams.ACTIVITY_USERINFO));
		}

		@Override
		public void onSocketConnectTestZKT(String des, Bundle data) {
			tvTestZKTSocketInfo.setText(des);
		}

		@Override
		public void onTestZKTRestarted(String des, Bundle data) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onGetTestZKTResult(String des, Bundle data) {
			// TODO Auto-generated method stub
		}
	}

}
