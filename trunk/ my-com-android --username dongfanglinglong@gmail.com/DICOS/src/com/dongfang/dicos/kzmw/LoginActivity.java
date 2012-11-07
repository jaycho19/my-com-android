package com.dongfang.dicos.kzmw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.GetPasswordThread;
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

	public static final String	tag				= "LoginActivity";

	/** 返回 */
	private Button				bBack, bSigne;

	/** 获取验证码 */
	private Button				bGetAuthCode;
	/** 确认递交 */
	private Button				bOK;

	/** 手机号码 */
	private EditText			etPhoneNumber;
	/** 验证码 */
	private EditText			etAuthCode;

	/** 忘记密码 */
	private TextView			tvForgetPassword;

	private int					lockSeconds		= -1;

	private Handler				loginHandler	= new LoginHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		bBack = (Button) findViewById(R.id.button_login_back);
		bSigne = (Button) findViewById(R.id.button_login_signe);
		bBack.setOnClickListener(this);
		bSigne.setOnClickListener(this);

		bGetAuthCode = (Button) findViewById(R.id.button_login_getauthcode);
		bGetAuthCode.setOnClickListener(this);

		bOK = (Button) findViewById(R.id.button_login_ok);
		bOK.setOnClickListener(this);

		tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
		tvForgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvForgetPassword.setOnClickListener(this);

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
		// SharedPreferences setConfig =
		// getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
		// Activity.MODE_PRIVATE);
		etPhoneNumber.setText(Util.getPhoneNumber(this));

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.tv_forget_password:
			if (!isEmail(etPhoneNumber.getText().toString())) {
				Toast.makeText(LoginActivity.this, "请输入正确的邮箱地址...", Toast.LENGTH_LONG).show();
			} else {
				new GetPasswordThread(LoginActivity.this, loginHandler, etPhoneNumber.getText().toString()).start();
			}
			break;
		case R.id.button_login_getauthcode:
			/**
			 * 1. 获取手机验证码，手机号码不允许为空； 2. 60s以内不允许重复获取，需要60秒倒计时；
			 * */

			if (!isEmail(etPhoneNumber.getText().toString())) {
				Toast.makeText(LoginActivity.this, "请输入正确的邮箱地址...", Toast.LENGTH_LONG).show();
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

			if (6 > etAuthCode.getText().toString().length() || !isEmail(etPhoneNumber.getText().toString())) {
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
		case R.id.button_login_signe: {
			LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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

	private boolean isEmail(String email) {
		if (!email.contains("@") || !email.contains("."))
			return false;
		String str = "[a-zA-Z0-9_-]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
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
			case ComParams.HANDLER_RESULT_GET_PASSWORD:
				Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				if (0 < msg.arg1) {
					tvForgetPassword.setEnabled(false);
				}
				break;
			case ComParams.HANDLER_RESULT_VALIDATE:
				/**
				 * 登录成功，保存登录成功标识；
				 * 
				 * */
				data = msg.getData();
				if (1 == msg.arg1) {
					saveLoginStatus();
					Toast.makeText(LoginActivity.this, R.string.login_result_success, Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(LoginActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
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
