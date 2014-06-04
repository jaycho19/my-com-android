package com.dongfang.daohang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.dongfang.v4.app.MCaptureActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TakeMeActivity extends BaseActivity {

	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;
	@ViewInject(R.id.dialog_goto_tv_qidian)
	private TextView qidian;
	@ViewInject(R.id.dialog_goto_tv_zhongdian)
	private TextView zhongdian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeme);
		title.setText("带我去");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && null != data && data.hasExtra("result")) {
			switch (requestCode) {
			case 0x00FF:
			case 0x00FE:
				qidian.setText(data.getStringExtra("result"));
				break;
			case 0x00FD:
				zhongdian.setText(data.getStringExtra("result"));
				break;
			}

		}
		StringBuilder sb = new StringBuilder();
		sb.append("requestCode = ").append(requestCode);
		sb.append("\n").append("resultCode = ").append(resultCode);
		if (null != data && data.hasExtra("result"))
			sb.append("\n").append("result = ").append(data.getStringExtra("result"));
		ULog.e(sb.toString());
	}

	@OnClick({ R.id.top_bar_btn_back, R.id.dialog_goto_im_qr, R.id.dialog_goto_tv_qidian ,R.id.dialog_goto_tv_zhongdian})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_goto_im_qr:
			startActivityForResult(new Intent(this, MCaptureActivity.class), 0x00FF);
			break;
		case R.id.dialog_goto_tv_qidian:
			startActivityForResult(new Intent(this, TakeMeSelectActivity.class), 0x00FE);
			break;
		case R.id.dialog_goto_tv_zhongdian:
			startActivityForResult(new Intent(this, TakeMeSelectActivity.class), 0x00FD);
			break;
		case R.id.top_bar_btn_back:
			finish();
			break;
		default:
			break;
		}
	}

}
