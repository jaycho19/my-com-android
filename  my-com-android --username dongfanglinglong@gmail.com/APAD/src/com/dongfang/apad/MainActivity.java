package com.dongfang.apad;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.dongfang.apad.util.ULog;

public class MainActivity extends BaseActivity implements OnClickListener {
	@Override
	protected void setBaseValues() {
		TAG = MainActivity.class.getSimpleName();
	}

	private Button		btnLeft;
	private Button		btnRight;
	/** 体重身高 */
	private Button		btn001;
	/** 肺活量 */
	private Button		btn002;
	/** 台阶指数 */
	private Button		btn003;
	/** 握力指数 */
	private Button		btn004;
	/** 纵跳测试 */
	private Button		btn005;
	/** 俯卧撑 */
	private Button		btn006;
	/** 仰卧起坐 */
	private Button		btn007;
	/** 单眼单脚 */
	private Button		btn008;
	/** 坐位体前屈 */
	private Button		btn009;
	/** 选择反应 */
	private Button		btn010;

	private ViewFlipper	viewFlipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		btnLeft = (Button) findViewById(R.id.btn_left);
		btnRight = (Button) findViewById(R.id.btn_right);
		btn001 = (Button) findViewById(R.id.btn_001);
		btn002 = (Button) findViewById(R.id.btn_002);
		btn003 = (Button) findViewById(R.id.btn_003);
		btn004 = (Button) findViewById(R.id.btn_004);
		btn005 = (Button) findViewById(R.id.btn_005);
		btn006 = (Button) findViewById(R.id.btn_006);
		btn007 = (Button) findViewById(R.id.btn_007);
		btn008 = (Button) findViewById(R.id.btn_008);
		btn009 = (Button) findViewById(R.id.btn_009);
		btn010 = (Button) findViewById(R.id.btn_010);

		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btn001.setOnClickListener(this);
		btn002.setOnClickListener(this);
		btn003.setOnClickListener(this);
		btn004.setOnClickListener(this);
		btn005.setOnClickListener(this);
		btn006.setOnClickListener(this);
		btn007.setOnClickListener(this);
		btn008.setOnClickListener(this);
		btn009.setOnClickListener(this);
		btn010.setOnClickListener(this);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
	}

	private void showNexts(int id) {
		if (id == R.id.btn_left) {
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_rtol_exit));
			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_rtol_enter));
			viewFlipper.showPrevious();
			if (viewFlipper.getCurrentView().equals(viewFlipper.getChildAt(0))){
				btnLeft.setEnabled(false);
			}
			else{
				btnLeft.setEnabled(true);
			}
			btnRight.setEnabled(true);
		}
		else if (id == R.id.btn_right) {
			viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_ltor_enter));
			viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_ltor_exit));
			viewFlipper.showNext();
			if (viewFlipper.getCurrentView().equals(viewFlipper.getChildAt(viewFlipper.getChildCount() - 1))){
				btnRight.setEnabled(false);
			}
			else{
				btnRight.setEnabled(true);
			}
			btnLeft.setEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		ULog.d(TAG, "id = " + v.getId());
		switch (v.getId()) {
		case R.id.btn_left:
		case R.id.btn_right:
			showNexts(v.getId());
			break;
		case R.id.btn_001:break;
		case R.id.btn_002:break;
		case R.id.btn_003:break;
		case R.id.btn_004:break;
		case R.id.btn_005:break;
		case R.id.btn_006:break;
		case R.id.btn_007:break;
		case R.id.btn_008:break;
		case R.id.btn_009:break;
		case R.id.btn_010:break;
		default:
			break;
		}
	}

}
