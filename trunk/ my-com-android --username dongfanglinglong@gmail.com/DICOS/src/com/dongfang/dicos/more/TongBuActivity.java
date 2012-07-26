package com.dongfang.dicos.more;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dongfang.dicos.R;
import com.dongfang.dicos.kaixin.Kaixin;
import com.dongfang.dicos.kaixin.KaixinAuthError;
import com.dongfang.dicos.kaixin.KaixinAuthListener;
import com.dongfang.dicos.sina.AccessToken;
import com.dongfang.dicos.sina.DialogError;
import com.dongfang.dicos.sina.Weibo;
import com.dongfang.dicos.sina.WeiboDialogListener;
import com.dongfang.dicos.sina.WeiboException;
import com.dongfang.dicos.util.ComParams;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.view.SlipButton;
import com.dongfang.dicos.view.SlipButton.OnChangedListener;

/**
 * ͬ������
 * 
 * @author dongfang
 */
public class TongBuActivity extends Activity implements OnClickListener {
	public static final String	tag	= "TongBuActivity";

	/** ���ذ�ť */
	private Button				bBack;

	private RelativeLayout		rlSina;
	private RelativeLayout		rlKaiXin;

	// private TextView tvSina;
	// private TextView tvKaiXin;

	private Kaixin				kaixin;
	private Weibo				weibo;

	/** sina ������ť */
	private SlipButton			slipButton_Sina;
	/** kaixin ������ť */
	private SlipButton			slipButton_Kaixin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ULog.d(tag, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_tongbu);

		bBack = (Button) findViewById(R.id.button_setting_tongbu_back);
		bBack.setOnClickListener(this);

		rlSina = (RelativeLayout) findViewById(R.id.relativelayout_setting_tongbu_sina);
		rlSina.setOnClickListener(this);
		rlKaiXin = (RelativeLayout) findViewById(R.id.relativelayout_setting_tongbu_kaixin);
		rlKaiXin.setOnClickListener(this);

		// tvSina = (TextView) findViewById(R.id.textView_setting_tongbu_sina);
		// tvKaiXin = (TextView)
		// findViewById(R.id.textView_setting_tongbu_kaixin);

