package com.dongfang.apad;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.apad.bean.TestResult;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.broadcast.UpdateDataReceiver;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.service.DFService;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

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
	private UserInfo			userInfo;

	private int					pageNameStringid	= R.string.app_name;

	private UpdateDataReceiver	updateDataReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		btnBack = (TextView) findViewById(R.id.tv_back);
		btnBack.setOnClickListener(this);
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);
		tvUserInfo.setVisibility(View.GONE);
		btnPageName = (TextView) findViewById(R.id.tv_page_name);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		imageView = (ImageView) findViewById(R.id.imageView);
		tvTestingResult = (TextView) findViewById(R.id.test_result);
		tvCardSocketInfo = (TextView) findViewById(R.id.tv_cardSocketInfo);
		tvTestZKTSocketInfo = (TextView) findViewById(R.id.tv_testZKTSocketInfo);

		updateDataReceiver = new UpdateDataReceiver();
		updateDataReceiver.setOnUpdateDataListener(new MyOnUpdateDataListener());

		intent = getIntent();

		Util.getIPandPort(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dongfang.apad.BaseActivity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

		Intent intentService = new Intent(this, DFService.class);
		intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_CARD, ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
		startService(intentService);
	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName().toString() + "." + UpdateDataReceiver.TAG);
		registerReceiver(updateDataReceiver, filter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(updateDataReceiver);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Intent intentService = new Intent(this, DFService.class);
		intentService.putExtra(ComParams.SERVICE_HANDLER_REMOVE_ALL, ComParams.SERVICE_HANDLER_REMOVE_ALL);
		intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CLOSE_CARD
		, ComParams.HANDLER_SOCKET_CLOSE_TEST_ZKT
				});
		startService(intentService);

	}

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
		this.userInfo = null;
		this.userInfo = userInfo;

		pageNameStringid = intent.getIntExtra(ComParams.ACTIVITY_PAGENAME, R.string.app_name);

		btnPageName.setText(pageNameStringid);
		tvTitle.setText(pageNameStringid);
		imageView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.change_image));
		imageView.setImageResource(intent.getIntExtra(ComParams.ACTIVITY_IMAGE_SRC_ID, R.drawable.card_notice));

		// tvTestingResult.setVisibility(View.VISIBLE);

		ULog.d(TAG, userInfo.toString());
		tvUserInfo.setText(userInfo.getUserShowInfo());
		tvUserInfo.setVisibility(View.VISIBLE);

		Intent intentService = new Intent(this, DFService.class);
		intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START });
		startService(intentService);

		// new WriteGripToCard(this, userInfo).execute(11.1, 2.0);

	}

	class MyOnUpdateDataListener implements OnUpdateDataListener {

		@Override
		public void onSocketConnectCard(boolean isConnect, Bundle data) {
			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				tvCardSocketInfo.setText(data.getString(ComParams.BROADCAST_HANDLER_DES));
			}

			if (isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_USER_INFO });
				startService(intentService);
			}
		}

		@Override
		public void onGetCardId(boolean isConnect, Bundle data) {
			/** 有可能存在死循环错误： 与onSocketConnectCard */
			if (!isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_CARD });
				startService(intentService);
			}

			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_USERINFO)) {
				ULog.d(TAG, ((UserInfo) data.getParcelable(ComParams.ACTIVITY_USERINFO)).toString());
			}
		}

		@Override
		public void onGetUserInfo(boolean isConnect, Bundle data) {
			/** 有可能存在死循环错误： 与onSocketConnectCard */
			if (!isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_CARD });
				startService(intentService);
			}
			else {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START });
				startService(intentService);
			}
			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_USERINFO))
				initData((UserInfo) data.getParcelable(ComParams.ACTIVITY_USERINFO));
		}

		@Override
		public void onSocketConnectTestZKT(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onSocketConnectTestZKT");
			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				tvTestZKTSocketInfo.setText(data.getString(ComParams.BROADCAST_HANDLER_DES));
			}

			if (isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START });
				startService(intentService);
			}

		}

		@Override
		public void onTestZKTRestarted(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onTestZKTRestarted");
			/** 有可能存在死循环错误： 与onSocketConnectCard */
			if (!isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
				startService(intentService);
			}

			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				ULog.d(TAG, data.getString(ComParams.BROADCAST_HANDLER_DES));
			}

			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_TESTRESULT)) {
				ULog.d(TAG, ((TestResult) data.getParcelable(ComParams.ACTIVITY_TESTRESULT)).toString());
				// tvTitle.setText("请按钮");

				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT });
				startService(intentService);
			}
		}

		@Override
		public void onGetTestZKTResult(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onGetTestZKTResult");
			if (!isConnect) {
				Intent intentService = new Intent(StartActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
				startService(intentService);
			}

			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_USERINFO) && !tvUserInfo.isShown())
				initData((UserInfo) data.getParcelable(ComParams.ACTIVITY_USERINFO));

			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_TESTRESULT)) {
				TestResult testResult = (TestResult) data.getParcelable(ComParams.ACTIVITY_TESTRESULT);
				ULog.d(TAG, testResult.toString());

				if (testResult.getResult() > 0 && tvUserInfo.isShown()) {
					tvTestingResult.setText(Double.toString(testResult.getResult()));
					tvTestingResult.setVisibility(1);
				}
				else {
					tvTestingResult.setVisibility(4);
				}

				if (0x01 == testResult.getIsFinish()) {
					Intent intent = new Intent(StartActivity.this, EndActivity.class);
					// data.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
					intent.putExtra(ComParams.ACTIVITY_PAGENAME, pageNameStringid);
					intent.putExtras(data);
					startActivity(intent);
					finish();
				}
			}
		}

		@Override
		public void onError(boolean isConnect, Bundle data) {

			if (data.getInt(ComParams.ACTIVITY_ERRORID) > 4)
				Toast.makeText(StartActivity.this, data.getString(ComParams.ACTIVITY_ERRORINFO), Toast.LENGTH_SHORT).show();
				//finish();

		}
	}

}
