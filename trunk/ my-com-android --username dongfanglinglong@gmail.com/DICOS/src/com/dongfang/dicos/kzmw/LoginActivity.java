package com.dongfang.dicos.kzmw;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.LoginThread;
import com.dongfang.dicos.net.thread.ValidateThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 登录
 * 
 * @author dongfang
 * */
public class LoginActivity extends Activity implements OnClickListener {

	public static final String	tag				= "android:maxLength";

	/** 返回 */
	private Button				bBack;

	/** 获取验证码 */
	private Button				bGetAuthCode;
	/** 确认递交 */
	private Button				bOK;

	/** 手机号码 */
	private EditText			etPhoneNumber;
	/** 验证码 */
	private EditText			etAuthCode;

	private int					lockSeconds		= -1;

	private Handler				loginHandler	= new LoginHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		bBack = (Button) findViewById(R.id.button_login_back);
		bBack.setOnClickListener(this);

		bGetAuthCode = (Button) findViewById(R.id.button_login_getauthcode);
		bGetAuthCode.setOnClickListener(this);

		bOK = (Button) findViewById(R.id.button_login_ok);
		bOK.setOnClickListener(this);

		etPhoneNumber = (EditText) findViewById(R.id.editview_login_phonenumber);
		etAuthCode = (EditText) findViewById(R.id.editview_login_authcode);
		etAuthCode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (etAuthCode.getText().toString().length() > 0)
					bOK.setClickable(true);
				else
					bOK.setClickable(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

		// 初始化最近一次登录的手机号码
		SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME, Activity.MODE_PRIVATE);
		etPhoneNumber.setText(Util.getPhoneNumber(this));

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_login_getauthcode:
			/**
			 * 1. 获取手机验证码，手机号码不允许为空； 2. 60s以内不允许重复获取，需要60秒倒计时；
			 * */

			if (etPhoneNumber.getText().toString().length() != 11) {
				Toast.makeText(LoginActivity.this, "请输入正确的手机号码...", Toast.LENGTH_LONG);
			} else {
				bGetAuthCode.setClickable(false);
				lockSeconds = ComParams.BUTTON_GET_AUTH_CODE_LOCKED_SECOND;
				bGetAuthCode.setText(String.format(getResources().getString(R.string.button_get_auth_code_locked),
						lockSeconds--));
				loginHandler.sendEmptyMessage(ComParams.HANDLER_LOGIN_GET_AUTH_CODE_LOCKED);
				new LoginThread(LoginActivity.this, loginHandler, etPhoneNumber.getText().toString()).start();
			}
			break;
		case R.id.button_login_ok:
			/**
			 * 1. 获取验证码按钮等待事件结束，lockSeconds置为-1; 2. 锁定登录按钮
			 * 
			 */

			if (11 != etPhoneNumber.getText().toString().length() && 6 != etAuthCode.getText().toString().length()) {
				Toast.makeText(LoginActivity.this, R.string.login_input_error, Toast.LENGTH_LONG).show();
			} else {
				loginHandler.removeMessages(ComParams.HANDLER_LOGIN_GET_AUTH_CODE_LOCKED);
				lockSeconds = -1;
				bGetAuthCode.setText(getResources().getString(R.string.button_get_auth_code));
				bGetAuthCode.setClickable(false);
				bOK.setClickable(false);
				new ValidateThread(LoginActivity.this, loginHandler, etPhoneNumber.getText().toString(), etAuthCode
						.getText().toString()).start();
				savePhoneNumber();
			}
			break;
		case R.id.button_login_back:
			// /if (isLogin())
			finish();
			// /else
			// super.onKeyDown(KeyEvent.KEYCODE_BACK, null);
			break;
		}
	}

	/** 保存手机号码 */
	private void savePhoneNumber() {
		Util.setPhoneNumber(this, etPhoneNumber.getText().toString());
	}

	/** 保存登录状态 */
	private void saveLoginStatus() {
		Util.setLoginStatus(this, true);
	}

	class LoginHandler extends Handler {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_VALIDATE:
				/**
				 * 登录成功，保存登录成功标识；
				 * 
				 * */
				data = msg.getData();
				if (Actions.ACTIONS_TYPE_VALIDATE.equalsIgnoreCase(data.getString(Actions.ACTIONS_KEY_ACT))
						&& "1".equals(data.getString(Actions.ACTIONS_KEY_RESULT))) {
					saveLoginStatus();
					Toast.makeText(LoginActivity.this, R.string.login_result_success, Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(LoginActivity.this, R.string.login_result_fail, Toast.LENGTH_LONG).show();
					etAuthCode.setText("");
				}

				bOK.setClickable(true);
				bGetAuthCode.setClickable(true);

				break;
			case ComParams.HANDLER_RESULT_LOGIN:
				/** 验证码获取成功 */
				data = msg.getData();
				if (data.getString(Actions.ACTIONS_KEY_ACT).equalsIgnoreCase(Actions.ACTIONS_TYPE_LOGIN)
						&& data.getString(Actions.ACTIONS_KEY_RESULT).equals("1")) {
					Toast.makeText(LoginActivity.this, R.string.login_sending_code, Toast.LENGTH_LONG).show();
				}
				break;
			case ComParams.HANDLER_LOGIN_GET_AUTH_CODE_LOCKED:
				if (1 < lockSeconds) {
					bGetAuthCode.setText(String.format(getResources().getString(R.string.button_get_auth_code_locked),
							lockSeconds--));
					this.sendEmptyMessageDelayed(ComParams.HANDLER_LOGIN_GET_AUTH_CODE_LOCKED, 1000);
				} else {
					bGetAuthCode.setText(getResources().getString(R.string.button_get_auth_code));
					bGetAuthCode.setClickable(true);
				}
				break;

			}
		}
	}

}
