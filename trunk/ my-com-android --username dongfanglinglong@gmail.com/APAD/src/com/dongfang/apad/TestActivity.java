package com.dongfang.apad;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.dongfang.apad.analytic.GetCardInfo;
import com.dongfang.apad.analytic.ReadAsynctask;
import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.SystemUiHider;
import com.dongfang.apad.util.Util;
import com.dongfang.apad.view.MyProgressDialog;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class TestActivity extends Activity {

	private TextView	content;
	private EditText	ip;
	private MyProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		progressDialog = MyProgressDialog.show(this, "数据上传中");
		
		content = (TextView) findViewById(R.id.fullscreen_content);
		ip = (EditText) findViewById(R.id.editText_addr);
		findViewById(R.id.connect_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ComParams.IP = ip.getText().toString();
				new GetCardInfo(TestActivity.this, mHideHandler).execute();
			}
		});
		findViewById(R.id.read_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ComParams.IP = ip.getText().toString();
				new ReadAsynctask(TestActivity.this, mHideHandler).execute();
			}
		});
		findViewById(R.id.upload_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mHideHandler.sendEmptyMessage(2);
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

	Handler	mHideHandler	= new Handler() {
								@Override
								public void handleMessage(Message msg) {
									StringBuilder sb = new StringBuilder();
									switch (msg.what) {
									case 1:
										sb.append("---------------" + Util.getDefDate()).append("\n");
										sb.append((String) msg.obj).append("\n");
										if (!TextUtils.isEmpty(content.getText().toString())) {
											sb.append(content.getText().toString());
										}
										content.setText(sb.toString());
										break;
									case 2:
										progressDialog.show();
										this.sendEmptyMessageDelayed(3, 3000);
										break;
									case 3:
										progressDialog.dismiss();
										sb.append("---------------" + Util.getDefDate()).append("\n");
										sb.append("您的体制优，请保持锻炼").append("\n");
										sb.append("上传成功").append("\n");
										if (!TextUtils.isEmpty(content.getText().toString())) {
											sb.append(content.getText().toString());
										}
										content.setText(sb.toString());
										break;
									}

								}

							};
}
