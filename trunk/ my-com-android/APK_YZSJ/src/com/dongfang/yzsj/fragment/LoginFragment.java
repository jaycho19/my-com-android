package com.dongfang.yzsj.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.LoginBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 登陆
 * 
 * @author dongfang
 * 
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
	public static final String TAG = "LoginFragment";

	private View btnLogin; // 登陆按钮
	private View tvGetAuthCode; // 获取验证码

	private EditText etUserName;// 用户名称
	private EditText etAuthCode;// 验证码

	private com.dongfang.view.ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		progDialog = com.dongfang.view.ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
		initView(view);
		return view;
	}

	private void initView(View view) {

		btnLogin = view.findViewById(R.id.login_btn_login);
		btnLogin.setOnClickListener(this);
		tvGetAuthCode = view.findViewById(R.id.login_tv_get_authcode);
		tvGetAuthCode.setOnClickListener(this);

		etUserName = (EditText) view.findViewById(R.id.login_et_userName);
		etAuthCode = (EditText) view.findViewById(R.id.login_et_authCode);

		// btnLogin.requestFocus();

	}

	@Override
	public void onStart() {
		super.onStart();
		btnLogin.requestFocus();
	}

	private boolean isPhoneNumber(String phoneNumber) {
		if (TextUtils.isEmpty(phoneNumber))
			return false;
		return true;
	}

	private boolean isAuthCode(String autoCode) {
		if (!TextUtils.isEmpty(autoCode) && autoCode.length() == 6)
			return true;
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:
			if (!isPhoneNumber(etUserName.getText().toString()) || !isAuthCode(etAuthCode.getText().toString()))
				return;

			String url = ComParams.HTTP_LOGIN + "obj.tel=" + etUserName.getText() + "&obj.verifyCode="
					+ etAuthCode.getText();
			ULog.i(TAG, url);
			
			// http://m.fortune-net.cn/user/user!phoneLogin.action?obj.tel=15631127974&obj.verifyCode=888222

			new HttpUtils().send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);
					progDialog.dismiss();
					try {

						LoginBean bean = new com.google.gson.Gson().fromJson(result, LoginBean.class);

						if (null != bean && bean.isSuccess() && !TextUtils.isEmpty(bean.getToken())) {
							// 保存token
							User.saveToken(getActivity(), bean.getToken());
							// 保存登陆状态
							// User.saveUserLoginStatu(getActivity(), true);

							User.savePhone(getActivity(), etUserName.getText().toString());

							Toast.makeText(getActivity(), "登陆成功！", Toast.LENGTH_LONG).show();

							if (null != onLoginAndVerify)
								onLoginAndVerify.login(true, etUserName.getText().toString());
						}
						else {
							// 保存token
							User.saveToken(getActivity(), "");
							// 获取失败
							// User.saveUserLoginStatu(getActivity(), false);
							User.savePhone(getActivity(), "");

							Toast.makeText(getActivity(), "登陆失败！", Toast.LENGTH_LONG).show();
							if (null != onLoginAndVerify)
								onLoginAndVerify.login(false, etUserName.getText().toString());
						}
					} catch (Exception e) {
						Toast.makeText(getActivity(), "JSON解析失败", Toast.LENGTH_LONG).show();
						if (null != onLoginAndVerify)
							onLoginAndVerify.login(false, etUserName.getText().toString());
						e.printStackTrace();
					}
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "onStart");
					progDialog.show();

				}

				@Override
				public void onFailure(Throwable error, String msg) {
					ULog.i(TAG, "onFailure");
					progDialog.dismiss();
				}
			});

			break;
		case R.id.login_tv_get_authcode:
			if (!isPhoneNumber(etUserName.getText().toString()))
				return;

			new HttpUtils().send(HttpRequest.HttpMethod.GET,
					ComParams.HTTP_AUTHCODE + "obj.tel=" + etUserName.getText(), new RequestCallBack<String>() {
						@Override
						public void onLoading(long total, long current) {
							ULog.d(TAG, "total = " + total + "; current = " + current);
						}

						@Override
						public void onSuccess(String result) {
							ULog.d(TAG, "onSuccess  --" + result);
							try {
								JSONObject obj = new JSONObject(result);
								if (obj.has("success") && obj.getBoolean("success")) {
									// 获取成功
									Toast.makeText(getActivity(), "验证码获取成功！", Toast.LENGTH_LONG).show();

									if (null != onLoginAndVerify)
										onLoginAndVerify.verify(true, etUserName.getText().toString());
								}
								else {
									Toast.makeText(getActivity(), "验证码获取失败！", Toast.LENGTH_LONG).show();

									if (null != onLoginAndVerify)
										onLoginAndVerify.verify(false, etUserName.getText().toString());
								}
							} catch (JSONException e) {
								Toast.makeText(getActivity(), "JSON解析失败", Toast.LENGTH_LONG).show();
								if (null != onLoginAndVerify)
									onLoginAndVerify.verify(false, etUserName.getText().toString());
								e.printStackTrace();
							}

							progDialog.dismiss();
						}

						@Override
						public void onStart() {
							ULog.i(TAG, "onStart");
							progDialog.show();

						}

						@Override
						public void onFailure(Throwable error, String msg) {
							ULog.i(TAG, "onFailure");
							progDialog.dismiss();
						}
					});

			break;
		}
	}

	private OnLoginAndVerify onLoginAndVerify;

	public OnLoginAndVerify getOnLoginAndVerify() {
		return onLoginAndVerify;
	}

	public void setOnLoginAndVerify(OnLoginAndVerify onLoginAndVerify) {
		this.onLoginAndVerify = onLoginAndVerify;
	}

	public interface OnLoginAndVerify {
		/**
		 * 登陆成功或者失败调用，
		 * 
		 * @param login
		 *            登陆是否成功
		 * @param phoneNumber
		 *            手机号码
		 */
		public abstract void login(boolean login, String phoneNumber);

		/**
		 * 获取验证码成功或者失败调用，
		 * 
		 * @param verify
		 *            获取验证码是否成功
		 * @param phoneNumber
		 *            手机号码
		 */
		public abstract void verify(boolean verify, String phoneNumber);
	}

}
