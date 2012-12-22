package com.dongfang.dicos.kzmw;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.net.thread.GetPasswordThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * ͨ��ע�������ȡ����
 * 
 * @author dongfang
 */
public class GetPasswordByEmail extends Activity implements OnClickListener {
	public static final String	TAG	= GetPasswordByEmail.class.getName();

	/** ���� ,ע�� */
	private Button				bBack, bSigne;

	/** ȷ�ϵݽ�,ȡ�� */
	private Button				bOK, bCancel;

	/** �����ַ */
	private EditText			etEmail;

	
	private long				remainingTime;	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getpassword_by_email);

//		setRemainingTime(System.currentTimeMillis() + 70 * 1000);

		bBack = (Button) findViewById(R.id.getpwd_button_back);
		bSigne = (Button) findViewById(R.id.getpwd_button_signe);
		bOK = (Button) findViewById(R.id.getpwd_button_ok);
		bCancel = (Button) findViewById(R.id.getpwd_button_cancel);
		etEmail = (EditText) findViewById(R.id.getpwd_edittext_email);

		bBack.setOnClickListener(this);
		bSigne.setOnClickListener(this);
		bOK.setOnClickListener(this);
		bCancel.setOnClickListener(this);

		remainingTime = getRemainingTime();
		ULog.i(TAG, "remainingTime = " + remainingTime);
		ULog.i(TAG, "currentTimeMillis = " + System.currentTimeMillis());
		
		if (System.currentTimeMillis() + 2000 < remainingTime) {
			handler.sendEmptyMessage(100);
		}

	}
	
	private long getRemainingTime() {
		SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME, Activity.MODE_PRIVATE);
		return setConfig.getLong("Remaining_Time", 0L);
	}

	private void setRemainingTime(Long time) {
		SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME, Activity.MODE_PRIVATE);
		setConfig.edit().putLong("Remaining_Time", time).commit();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getpwd_button_back:
			finish();
			break;
		case R.id.getpwd_button_signe:
			startActivity(new Intent(this, RegisterActivity.class));
			finish();
			break;
		case R.id.getpwd_button_ok:
			if (!Util.isEmail(etEmail.getText().toString())) {
				Toast.makeText(this, "��������ȷ�������ַ...", Toast.LENGTH_LONG).show();
			} else {
				bOK.setEnabled(false);
				remainingTime = System.currentTimeMillis() + 5 * 60 * 1000;
				setRemainingTime(remainingTime);
				handler.sendEmptyMessage(100);
				new GetPasswordThread(this, handler, etEmail.getText().toString()).start();
			}
			break;
		case R.id.getpwd_button_cancel:
			etEmail.setText("");
			break;
		default:
			break;
		}

	}

	private Handler handler = new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_GET_PASSWORD:
				if (0 < msg.arg1){
					new AlertDialog.Builder(GetPasswordByEmail.this)
					.setTitle("�����ѷ�����ע�����䣬��ע����գ�")
					.setPositiveButton("ȷ��", null)
					.setNegativeButton("ȡ��", null)
					.show();
					bOK.setEnabled(false);
					this.sendEmptyMessage(100);
				}
				else{
					removeMessages(100);
					bOK.setEnabled(true);
					bOK.setText("ȷ�� ");
					new AlertDialog.Builder(GetPasswordByEmail.this)
					.setTitle("�������ַ�����δ��ע�ᣬ��������ȷ�������ַ��")
					.setPositiveButton("ȷ��", null)
					.setNegativeButton("ȡ��", null)
					.show();
				}
				// Toast.makeText(GetPasswordByEmail.this, (String) msg.obj, Toast.LENGTH_LONG).show();
				break;
			case 100:{
				int time = (int)(remainingTime - System.currentTimeMillis()) / 1000;
				if  ( time  > 0){
					bOK.setEnabled(false);
					int min = time / 60;
					int sec = time - min * 60;
					
					String s = sec < 10 ? "0"+sec : ""+sec;
					
					bOK.setText("ȷ�� "  +  ( min > 0 ? "(0"+ min +":"+ s +")" : "("+ s +")"));
					sendEmptyMessageDelayed(100, 1000);
				}
				else{
					bOK.setEnabled(true);
					bOK.setText("ȷ�� ");
				}
			}
				break;
				
			default:
				break;
			}
		}
		
	};
	
	
}
