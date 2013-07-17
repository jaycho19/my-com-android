package com.dongfang.apad;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.apad.asynctask.SaveTestResult;
import com.dongfang.apad.asynctask.WriteGripToCard;
import com.dongfang.apad.bean.TestResult;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.broadcast.UpdateDataReceiver;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.service.DFService;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.view.DialogFactory;
import com.dongfang.apad.view.DialogFactory.onBtnClickListener;

/**
 * 测试结果
 * 
 * @author dongfang
 * 
 */
public class EndActivity extends BaseActivity implements android.view.View.OnClickListener {
	@Override
	protected void setBaseValues() {
		TAG = EndActivity.class.getSimpleName();
	}

	/** @deprecated */
	public static final int		TESTTIME_TOTAL		= 2;
	private int					times				= 0;
	private int					gray				= 0;
	private Button				btnTestagain;

	/** 测试页面名称 */
	private TextView			tvPageName;
	/** 用户信息 */
	private TextView			tvUserInfo;

	/** 行数据 */
	private TableLayout[]		tableLayout			= new TableLayout[2];
	/** 分类 */
	private TextView[]			tvClassify			= new TextView[2];
	/** 项目描述 */
	private TextView[]			tvTestItemDes		= new TextView[2];
	/** 项目名称 */
	private TextView[]			tvTestItem			= new TextView[2];
	/** 第二项目名称 */
	private TextView[]			tvTestItem1			= new TextView[2];
	/** 测试结果 */
	private TextView[]			tvTestResult		= new TextView[2];
	/** 第二项测试结果 */
	private TextView[]			tvTestResult1		= new TextView[2];
	/** 测试次数 */
	private TextView[]			tvTestTimes			= new TextView[2];
	/** 测试日期 */
	private TextView[]			tvTestDate			= new TextView[2];
	/** 测试成绩 */
	private ImageView[]			ivTestGrade			= new ImageView[2];
	/** 成绩描述 */
	private TextView			tvTestResultDes		= null;
	/** 成绩描述图片 */
	private ImageView			iv_testresult_des	= null;

	/** 成绩描述布局 */
	private LinearLayout		ll_1_testresult_des;

	/** 读卡器连接状态 */
	private TextView			tvCardSocketInfo;
	/** 中控板连接状态 */
	private TextView			tvTestZKTSocketInfo;

	private UpdateDataReceiver	updateDataReceiver;

