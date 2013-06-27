package com.dongfang.apad;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.Util;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class SetActivity extends BaseActivity implements android.view.View.OnClickListener {

	private EditText	etCardIP, etCartPort, etZKTIP, etZKTPort;

	@Override
	protected void setBaseValues() {
		TAG = SetActivity.class.getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_activity);

		etCardIP = (EditText) findViewById(R.id.et_card_ip);
		etCartPort = (EditText) findViewById(R.id.et_card_port);
		etZKTIP = (EditText) findViewById(R.id.et_zkt_ip);
		etZKTPort = (EditText) findViewById(R.id.et_zkt_port);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
		findViewById(R.id.btn_ok).setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Util.getIPandPort(this);
		etCardIP.setText(ComParams.IP_CARD);
		etCartPort.setText(ComParams.PORT_CARD + "");
		etZKTIP.setText(ComParams.IP_TEST);
		etZKTPort.setText(ComParams.PORT_TEST + "");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel:
			finish();
			break;
		case R.id.btn_ok:
			Util.saveIPandPort(this, etCardIP.getText().toString(), Integer.valueOf(etCartPort.getText().toString()),
					etZKTIP.getText().toString(), Integer.valueOf(etZKTPort.getText().toString()));
			break;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
}
