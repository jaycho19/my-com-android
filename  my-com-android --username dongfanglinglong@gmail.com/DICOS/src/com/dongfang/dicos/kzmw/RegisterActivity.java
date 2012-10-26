package com.dongfang.dicos.kzmw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 注册
 * 
 * @author dongfang
 * */
public class RegisterActivity extends Activity implements OnClickListener {

	public static final String	tag								= "RegisterActivity";
	public static final int		CHICK_PHONENUMBEREMAIL_IS_OK	= 1000;

	/** 返回 */
	private Button				bBack;

	/** 确认递交 */
	private Button				bOK;

	/** 手机号码 */
	private EditText			etPhoneNumber;
	/** 昵称 */
	private EditText			etNickname;
	/** 密码 */
	private EditText			etPassword;
	/** 确认密码 */
	private EditText			etPasswordAgian;

	private TextView			tvPhoneNumber, tvNickname, tvPassword, tvPasswordAgain;

	private boolean[]			isbool							= { false, false, false, false };

	private Handler				registerhandler					= new RegisterHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		bBack = (Button) findViewById(R.id.button_register_back);
		bBack.setOnClickListener(this);
		bOK = (Button) findViewById(R.id.button_register_ok);
		bOK.setOnClickListener(this);
		initButtonOK();

		tvPhoneNumber = (TextView) findViewById(R.id.textview_register_phonenumber);
		tvNickname = (TextView) findViewById(R.id.textview_register_nickname);
		tvPassword = (TextView) findViewById(R.id.textview_register_pwd);
		tvPasswordAgain = (TextView) findViewById(R.id.textview_register_pwd_again);

		etPhoneNumber = (EditText) findViewById(R.id.editview_register_phonenumber);
		etNickname = (EditText) findViewById(R.id.editview_register_nickname);
		etPassword = (EditText) findViewById(R.id.editview_register_pwd);
		etPasswordAgian = (EditText) findViewById(R.id.editview_register_pwd_again);
		initEditText();

	}

	private void initEditText() {
		// etPhoneNumber.addTextChangedListener(new TextWatcher() {
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before, int
		// count) {
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// registerhandler.sendEmptyMessage(CHICK_PHONENUMBEREMAIL_IS_OK);
		// }
		// });

		etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				ULog.e(tag, "setOnFocusChangeListener hasFocus = " + hasFocus);
				if (!hasFocus) {
					registerhandler.sendEmptyMessage(CHICK_PHONENUMBEREMAIL_IS_OK);
				}
			}
		});

		etNickname.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (etNickname.getText().length() > 0) {
					tvNickname.setTextColor(0xFF5C9200);
					isbool[1] = true;
				} else {
					isbool[1] = false;
					tvNickname.setTextColor(0xFF000000);
				}
				initButtonOK();
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
		etPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = etPassword.getText().toString();
				if (str.length() > 5) {
					tvPassword.setTextColor(0xFF5C9200);
					isbool[2] = true;
					if (str.equals(etPasswordAgian.getText().toString())) {
						tvPasswordAgain.setTextColor(0xFF5C9200);
						isbool[3] = true;
					}
				} else {
					isbool[2] = false;
					tvPassword.setTextColor(0xFF000000);
				}

				initButtonOK();
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
		etPasswordAgian.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String str = etPasswordAgian.getText().toString();
				if (str.length() > 5 && str.equals(etPassword.getText().toString())) {
					tvPasswordAgain.setTextColor(0xFF5C9200);
					isbool[3] = true;
				} else {
					isbool[3] = false;
					tvPasswordAgain.setTextColor(0xFF000000);
				}
				initButtonOK();
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

	private void initButtonOK() {
		if (isbool[0] && isbool[1] && isbool[2] && isbool[3]) {
			bOK.setClickable(true);
			bOK.setTextColor(0xFF000000);

		} else {
			bOK.setClickable(false);
			bOK.setTextColor(0xFF808080);
		}
	}

	private boolean isEmail(String email) {
		if (!email.contains("@") || !email.contains("."))
			return false;
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	private boolean isMobileNO(String mobiles) {
		if (mobiles.length() != 11)
			return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
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
		etPhoneNumber.setText(Util.getPhoneNumber(this));
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_register_back:
			finish();
			break;
		case R.id.button_register_ok:
			break;
		}
	}

	class RegisterHandler extends Handler {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHICK_PHONENUMBEREMAIL_IS_OK: {
				String str = etPhoneNumber.getText().toString();
				if (isMobileNO(str) || isEmail(str)) {
					tvPhoneNumber.setTextColor(0xFF5C9200);
					isbool[0] = true;

				} else {
					isbool[0] = false;
					tvPhoneNumber.setTextColor(0xFF000000);
				}
				initButtonOK();
			}
				break;

			default:
				break;
			}
		}
	}

}
