package com.dongfang.dicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.more.CityListActivity;
import com.dongfang.dicos.more.FeedBackActivity;
import com.dongfang.dicos.more.TongBuActivity;
import com.dongfang.dicos.more.TuiSongActivity;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.LogoutThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * ����ҳ��
 * 
 * @author dongfang
 * */
public class MoreActivity extends Activity implements OnClickListener {

	public static final String	tag	= "MoreActivity";
	/** ͬ������ */
	private Button				bTongbu;
	/** �������� */
	private Button				bTuisong;
	/** �����л� */
	private Button				bChengshi;

	/** ������� */
	private Button				bFeedback;

	/** �˳��˺� */
	private Button				bExit;

	/** ���� */
	private Button				bBack;

	private MoreHandler			handler;

	private TextView			tvBanben;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		bTongbu = (Button) findViewById(R.id.button_setting_tongbu);
		bTongbu.setOnClickListener(this);

		bTuisong = (Button) findViewById(R.id.button_setting_tuisong);
		bTuisong.setOnClickListener(this);

		bChengshi = (Button) findViewById(R.id.button_setting_chengshi);
		bChengshi.setOnClickListener(this);

		bFeedback = (Button) findViewById(R.id.button_setting_feedback);
		bFeedback.setOnClickListener(this);

		bExit = (Button) findViewById(R.id.button_setting_exit);
		bExit.setOnClickListener(this);

		bBack = (Button) findViewById(R.id.button_setting_back);
		bBack.setOnClickListener(this);

		tvBanben = (TextView) findViewById(R.id.textview_setting_banben);

		try {
			PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			String versionName = pi.versionName;
			if (!TextUtils.isEmpty(versionName))
				tvBanben.setText("(�汾��   " + versionName + "  )");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		handler = new MoreHandler();

	}

	/** �˳��Ի��� */
	public void showSystemExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�¿�ʿ").setIcon(R.drawable.ic_menu_notifications).setMessage("�˳�֮�������µ�¼���Ƿ���Ҫ�˳��¿�ʿ��").setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						/** �����¼״̬ */
						SharedPreferences setConfig = getSharedPreferences(ComParams.SHAREDPREFERENCES_FILE_NAME,
								Activity.MODE_PRIVATE);
						new LogoutThread(MoreActivity.this, handler, setConfig.getString(
								ComParams.SHAREDPREFERENCES_PHONE_NUMBER, "")).start();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		Intent intent;
		switch (v.getId()) {
		case R.id.button_setting_tongbu:
			intent = new Intent(MoreActivity.this, TongBuActivity.class);
			startActivity(intent);
			break;
		case R.id.button_setting_tuisong:
			intent = new Intent(MoreActivity.this, TuiSongActivity.class);
			startActivity(intent);
			break;
		case R.id.button_setting_chengshi:
			intent = new Intent(MoreActivity.this, CityListActivity.class);
			startActivity(intent);
			break;
		case R.id.button_setting_feedback:
			intent = new Intent(MoreActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.button_setting_exit: {
			if (Util.isLogin(this)) {
				showSystemExitDialog();
			} else {
				Toast.makeText(MoreActivity.this, "����δ��¼...", Toast.LENGTH_LONG).show();
			}

			// bExit.setClickable(false);

		}
			break;
		case R.id.button_setting_back:
			finish();
			break;
		}
	}

	class MoreHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_LOGOUT: {
				data = msg.getData();

				if (data.getString(Actions.ACTIONS_KEY_ACT).equalsIgnoreCase(Actions.ACTIONS_TYPE_LOGOUT)
						&& data.getString(Actions.ACTIONS_KEY_RESULT).equals("1")) {
					Util.setLoginStatus(MoreActivity.this, false);
					Toast.makeText(MoreActivity.this, "�˳��ɹ�...", Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(MoreActivity.this, "�˳�ʧ��...", Toast.LENGTH_LONG).show();
				}

				bExit.setClickable(true);

			}
				break;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Util.showExitDialog(this);

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}
