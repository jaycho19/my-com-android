package com.dongfang.dicos.kzmw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
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
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.thread.RegisterTask;
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

	private EditText[]			etDyk							= new EditText[5];					// etDyk1,
																									// etDyk2,
																									// etDyk3,
																									// etDyk4,
																									// etDyk5;

	private Button				addDyk;

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

		etDyk[0] = (EditText) findViewById(R.id.editview_register_dyk_1);
		etDyk[1] = (EditText) findViewById(R.id.editview_register_dyk_2);
		etDyk[2] = (EditText) findViewById(R.id.editview_register_dyk_3);
		etDyk[3] = (EditText) findViewById(R.id.editview_register_dyk_4);
		etDyk[4] = (EditText) findViewById(R.id.editview_register_dyk_5);

		addDyk = (Button) findViewById(R.id.button_register_add_dyk);
		addDyk.setOnClickListener(this);

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
				}
				else {
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
				}
				else {
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
				}
				else {
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
		// if (isbool[0] && isbool[1] && isbool[2] && isbool[3]) {
		// bOK.setClickable(true);
		// bOK.setTextColor(0xFF000000);
		//
		// }
		// else {
		// bOK.setClickable(false);
		// bOK.setTextColor(0xFF808080);
		// }
	}

	private boolean isEmail(String email) {
		if (!email.contains("@") || !email.contains("."))
			return false;
		String str = "[a-zA-Z0-9_-.]+@\\w+\\.[a-z]+(\\.[a-z]+)?";
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
		// etPhoneNumber.setText(Util.getPhoneNumber(this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_register_add_dyk: {
			for (int i = 0; i < etDyk.length; i++) {
				if (!etDyk[i].isShown()) {
					etDyk[i].setVisibility(View.VISIBLE);
					return;
				}
			}
		}
			break;
		case R.id.button_register_back:
			finish();
			break;
		case R.id.button_register_ok:
			if (!isbool[0]) {
				Toast.makeText(RegisterActivity.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
			}
			else if (!isbool[1]) {
				Toast.makeText(RegisterActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
			}
			else if (!isbool[2]) {
				Toast.makeText(RegisterActivity.this, "请输入密码6至15位密码", Toast.LENGTH_SHORT).show();
			}
			else if (!isbool[3]) {
				Toast.makeText(RegisterActivity.this, "两次密码输入不同，请重新输入", Toast.LENGTH_SHORT).show();
			}
			else {
				String[] cno = new String[etDyk.length];
				for (int i = 0; i < etDyk.length; i++) {
					cno[i] = etDyk[i].getText().toString();
				}

				new RegisterTask(RegisterActivity.this, registerhandler, etPhoneNumber.getText().toString(), etPassword
						.getText().toString(), etNickname.getText().toString(), cno).execute("");

			}

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
				// if (isMobileNO(str) || isEmail(str)) {
				if (isEmail(str)) {
					tvPhoneNumber.setTextColor(0xFF5C9200);
					isbool[0] = true;

				}
				else {
					isbool[0] = false;
					tvPhoneNumber.setTextColor(0xFF000000);
				}
				initButtonOK();
			}
				break;
			case ComParams.HANDLER_RESULT_REGISTER: {
				if (0 > msg.arg1) {
					Toast.makeText(RegisterActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				}
				else {
					Util.setNickName(RegisterActivity.this, etNickname.getText().toString());
					Toast.makeText(RegisterActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
					finish();
				}

			}
				break;
			default:
				break;
			}
		}
	}

}