		slipButton_Sina = (SlipButton) findViewById(R.id.slipButton_setting_tongbu_sina);
		slipButton_Sina.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				if (CheckState) {
					tongbuToSina();
				} else {
					showSinaChkDialog(weibo);
				}
			}
		});
		slipButton_Kaixin = (SlipButton) findViewById(R.id.slipButton_setting_tongbu_kaixin);
		slipButton_Kaixin.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				if (CheckState) {
					tongbuToKaiXin();
				} else {
					showKaixinChkDialog(kaixin);
				}
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
		ULog.d(tag, "onStart");
		super.onStart();

		kaixin = Kaixin.getInstance();
		kaixin.loadStorage(this);
		weibo = Weibo.getInstance();
		weibo.loadStorage(this);

		if (weibo.isSessionValid()) {
			slipButton_Sina.setCheck(true);
		} else {
			slipButton_Sina.setCheck(false);
		}

		if (kaixin.isSessionValid()) {
			slipButton_Kaixin.setCheck(true);
		} else {
			slipButton_Kaixin.setCheck(false);
		}

		slipButton_Sina.invalidate();
		slipButton_Sina.destroyDrawingCache();

		slipButton_Kaixin.invalidate();
		slipButton_Kaixin.destroyDrawingCache();

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.relativelayout_setting_tongbu_sina:
			tongbuToSina();
			break;
		case R.id.relativelayout_setting_tongbu_kaixin:
			tongbuToKaiXin();
			break;
		case R.id.button_setting_tongbu_back:
			finish();
			break;
		}
	}

	/** ����΢����Ȩ */
	public void tongbuToSina() {
		Weibo weibo = Weibo.getInstance();
		weibo.loadStorage(this);
		if (weibo.isSessionValid()) {
			showSinaChkDialog(weibo);
		} else {
			weibo.setupConsumerConfig(ComParams.SINA_APP_KEY, ComParams.SINA_APP_SECRET);

			// Oauth2.0
			// ��ʽ��Ȩ��֤��ʽ
			weibo.setRedirectUrl(ComParams.SINA_APP_URL);
			// �˴��ص�ҳ����Ӧ���滻Ϊ��appkey��Ӧ��Ӧ�ûص�ҳ
			// ��Ӧ��Ӧ�ûص�ҳ���ڿ����ߵ�½����΢������ƽ̨֮��
			// �����ҵ�Ӧ��--Ӧ������--Ӧ����Ϣ--�߼���Ϣ--��Ȩ����--Ӧ�ûص�ҳ�������úͲ鿴��
			// Ӧ�ûص�ҳ����Ϊ��

			weibo.authorizeByActivity(TongBuActivity.this, new AuthDialogListener());
		}
	}

	/** ���Ľ�Ȩ */
	public void tongbuToKaiXin() {
		if (kaixin.isSessionValid()) {
			// Intent intent = new Intent(TongBuActivity.this,
			// ApiDemosActivity.class);
			// startActivity(intent);
			showKaixinChkDialog(kaixin);
		} else {

			String[] permissions = { "basic", "create_records" };
			kaixin.authorizeByActivity(TongBuActivity.this, permissions, new MyKaixinAuthListener());
		}
	}

	/** ȡ������ͬ���Ի��� */
	public void showSinaChkDialog(final Weibo weibo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�¿�ʿ").setIcon(R.drawable.ic_menu_notifications).setMessage("�Ƿ�Ҫֹͣͬ��������΢����")
				.setCancelable(false).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						weibo.clearStorage(TongBuActivity.this);
						slipButton_Sina.setCheck(false);
						slipButton_Sina.invalidate();
						slipButton_Sina.destroyDrawingCache();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	/** ȡ������ͬ���Ի��� */
	public void showKaixinChkDialog(final Kaixin kaixin) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�¿�ʿ").setIcon(R.drawable.ic_menu_notifications).setMessage("�Ƿ�Ҫֹͣͬ������������")
				.setCancelable(false).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						kaixin.clearStorage(TongBuActivity.this);
						slipButton_Kaixin.setCheck(false);
						slipButton_Kaixin.invalidate();
						slipButton_Kaixin.destroyDrawingCache();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	/** ���˽�Ȩ״̬�ӿ� */
	class AuthDialogListener implements WeiboDialogListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString(Weibo.TOKEN);
			String expires_in = values.getString(Weibo.EXPIRES);
			ULog.d(tag, "AuthDialogListener#onComplete " + Weibo.TOKEN + " : " + token + ";" + Weibo.EXPIRES + " : "
					+ expires_in);

			Weibo.getInstance().updateStorage(TongBuActivity.this, token, Long.valueOf(expires_in));

			// AccessToken accessToken = new AccessToken(token,
			// ComParams.SINA_APP_SECRET);
			// accessToken.setExpiresIn(Long.valueOf(expires_in));
			// weibo.setAccessToken(accessToken);
			// Intent intent = new Intent();
			// intent.setClass(AuthorizeActivity.this, TestActivity.class);
			// startActivity(intent);

			slipButton_Sina.setCheck(true);

		}

		@Override
		public void onError(DialogError e) {
			Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

	}

	/** ���Ľ�Ȩ״̬�ӿ� */
	class MyKaixinAuthListener implements KaixinAuthListener {
		@Override
		public void onAuthCancel(Bundle values) {
			ULog.d(tag, "onAuthCancel");
		}

		@Override
		public void onAuthCancelLogin() {
			ULog.d(tag, "onAuthCancelLogin");
		}

		@Override
		public void onAuthComplete(Bundle values) {
			ULog.d(tag, "onAuthComplete");
			String accessToken = values.getString(Kaixin.ACCESS_TOKEN);
			String refreshToken = values.getString(Kaixin.REFRESH_TOKEN);
			String expiresIn = values.getString(Kaixin.EXPIRES_IN);

			ULog.d(tag, Kaixin.ACCESS_TOKEN + " : " + accessToken + ";" + Kaixin.REFRESH_TOKEN + " : " + refreshToken
					+ ";" + Kaixin.EXPIRES_IN + " : " + expiresIn);

			slipButton_Kaixin.setCheck(true);

			// Kaixin kaixin = Kaixin.getInstance();
			// try {
			// String
			// response = kaixin.refreshAccessToken(AndroidExample.this, null);
			// } catch
			// (FileNotFoundException e) {
			// // TODO Auto-generated
			// catch block
			// e.printStackTrace();
			// } catch
			// (MalformedURLException e) {
			// // TODO Auto-generated
			// catch block
			// e.printStackTrace();
			// } catch
			// (IOException e) {
			// // TODO Auto-generated
			// catch block
			// e.printStackTrace();
			// }
			// Intent intent = new Intent(AndroidExample.this,
			// ApiDemosActivity.class);
			// startActivity(intent);
		}

		@Override
		public void onAuthError(KaixinAuthError kaixinAuthError) {
			ULog.d(tag, "onAuthError");
			Toast.makeText(TongBuActivity.this, kaixinAuthError.getErrorDescription(), Toast.LENGTH_SHORT).show();
		}
	}

}