	/** 测试结果类 */
	private TestResult			testResult			= null;
	private UserInfo			userInfo			= null;
	private DialogFactory		dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_activity);
		initView();
		dialog = new DialogFactory(this);

		if (0 != getIntent().getIntExtra(ComParams.ACTIVITY_PAGENAME, 0)) {
			tvPageName.setText(getIntent().getIntExtra(ComParams.ACTIVITY_PAGENAME, 0));
		}

		if (null != getIntent().getParcelableExtra(ComParams.ACTIVITY_TESTRESULT)) {
			testResult = getIntent().getParcelableExtra(ComParams.ACTIVITY_TESTRESULT);
		}
		if (null != getIntent().getParcelableExtra(ComParams.ACTIVITY_USERINFO)) {
			userInfo = getIntent().getParcelableExtra(ComParams.ACTIVITY_USERINFO);
		}

		if (null == testResult && null != savedInstanceState) {
			testResult = savedInstanceState.getParcelable(ComParams.ACTIVITY_TESTRESULT);
		}
		if (null == userInfo && null != savedInstanceState) {
			userInfo = savedInstanceState.getParcelable(ComParams.ACTIVITY_USERINFO);
		}

		updateDataReceiver = new UpdateDataReceiver();
		updateDataReceiver.setOnUpdateDataListener(new MyOnUpdateDataListener());
	}

	/** 初始化view */
	private void initView() {
		btnTestagain = (Button) findViewById(R.id.btn_testagain);
		btnTestagain.setOnClickListener(this);
		btnTestagain.setClickable(false);
		findViewById(R.id.tv_back).setOnClickListener(this);
		// findViewById(R.id.btn_write_to_card).setOnClickListener(this);
		// findViewById(R.id.btn_save_to_cloud).setOnClickListener(this);

		tvCardSocketInfo = (TextView) findViewById(R.id.tv_cardSocketInfo);
		tvTestZKTSocketInfo = (TextView) findViewById(R.id.tv_testZKTSocketInfo);
		tvPageName = (TextView) findViewById(R.id.tv_page_name);
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);
		tableLayout[0] = (TableLayout) findViewById(R.id.tableLayout0);
		tableLayout[1] = (TableLayout) findViewById(R.id.tableLayout1);
		tvClassify[0] = (TextView) findViewById(R.id.tv_0_classify);
		tvClassify[1] = (TextView) findViewById(R.id.tv_1_classify);
		tvTestItemDes[0] = (TextView) findViewById(R.id.tv_0_testitemDes);
		tvTestItemDes[1] = (TextView) findViewById(R.id.tv_1_testitemDes);
		tvTestItem[0] = (TextView) findViewById(R.id.tv_0_testitem);
		tvTestItem[1] = (TextView) findViewById(R.id.tv_1_testitem);
		tvTestItem1[0] = (TextView) findViewById(R.id.tv_0_testitem1);
		tvTestItem1[1] = (TextView) findViewById(R.id.tv_1_testitem1);
		tvTestResult[0] = (TextView) findViewById(R.id.tv_0_testresult);
		tvTestResult[1] = (TextView) findViewById(R.id.tv_1_testresult);
		tvTestResult1[0] = (TextView) findViewById(R.id.tv_0_testresult1);
		tvTestResult1[1] = (TextView) findViewById(R.id.tv_1_testresult1);
		tvTestTimes[0] = (TextView) findViewById(R.id.tv_0_testtimes);
		tvTestTimes[1] = (TextView) findViewById(R.id.tv_1_testtimes);
		tvTestDate[0] = (TextView) findViewById(R.id.tv_0_testdate);
		tvTestDate[1] = (TextView) findViewById(R.id.tv_1_testdate);
		ivTestGrade[0] = (ImageView) findViewById(R.id.iv_0_testgrade);
		ivTestGrade[1] = (ImageView) findViewById(R.id.iv_1_testgrade);
		// tvTestResultDes[0] = (TextView) findViewById(R.id.tv_1_testresult_des);
		// tvTestResultDes[1] = (TextView) findViewById(R.id.tv_1_testresult_des);
		// iv_testresult_des[0] = (ImageView) findViewById(R.id.iv_1_testresult_des);
		// iv_testresult_des[1] = (ImageView) findViewById(R.id.iv_1_testresult_des);
		tvTestResultDes = (TextView) findViewById(R.id.tv_1_testresult_des);
		iv_testresult_des = (ImageView) findViewById(R.id.iv_1_testresult_des);
		ll_1_testresult_des = (LinearLayout) findViewById(R.id.ll_1_testresult_des);

		tableLayout[1].setVisibility(View.GONE);
		ll_1_testresult_des.setVisibility(View.GONE);

	}

	/** 初始化数据 */
	private void initData() {
		if (null != userInfo) {
			tvUserInfo.setText(userInfo.getUserShowInfo());
		}

		if (null != testResult) {
			tvClassify[times].setText(testResult.getClassify());
			// --------------------------------------------------------
			if (TextUtils.isEmpty(testResult.getItemDes())) {
				tvTestItemDes[0].setVisibility(View.GONE);
				tvTestItemDes[1].setVisibility(View.GONE);
			}
			else {
				tvTestItemDes[times].setVisibility(View.VISIBLE);
				tvTestItemDes[times].setText(testResult.getItemDes());
			}
			tvTestItem[times].setText(testResult.getItem());
			if (TextUtils.isEmpty(testResult.getItem1())) {
				tvTestItem1[0].setVisibility(View.GONE);
				tvTestItem1[1].setVisibility(View.GONE);
			}
			else {
				tvTestItem1[times].setVisibility(View.VISIBLE);
				tvTestItem1[times].setText(testResult.getItem1());
			}
			// --------------------------------------------------------
			if (1 == times && testResult.getIsFinish() == 0 && tvTestResult[0].getText().toString().equals(Double.toString(testResult.getResult())))
				tvTestResult[1].setText("0.0");
			else
				tvTestResult[times].setText(Double.toString(testResult.getResult()));
			if (testResult.getResult1() < 0.1) {
				tvTestResult1[0].setVisibility(View.GONE);
				tvTestResult1[1].setVisibility(View.GONE);
			}
			else {
				tvTestResult1[times].setVisibility(View.VISIBLE);
				tvTestResult1[times].setText(Double.toString(testResult.getResult1()));
			}
			// ------次数不填写--------------------------------------------------
			// tvTestTimes[times].setText(Integer.toString(times));
			// --------------------------------------------------------
			tvTestDate[times].setText(testResult.getDate());
			// --------------------------------------------------------
			int iImageId = R.drawable.level_03;
			switch (testResult.getResultGray()) {
			case 1:
				iImageId = R.drawable.level_01;
				break;
			case 2:
				iImageId = R.drawable.level_02;
				break;
			case 4:
				iImageId = R.drawable.level_04;
				break;
			case 5:
				iImageId = R.drawable.level_05;
				break;
			}
			ivTestGrade[times].setImageResource(iImageId);
			// --------------------------------------------------------
			if (1 == times) {
				gray = Math.max(testResult.getResultGray(), gray);
				switch (gray) {
				case 2:
					iv_testresult_des.setImageResource(R.drawable.notice_level02);
					tvTestResultDes.setText("您的握力最好成绩评分为2分，说明您手臂的力量较差，容易导致手臂的骨骼（桡骨）骨质少空孔，并增加手臂肌肉劳损和 骨折的风险。科学的力量练习能有效降低风险。");
					break;
				case 3:
					iv_testresult_des.setImageResource(R.drawable.notice_level03);
					tvTestResultDes.setText("您的握力最好成绩评分为 3 分，说明您手臂的力量较差，容易导致手臂的骨骼（桡骨）骨质少空孔，并增加手臂肌肉劳损和 骨折的风险。科学的力量练习能有效降低风险。");
					break;
				case 4:
					iv_testresult_des.setImageResource(R.drawable.notice_level03);
					tvTestResultDes.setText("您的握力最好成绩评分为 4 分，说明您手臂的力量较好，请保持。");
					break;
				case 5:
					iv_testresult_des.setImageResource(R.drawable.notice_level03);
					tvTestResultDes.setText("您的握力最好成绩评分为 5 分，说明您手臂的力量非常好，请保持。");
					break;
				case 1:
				default:
					iv_testresult_des.setImageResource(R.drawable.notice_level01);
					tvTestResultDes.setText("您的握力最好成绩评分为1分，说明您手臂的力量较差，容易导致手臂的骨骼（桡骨）骨质少空孔，并增加手臂肌肉劳损和 骨折的风险。科学的力量练习能有效降低风险。");
					break;
				}

			}
			else if (0 == times) {
				gray = testResult.getResultGray();
			}

			if (times == 1) {
				tableLayout[1].setVisibility(View.VISIBLE);
				// ll_1_testresult_des.setVisibility(View.VISIBLE);
			}
		}
	}

	// @Override
	protected void onStart() {
		super.onStart();
		initData();
		IntentFilter filter = new IntentFilter();
		filter.addAction(getPackageName().toString() + "." + UpdateDataReceiver.TAG);
		registerReceiver(updateDataReceiver, filter);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intentService = new Intent(EndActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
				startService(intentService);
			}
		}, 500);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(updateDataReceiver);

		Intent intentService = new Intent(this, DFService.class);
		intentService.putExtra(ComParams.SERVICE_CLEAR_TESTINFO, ComParams.SERVICE_CLEAR_TESTINFO);
		intentService.putExtra(ComParams.SERVICE_HANDLER_REMOVE_ALL, ComParams.SERVICE_HANDLER_REMOVE_ALL);
		intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CLOSE_CARD, ComParams.HANDLER_SOCKET_CLOSE_TEST_ZKT });
		startService(intentService);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable(ComParams.ACTIVITY_TESTRESULT, testResult);
		savedInstanceState.putParcelable(ComParams.ACTIVITY_USERINFO, userInfo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		// case R.id.btn_write_to_card: {
		// Intent intentService = new Intent(EndActivity.this, DFService.class);
		// intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] {
		// ComParams.HANDLER_SOCKET_WRITE_CARD_INFO });
		// startService(intentService);
		// }
		// break;
		// case R.id.btn_save_to_cloud: {
		// Intent intentService = new Intent(EndActivity.this, DFService.class);
		// intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] {
		// ComParams.HANDLER_SOCKET_SAVE_TO_CLOUD });
		// startService(intentService);
		// btnTestagain.setClickable(false);

		// }
		// break;
		case R.id.btn_testagain: {
			if (times < 1) {
				times = 1;
				btnTestagain.setClickable(false);
				 Intent intentService = new Intent(EndActivity.this, DFService.class);
				 intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] {
				 ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START });
				 startService(intentService);
			}

			if (1 <= times) {
				btnTestagain.setVisibility(View.INVISIBLE);
				tableLayout[1].setVisibility(1);
			}

		}
			break;
		default:
			break;
		}
	}

	public void reWriteToCard() {
		dialog.showDialogAboutCard(new onBtnClickListener() {

			@Override
			public void btnOkClickListener(View v) {
				double max = Math.max(testResult.getResult(), Double.valueOf(tvTestResult[0].getText().toString()));
				new WriteGripToCard(EndActivity.this, userInfo).execute(max, 2.0);
			}

			@Override
			public void btnCancleClickListener(View v) {
				// TODO Auto-generated method stub
			}

		}, true);

	}

	class MyOnUpdateDataListener implements OnUpdateDataListener {

		@Override
		public void onSocketConnectCard(boolean isConnect, Bundle data) {
			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				tvCardSocketInfo.setText(data.getString(ComParams.BROADCAST_HANDLER_DES));
			}
		}

		@Override
		public void onGetCardId(boolean isConnect, Bundle data) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetUserInfo(boolean isConnect, Bundle data) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSocketConnectTestZKT(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onSocketConnectTestZKT isConnect = " + isConnect);

			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				tvTestZKTSocketInfo.setText(data.getString(ComParams.BROADCAST_HANDLER_DES));
			}

			ULog.e(TAG, "df  = " + isConnect + " , btnTestagain isvisibility = " + btnTestagain.isShown());

			if (isConnect) {
				if (times < 1) {
					btnTestagain.setClickable(true);
					btnTestagain.setVisibility(1);
				}

				Intent intentService = new Intent(EndActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_START });
				startService(intentService);
			}
			else {
				btnTestagain.setClickable(false);
				btnTestagain.setVisibility(4);

			}

		}

		@Override
		public void onTestZKTRestarted(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onTestZKTRestarted isConnect = " + isConnect);
			/** 有可能存在死循环错误： 与onSocketConnectCard */
			if (!isConnect) {
				Intent intentService = new Intent(EndActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
				startService(intentService);
			}

			if (null != data && !TextUtils.isEmpty(data.getString(ComParams.BROADCAST_HANDLER_DES))) {
				ULog.d(TAG, data.getString(ComParams.BROADCAST_HANDLER_DES));
			}

			if (null != data && null != data.getParcelable(ComParams.ACTIVITY_TESTRESULT)) {
				ULog.d(TAG, ((TestResult) data.getParcelable(ComParams.ACTIVITY_TESTRESULT)).toString());
				tvCardSocketInfo.setText("请测试...");

				Intent intentService = new Intent(EndActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_GET_TEST_ZKT_RESULT });
				startService(intentService);
			}
		}

		@Override
		public void onGetTestZKTResult(boolean isConnect, Bundle data) {
			ULog.d(TAG, "onGetTestZKTResult isConnect = " + isConnect);
			if (times < 1)
				btnTestagain.setClickable(true);
			if (!isConnect) {
				Intent intentService = new Intent(EndActivity.this, DFService.class);
				intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] { ComParams.HANDLER_SOCKET_CONNECT_TEST_ZKT });
				startService(intentService);
			}

			if (1 == times && null != data && null != data.getParcelable(ComParams.ACTIVITY_TESTRESULT)) {
				ULog.d(TAG, ((TestResult) data.getParcelable(ComParams.ACTIVITY_TESTRESULT)).toString());
				testResult = (TestResult) data.getParcelable(ComParams.ACTIVITY_TESTRESULT);
				initData();

				userInfo = (UserInfo) data.getParcelable(ComParams.ACTIVITY_USERINFO);

				if (0x01 == testResult.getIsFinish() && times == 1) {
					ll_1_testresult_des.setVisibility(View.VISIBLE);

					// Intent intentService = new Intent(EndActivity.this, DFService.class);
					// intentService.putExtra(ComParams.SERVICE_HANDLER_ACTION_ID, new int[] {
					// ComParams.HANDLER_SOCKET_CLOSE_CARD, ComParams.HANDLER_SOCKET_CLOSE_TEST_ZKT });
					// startService(intentService);

					double max = Math.max(testResult.getResult(), Double.valueOf(tvTestResult[0].getText().toString()));
					new SaveTestResult(EndActivity.this).execute(Integer.toString(userInfo.getUserId()), Double.toString(max));
					new WriteGripToCard(EndActivity.this, userInfo).execute(max, 1.0);
				}

			}
		}

		@Override
		public void onError(boolean isConnect, Bundle data) {
			if (data.getInt(ComParams.ACTIVITY_ERRORID) > 4)
				Toast.makeText(EndActivity.this, data.getString(ComParams.ACTIVITY_ERRORINFO), Toast.LENGTH_SHORT).show();
				//finish();
		}
	}
}
