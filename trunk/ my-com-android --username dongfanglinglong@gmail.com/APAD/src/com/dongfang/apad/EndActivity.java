package com.dongfang.apad;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.apad.bean.TestResult;
import com.dongfang.apad.bean.UserInfo;
import com.dongfang.apad.param.ComParams;

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

	/** 测试页面名称 */
	private TextView	tvPageName;
	/** 用户信息 */
	private TextView	tvUserInfo;

	/** 分类 */
	private TextView	tvClassify;
	/** 项目描述 */
	private TextView	tvTestItemDes;
	/** 项目名称 */
	private TextView	tvTestItem;
	/** 第二项目名称 */
	private TextView	tvTestItem1;
	/** 测试结果 */
	private TextView	tvTestResult;
	/** 第二项测试结果 */
	private TextView	tvTestResult1;
	/** 测试次数 */
	private TextView	tvTestTimes;
	/** 测试日期 */
	private TextView	tvTestDate;
	/** 测试成绩 */
	private ImageView	ivTestGrade;
	/** 成绩描述 */
	private TextView	tvTestResultDes;
	/** 成绩描述图片 */
	private ImageView	iv_testresult_des;

	/** 测试结果类 */
	private TestResult	testResult	= null;
	private UserInfo	userInfo	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_activity);
		initView();

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
	}

	/** 初始化view */
	private void initView() {
		tvPageName = (TextView) findViewById(R.id.tv_page_name);
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);
		tvClassify = (TextView) findViewById(R.id.tv_classify);
		tvTestItemDes = (TextView) findViewById(R.id.tv_testitemDes);
		tvTestItem = (TextView) findViewById(R.id.tv_testitem);
		tvTestItem1 = (TextView) findViewById(R.id.tv_testitem1);
		tvTestResult = (TextView) findViewById(R.id.tv_testresult);
		tvTestResult1 = (TextView) findViewById(R.id.tv_testresult1);
		tvTestTimes = (TextView) findViewById(R.id.tv_testtimes);
		tvTestDate = (TextView) findViewById(R.id.tv_testdate);
		ivTestGrade = (ImageView) findViewById(R.id.iv_testgrade);
		tvTestResultDes = (TextView) findViewById(R.id.tv_testresult_des);
		iv_testresult_des = (ImageView) findViewById(R.id.iv_testresult_des);
		findViewById(R.id.tv_back).setOnClickListener(this);
		findViewById(R.id.btn_testagain).setOnClickListener(this);
	}

	/** 初始化数据 */
	private void initData() {
		if (null != userInfo) {
			tvUserInfo.setText(userInfo.getUserShowInfo());
		}

		if (null != testResult) {
			tvClassify.setText(testResult.getClassify());
			// --------------------------------------------------------
			if (TextUtils.isEmpty(testResult.getItemDes())) {
				tvTestItemDes.setVisibility(View.GONE);
			}
			else {
				tvTestItemDes.setVisibility(View.VISIBLE);
				tvTestItemDes.setText(testResult.getItemDes());
			}
			tvTestItem.setText(testResult.getItem());
			if (TextUtils.isEmpty(testResult.getItem1())) {
				tvTestItem1.setVisibility(View.GONE);
			}
			else {
				tvTestItem1.setVisibility(View.VISIBLE);
				tvTestItem1.setText(testResult.getItem1());
			}
			// --------------------------------------------------------
			tvTestResult.setText(testResult.getResult());
			if (TextUtils.isEmpty(testResult.getResult1())) {
				tvTestResult1.setVisibility(View.GONE);
			}
			else {
				tvTestResult1.setVisibility(View.VISIBLE);
				tvTestResult1.setText(testResult.getResult1());
			}
			// --------------------------------------------------------
			tvTestTimes.setText(testResult.getTimes());
			// --------------------------------------------------------
			tvTestDate.setText(testResult.getDate());
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
			ivTestGrade.setImageResource(iImageId);
			// --------------------------------------------------------
			tvTestResultDes.setText(testResult.getResultDes());
			if (1 == testResult.getResultGray()) {
				iv_testresult_des.setImageResource(R.drawable.notice_level01);
			}
			else if (2 == testResult.getResultGray()) {
				iv_testresult_des.setImageResource(R.drawable.notice_level02);
			}
			else {
				iv_testresult_des.setImageResource(R.drawable.notice_level03);
			}

		}
	}

	// @Override
	protected void onStart() {
		super.onStart();
		initData();
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
		case R.id.btn_testagain:
			// testagain
			break;

		default:
			break;
		}
	}
}
