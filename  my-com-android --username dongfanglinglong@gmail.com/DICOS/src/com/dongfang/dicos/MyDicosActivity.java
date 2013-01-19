package com.dongfang.dicos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.dicos.kzmw.LoginActivity;
import com.dongfang.dicos.mydicos.SignHistoryAdapter;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.SignHistoryThread;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 我的德克士
 * 
 * @author dongfang
 * */
public class MyDicosActivity extends Activity implements OnClickListener {

	public static final String	tag	= "MyDicosActivity";

	private TextView			my_dicos_title;

	/** 顶部菜单右侧进度条 */
	private ProgressBar			progressBar;

	/** 签到按钮 */
	private Button				bSigne;
	private Button				bLogin;

	private ListView			lvSignHistory;

	private SignHistoryAdapter	adapter;

	private MyDICOSHandler		handler;

	private AlertDialog			dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydicos);
		handler = new MyDICOSHandler();

		my_dicos_title = (TextView) findViewById(R.id.my_dicos_title);

		progressBar = (ProgressBar) findViewById(R.id.progressbar_mydicos);

		bSigne = (Button) findViewById(R.id.button_mydicos_signein);
		bSigne.setOnClickListener(this);

		bLogin = (Button) findViewById(R.id.button_mydicos_login);
		bLogin.setOnClickListener(this);

		lvSignHistory = (ListView) findViewById(R.id.listview_mydicos_signein);
		adapter = new SignHistoryAdapter(this, handler);
		lvSignHistory.setAdapter(adapter);

		dialog = new AlertDialog.Builder(this).setTitle("请先登录").setIcon(R.drawable.ic_menu_notifications)// .setMessage("请先登录")
				.setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(MyDicosActivity.this, LoginActivity.class);
						startActivity(intent);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create();

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (Util.isLogin(this) && !TextUtils.isEmpty(Util.getNickName(this))) {
			my_dicos_title.setText(Util.getNickName(this));
		}
		else {
			my_dicos_title.setText("");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (Util.isLogin(this)) {
			bLogin.setVisibility(View.GONE);
			if (!progressBar.isShown())
				progressBar.setVisibility(View.VISIBLE);
			new SignHistoryThread(this, handler, Util.getPhoneNumber(this)).start();
		}
		else {
			bLogin.setVisibility(View.VISIBLE);
			my_dicos_title.setText("");
			
			if (lvSignHistory != null && adapter != null){
				adapter.setArray(new String[]{});
				adapter.notifyDataSetChanged();
			}
			
			
			if (!dialog.isShowing())
				dialog.show();
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.button_mydicos_login:
			if (!Util.isNetworkAvailable(this)) {
				Util.showDialogSetNetWork(this);
			}
			else if (Util.isLogin(this)) {
				Toast.makeText(this, "您已登录", Toast.LENGTH_LONG).show();
			}
			else {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.button_mydicos_signein:
			if (!Util.isLogin(MyDicosActivity.this)) {
				Util.showDialogLogin(MyDicosActivity.this);
			}
			else {
				intent = new Intent(MyDicosActivity.this, StoreSearchActivity.class);
				intent.putExtra("visibility", true);
				startActivity(intent);
			}
			break;
		}
	}

	class MyDICOSHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			ULog.d(tag, "handleMessage msg.what = " + msg.what);
			Bundle data;
			switch (msg.what) {
			case ComParams.HANDLER_RESULT_SIGNHISTORY:
				data = msg.getData();
				if (!TextUtils.isEmpty(data.getString(Actions.ACTIONS_KEY_ACT))
						&& data.getString(Actions.ACTIONS_KEY_ACT).equalsIgnoreCase(Actions.ACTIONS_TYPE_SIGNHISTORY)) {
					for (String s : data.getStringArray(Actions.ACTIONS_KEY_DATA)) {
						ULog.d(tag, s);
					}

					adapter.setArray(data.getStringArray(Actions.ACTIONS_KEY_DATA));
					adapter.notifyDataSetChanged();

					if (progressBar.isShown())
						progressBar.setVisibility(View.GONE);

				}

				// JSONObject json = new JSONObject();
				// try {
				// json.put(Actions.ACTIONS_KEY_NAME, "豫园店");
				// json.put(Actions.ACTIONS_KEY_TIME, "2012年7月16日21:18:20");
				// } catch (JSONException e) {
				// ULog.d(tag, e.toString());
				// }
				// String s = json.toString();
				// String[] as = new String[] { s, s, s, s, s };
				//
				// adapter.setArray(as);
				// adapter.notifyDataSetChanged();
				//
				// if (progressBar.isShown())
				// progressBar.setVisibility(View.GONE);
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
