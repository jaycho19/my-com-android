package com.dongfang.view;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.params.ComParams;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 进度条对话框
 * 
 * @author chenwansong
 * 
 */
public class OrderDialog extends Dialog {
	public static final String TAG = "OrderDialog";

	private static OrderDialog orderDialog;

	public OrderDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 
	 * @param context
	 *            需要显示的文字 ；传入null或者""则不显示文字
	 * @param message
	 * @return
	 */
	public static OrderDialog show(final Context context, final OnOrderDialogBtnListener onlistener, boolean cancelable) {
		orderDialog = new OrderDialog(context, R.style.CustomProgressDialog);
		orderDialog.setContentView(R.layout.activity_order_dialog);
		orderDialog.setCancelable(cancelable);
		orderDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		final EditText etAuthCode = (EditText) orderDialog.findViewById(R.id.order_et_authCode);
		final EditText etPhoneNum = (EditText) orderDialog.findViewById(R.id.order_et_userName);
		TextView tvAuthCode = (TextView) orderDialog.findViewById(R.id.order_tv_get_authcode);
		tvAuthCode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (11 != etPhoneNum.getText().length()) {
					Toast.makeText(context, "请输入正确的手机号啊", Toast.LENGTH_LONG).show();
				}
				else {
					getAuthCode(context, etPhoneNum.getText().toString());
				}
			}
		});

		Button btnOK = (Button) orderDialog.findViewById(R.id.order_btn_ok);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (11 == etPhoneNum.getText().length() && 6 == etAuthCode.getText().length()) {
					orderDialog.dismiss();
					if (null != onlistener) {
						onlistener.ok(etPhoneNum.getText().toString(), etAuthCode.getText().toString());
					}
				}
				else {
					Toast.makeText(context, "请输入正确的手机号码和验证码", Toast.LENGTH_LONG).show();
				}
			}
		});

		Button btnCancel = (Button) orderDialog.findViewById(R.id.order_btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				orderDialog.dismiss();
				if (null != onlistener) {
					onlistener.cancel();
				}
			}
		});
		return orderDialog;
	}

	public static OrderDialog show(Context context, OnOrderDialogBtnListener onlistener) {
		return show(context, onlistener, true);
	}

	@Override
	public void cancel() {
		super.cancel();
		orderDialog = null;
	}

	@Override
	public void dismiss() {
		super.dismiss();
		orderDialog = null;
	}

	private static void getAuthCode(final Context context, String phone) {
		final com.dongfang.view.ProgressDialog progressDialog = ProgressDialog.show(context);

		new HttpUtils().send(HttpRequest.HttpMethod.GET, ComParams.HTTP_AUTHCODE + "obj.tel=" + phone,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(String result) {
						progressDialog.dismiss();

						ULog.d(TAG, "onSuccess  --" + result);
						try {
							JSONObject obj = new JSONObject(result);
							if (obj.has("success") && obj.getBoolean("success")) {
								// 获取成功
								Toast.makeText(context, "验证码获取成功！", Toast.LENGTH_LONG).show();
							}
							else {
								Toast.makeText(context, "验证码获取失败！", Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							Toast.makeText(context, "验证码获取失败！", Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
						ULog.i(TAG, "onStart");
						progressDialog.show();

					}

					@Override
					public void onFailure(Throwable error, String msg) {
						ULog.i(TAG, "onFailure");
						Toast.makeText(context, "验证码获取失败！", Toast.LENGTH_LONG).show();
						progressDialog.dismiss();

					}
				});

	}

	public interface OnOrderDialogBtnListener {
		public void ok(String phone, String authCode);

		public void cancel();
	}
}