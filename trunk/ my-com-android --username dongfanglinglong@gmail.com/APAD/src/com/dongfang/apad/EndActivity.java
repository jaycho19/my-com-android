package com.dongfang.apad;

import com.dongfang.apad.bean.TestResult;
import com.dongfang.apad.param.ComParams;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

	private TextView	tvUserInfo;

	private TextView	tvClassify;
	private TextView	tvTestItem;
	private TextView	tvTestTimes;
	private TextView	tvTestResult;
	private TextView	tvTestDate;
	private ImageView	ivTestGrade;
	private TextView	tvTestResultDes;

	private Button		btnTestAgain;

	private TestResult	testResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.end_activity);
		//initView();

		// testResult = getIntent().getParcelableExtra(ComParams.ACTIVITY_TESTRESULT);

	}

	private void initView() {
		tvUserInfo = (TextView) findViewById(R.id.tv_userinfo);

		tvClassify = (TextView) findViewById(R.id.tv_classify);
		tvTestItem = (TextView) findViewById(R.id.tv_testitem);
		tvTestTimes = (TextView) findViewById(R.id.tv_testtimes);
		tvTestResult = (TextView) findViewById(R.id.tv_testresult);
		tvTestDate = (TextView) findViewById(R.id.tv_testdate);
		ivTestGrade = (ImageView) findViewById(R.id.iv_testgrade);
		tvTestResultDes = (TextView) findViewById(R.id.tv_testresult_des);
		findViewById(R.id.tv_back).setOnClickListener(this);
		findViewById(R.id.btn_testagain).setOnClickListener(this);
	}

//	@Override
//	protected void onStart() {
//		super.onStart();
//		tvUserInfo.setText(testResult.getResultDes());
//
//		tvClassify.setText(testResult.getClassify());
//		tvTestItem.setText(testResult.getItem());
//		tvTestTimes.setText(testResult.getTimes());
//		tvTestDate.setText(testResult.getDate());
//		tvTestResult.setText(testResult.getResult());
//		tvTestResultDes.setText(testResult.getResultDes());
//
//		int iImageId = R.drawable.level_03;
//		switch (testResult.getResultGray()) {
//		case 1:
//			iImageId = R.drawable.level_01;
//			break;
//		case 2:
//			iImageId = R.drawable.level_02;
//			break;
//		case 4:
//			iImageId = R.drawable.level_04;
//			break;
//		case 5:
//			iImageId = R.drawable.level_05;
//			break;
//		}
//
//		ivTestGrade.setImageResource(iImageId);
//	}

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
