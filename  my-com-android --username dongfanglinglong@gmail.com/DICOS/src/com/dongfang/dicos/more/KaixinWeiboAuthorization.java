package com.dongfang.dicos.more;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.dongfang.dicos.R;
import com.dongfang.dicos.kaixin.Kaixin;
import com.dongfang.dicos.kaixin.KaixinAuthError;
import com.dongfang.dicos.kaixin.KaixinDialogListener;
import com.dongfang.dicos.kaixin.UtilKaiXin;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;

public class KaixinWeiboAuthorization extends Activity implements OnClickListener {

	public static final String		tag	= "KaixinWeiboAuthorization";

	/** 返回按钮 */
	private Button					bBack;

	/** 授权页面 */
	private WebView					wvKaiXin;

	/** 授权页面载入进度 */

	private ProgressBar				pbKaiXin;

	private MyKaixinDialogListener	mListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_tongbu_kaixin);

		mListener = new MyKaixinDialogListener();

		Intent intent = this.getIntent();
		String url = intent.getStringExtra(ComParams.KAIXIN_AUTH_BY_ACTIVITY_INTENT_URL_NAME);

		if (TextUtils.isEmpty(url))
			finish();

		bBack = (Button) findViewById(R.id.button_setting_tongbu_kaixin_back);
		bBack.setOnClickListener(this);

		pbKaiXin = (ProgressBar) findViewById(R.id.progressbar_setting_tongbu_kaixin);

		wvKaiXin = (WebView) findViewById(R.id.webView_setting_tongbu_kaixin);
		wvKaiXin.setVerticalScrollBarEnabled(false);
		wvKaiXin.setHorizontalScrollBarEnabled(false);
		wvKaiXin.getSettings().setJavaScriptEnabled(true);
		wvKaiXin.setWebViewClient(new KaixinWebViewClient());

		wvKaiXin.loadUrl(url);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_setting_tongbu_kaixin_back:
			finish();
			break;
		}
	}

	class KaixinWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			int b = mListener.onPageBegin(url);
			switch (b) {
			case KaixinDialogListener.PROCCESSED:
				finish();
				return true;
			case KaixinDialogListener.DIALOG_PROCCESS:
				return false;
			}
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			boolean b = mListener.onPageStart(url);
			if (b) {
				view.stopLoading();
				finish();
				return;
			}
			super.onPageStarted(view, url, favicon);
			if (!pbKaiXin.isShown())
				pbKaiXin.setVisibility(View.VISIBLE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			mListener.onReceivedError(errorCode, description, failingUrl);
			if (pbKaiXin.isShown())
				pbKaiXin.setVisibility(View.GONE);
			finish();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			mListener.onPageFinished(url);
			if (pbKaiXin.isShown())
				pbKaiXin.setVisibility(View.GONE);
		}

	}

	class MyKaixinDialogListener implements KaixinDialogListener {
		@Override
		public int onPageBegin(String url) {
			return KaixinDialogListener.DIALOG_PROCCESS;
		}

		@Override
		public void onPageFinished(String url) {
		}

		@Override
		public boolean onPageStart(String url) {
			return (KaixinDialogListener.PROCCESSED == parseUrl(url));
		}

		@Override
		public void onReceivedError(int errorCode, String description, String failingUrl) {
			Kaixin.getInstance().getKaixinAuthListener()
					.onAuthError(new KaixinAuthError(String.valueOf(errorCode), description, failingUrl));
		}

		private int parseUrl(String url) {
			if (url.startsWith(ComParams.KAIXIN_APP_URL)) {
				Bundle values = UtilKaiXin.parseUrl(url);
				String error = values.getString("error");// 授权服务器返回的错误代码
				if (error != null) {
					if (Kaixin.ACCESS_DENIED.equalsIgnoreCase(error)) {
						Kaixin.getInstance().getKaixinAuthListener().onAuthCancel(values);
					} else if (Kaixin.LOGIN_DENIED.equalsIgnoreCase(error)) {
						Kaixin.getInstance().getKaixinAuthListener().onAuthCancelLogin();
					} else {
						Kaixin.getInstance().getKaixinAuthListener()
								.onAuthError(new KaixinAuthError(error, error, url));
					}

					UtilKaiXin.clearCookies(KaixinWeiboAuthorization.this);

					Kaixin.getInstance().setAccessToken(null);
					Kaixin.getInstance().setRefreshToken(null);
					Kaixin.getInstance().setAccessExpires(0L);

				} else {
					this.authComplete(values, url);
				}
				return KaixinDialogListener.PROCCESSED;
			}
			return KaixinDialogListener.UNPROCCESS;
		}

		private void authComplete(Bundle values, String url) {
			Kaixin.getInstance();
			CookieSyncManager.getInstance().sync();
			String accessToken = values.getString(Kaixin.ACCESS_TOKEN);
			String refreshToken = values.getString(Kaixin.REFRESH_TOKEN);
			String expiresIn = values.getString(Kaixin.EXPIRES_IN);
			if (accessToken != null && refreshToken != null && expiresIn != null) {
				try {
					Kaixin.getInstance().setAccessToken(accessToken);
					Kaixin.getInstance().setRefreshToken(refreshToken);
					Kaixin.getInstance().setAccessExpiresIn(expiresIn);
					Kaixin.getInstance().updateStorage(KaixinWeiboAuthorization.this);
					Kaixin.getInstance().getKaixinAuthListener().onAuthComplete(values);
				} catch (Exception e) {
					Kaixin.getInstance().getKaixinAuthListener()
							.onAuthError(new KaixinAuthError(e.getClass().getName(), e.getMessage(), e.toString()));
				}
			} else {
				Kaixin.getInstance().getKaixinAuthListener()
						.onAuthError(new KaixinAuthError("错误", "授权服务器返回的信息不完整", url));
			}
		}
	}

}
