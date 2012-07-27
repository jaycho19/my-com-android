package com.dongfang.dicos.kzmw;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.kaixin.Constant;
import com.dongfang.dicos.kaixin.Kaixin;
import com.dongfang.dicos.kaixin.PostRecordTask;
import com.dongfang.dicos.more.TongBuActivity;
import com.dongfang.dicos.net.Actions;
import com.dongfang.dicos.net.thread.SignThread;
import com.dongfang.dicos.sina.AsyncWeiboRunner;
import com.dongfang.dicos.sina.AsyncWeiboRunner.RequestListener;
import com.dongfang.dicos.sina.UtilSina;
import com.dongfang.dicos.sina.Weibo;
import com.dongfang.dicos.sina.WeiboException;
import com.dongfang.dicos.sina.WeiboParameters;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;

/**
 * 签到
 * 
 * @author dongfang
 * */
public class SigneInActivity extends Activity implements OnClickListener, CheckBox.OnCheckedChangeListener,
		RequestListener {

	public static final String	tag			= "SigneInActivity";
	private static final String	TYPE_SINA	= "sina";
	private static final String	TYPE_KAIXIN	= "kaixin";

	/** 返回 */
	private Button				bBack;

	/** 确认按钮 */
	private Button				bOK;

	/** 同步到新浪 */
	private CheckBox			cbSina;
	/** 同步到开心 */
	private CheckBox			cbKaiXing;

	private EditText			etMessage;
	private ProgressBar			progressbar;

	private Weibo				weibo;
	private Kaixin				kaixin;

	private SigneInHandler		handler;

	private String				lon			= "";
	private String				lat			= "";
	private String				id			= "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signein);

		handler = new SigneInHandler();

		bBack = (Button) findViewById(R.id.button_signein_cancel);
		bBack.setOnClickListener(this);

		bOK = (Button) findViewById(R.id.button_signein_ok);
		bOK.setOnClickListener(this);

		cbSina = (CheckBox) findViewById(R.id.checkBox_signein_sina);
		cbSina.setOnCheckedChangeListener(this);

		cbKaiXing = (CheckBox) findViewById(R.id.checkBox_signein_kaixing);
		cbKaiXing.setOnCheckedChangeListener(this);

		etMessage = (EditText) findViewById(R.id.editText_signein_msg);
		progressbar = (ProgressBar) findViewById(R.id.progressbar_signein);

		Intent intent = getIntent();
		if (!TextUtils.isEmpty(intent.getStringExtra("store_name"))) {
			etMessage.setText("我在" + intent.getStringExtra("store_name"));
			lon = intent.getStringExtra("x");
			lat = intent.getStringExtra("y");
			id = intent.getStringExtra("id");
			ULog.d(tag, "lon = " + lon + ",lat = " + lat + ",id = " + id);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		weibo = Weibo.getInstance();
		weibo.loadStorage(this);

		kaixin = Kaixin.getInstance();
		kaixin.loadStorage(this);

		if (weibo.isSessionValid()) {
			cbSina.setChecked(true);
		} else {
			cbSina.setChecked(false);
		}
		if (kaixin.isSessionValid()) {
			cbKaiXing.setChecked(true);
		} else {
			cbKaiXing.setChecked(false);
		}

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_login_getauthcode:
			break;
		case R.id.button_signein_ok:
			if (!progressbar.isShown())
				progressbar.setVisibility(View.VISIBLE);
			bOK.setClickable(false);
			handler.sendEmptyMessage(ComParams.HANDLER_SIGNE_IN);

			break;
		case R.id.button_signein_cancel:
			finish();
			break;
		}
	}

	/**
	 * 1. 判断授权是否成功 </br> 2. 授权未成功跳转到授权页面 </br> 3. 授权操作之后返回，检测授权是否成功</br> 4.
	 * 授权不成功，不允许checked；授权成功，运行默认进行checked；</br>
	 * */

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		ULog.d(tag, "onClick v.getId() = " + buttonView.getId() + " isChecked  = " + isChecked);
		switch (buttonView.getId()) {
		case R.id.checkBox_signein_sina:
			if (isChecked && !weibo.isSessionValid()) {
				showDialog(TYPE_SINA);
				buttonView.setChecked(false);
			}
			break;
		case R.id.checkBox_signein_kaixing:
			if (isChecked && !kaixin.isSessionValid()) {
				buttonView.setChecked(false);
				showDialog(TYPE_KAIXIN);
			}
			break;
		}
	}

	/** 取消新浪同步对话框 */
	public void showDialog(String type) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("德克士").setIcon(R.drawable.ic_menu_notifications).setCancelable(false);

		if (TYPE_SINA.equals(type)) {
			builder.setMessage("是否要同步到新浪微博？");
		} else if (TYPE_KAIXIN.equals(type)) {
			builder.setMessage("是否要同步到开心网？");
		}

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(SigneInActivity.this, TongBuActivity.class);
				startActivity(intent);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		}).show();
	}

	/** 分享到新浪微博 */
	private void shareToSina(String msg) throws MalformedURLException, IOException, WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", ComParams.SINA_APP_KEY);
		bundle.add("status", msg);
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		// weiboRunner.request(this, url, bundle, UtilSina.HTTPMETHOD_POST,
		// this);
		weiboRunner.post(this, url, msg, ComParams.SINA_APP_KEY, weibo.getAccessToken().getToken(), this);
		// (this, url, bundle, UtilSina.HTTPMETHOD_POST, this);
	}

	private String update(Weibo weibo, String source, String status, String lon, String lat)
			throws MalformedURLException, IOException, WeiboException {
		WeiboParameters bundle = new WeiboParameters();
		bundle.add("source", source);
		bundle.add("status", status);
		if (!TextUtils.isEmpty(lon)) {
			bundle.add("lon", lon);
		}
		if (!TextUtils.isEmpty(lat)) {
			bundle.add("lat", lat);
		}
		String rlt = "";
		String url = Weibo.SERVER + "statuses/update.json";
		AsyncWeiboRunner weiboRunner = new AsyncWeiboRunner(weibo);
		weiboRunner.request(this, url, bundle, UtilSina.HTTPMETHOD_POST, this);
		return rlt;
	}

	// private String update(String msg) throws WeiboException {
	// WeiboParameters bundle = new WeiboParameters();
	// bundle.add("source", ComParams.SINA_APP_KEY);
	// bundle.add("status", msg);
	// String rlt = "";
	// String url = Weibo.SERVER + "statuses/update.json";
	// rlt = weibo.request(this, url, bundle, UtilSina.HTTPMETHOD_POST,
	// weibo.getAccessToken());
	// ULog.d(tag, "update " + rlt);
	// return rlt;
	// }

	class SigneInHandler extends Handler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case Constant.RESULT_OK:
			case Constant.RESULT_FAILED_NETWORK_ERR:
			case Constant.RESULT_FAILED_REQUEST_ERR:
			case Constant.RESULT_POST_RECORD_OK:
			case Constant.RESULT_POST_RECORD_FAILED:
			case Constant.RESULT_FAILED_MALFORMEDURL_ERR:
			case Constant.RESULT_FAILED:
				ULog.d(tag, "kaixing result = " + msg.what);
				break;
			case ComParams.HANDLER_RESULT_SIGN:
				Bundle data = msg.getData();
				if (Actions.ACTIONS_TYPE_SIGN.equalsIgnoreCase(data.getString(Actions.ACTIONS_KEY_ACT))
						&& "1".equals(data.get(Actions.ACTIONS_KEY_RESULT))) {
					Toast.makeText(SigneInActivity.this, "签到成功", Toast.LENGTH_LONG).show();
					finish();
				} else {
					Toast.makeText(SigneInActivity.this, "签到失败", Toast.LENGTH_LONG).show();
				}

				
				progressbar.setVisibility(View.GONE);
				bOK.setClickable(true);
				break;
			case ComParams.HANDLER_SIGNE_IN:
				String sMsg = etMessage.getText().toString().trim();
				sMsg = TextUtils.isEmpty(sMsg) ? etMessage.getHint().toString() : sMsg;
				new SignThread(SigneInActivity.this, handler, id, Util.getPhoneNumber(SigneInActivity.this)).start();

				if (cbKaiXing.isChecked()) {
					PostRecordTask getDataTask = new PostRecordTask();
					getDataTask.execute(kaixin, handler, sMsg, null, SigneInActivity.this);
				}
				if (cbSina.isChecked()) {

					try {
						// shareToSina(sMsg);
						update(weibo, Weibo.getAppKey(), sMsg, "", "");
					} catch (WeiboException e) {
						ULog.e(tag, e.toString());
						e.printStackTrace();
					} catch (MalformedURLException e) {
						ULog.e(tag, e.toString());
						e.printStackTrace();
					} catch (IOException e) {
						ULog.e(tag, e.toString());
						e.printStackTrace();
					}
				}
				break;
			}
		}

	}

	@Override
	public void onComplete(String response) {
		ULog.d(tag, "onComplete " + response);
		// Toast.makeText(this, "新浪微博分享成功", Toast.LENGTH_LONG).show();
		// progressbar.setVisibility(View.GONE);

	}

	@Override
	public void onIOException(IOException e) {
		ULog.d(tag, "onIOException " + e.toString());
		// progressbar.setVisibility(View.GONE);

	}

	@Override
	public void onError(WeiboException e) {
		ULog.d(tag, "onError " + e.toString());
		// Toast.makeText(this, "新浪微博分享失败", Toast.LENGTH_LONG).show();
		// progressbar.setVisibility(View.GONE);
	}

}
