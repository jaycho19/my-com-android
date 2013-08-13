package com.dongfang.yzsj.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.HomeBean;
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

	private ProgressDialog progDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {

		progDialog = new ProgressDialog(getActivity());

		btnLogin = view.findViewById(R.id.login_btn_login);
		btnLogin.setOnClickListener(this);
		tvGetAuthCode = view.findViewById(R.id.login_tv_get_authcode);
		tvGetAuthCode.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login:
			new HttpUtils().send(HttpRequest.HttpMethod.GET, "http://m.fortune-net.cn/page/hbMobile/?jsonFormat=true", new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);
					HomeBean bean = new com.google.gson.Gson().fromJson(result, HomeBean.class);
					bean.toLog();
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
		case R.id.login_tv_get_authcode:
			break;
		}
	}
}
