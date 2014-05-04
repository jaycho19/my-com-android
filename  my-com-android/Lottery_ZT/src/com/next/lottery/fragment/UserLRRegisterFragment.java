package com.next.lottery.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MainActivity;
import com.next.lottery.R;
import com.next.lottery.UserRegisterActivity;
import com.next.lottery.beans.UserBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.view.SlipButton;
import com.next.lottery.view.SlipButton.OnChangedListener;

public class UserLRRegisterFragment extends BaseFragment {

	@ViewInject(R.id.fragment_user_register_ll_userid)
	private LinearLayout llUserId; // 用户名布局
	@ViewInject(R.id.fragment_user_register_ll_psw)
	private LinearLayout llPsw; // 密码布局

	private TextView tvTitle;

	private ProgressDialog progDialog;

	@ViewInject(R.id.fragment_usr_register_account_et)
	private EditText etUsername;
	@ViewInject(R.id.fragment_usr_register_password_et)
	private EditText etPassword;
	@ViewInject(R.id.fragment_usr_register_slip)
	private SlipButton slipBtn;
	@ViewInject(R.id.fragment_usr_register_ok_btn)
	private Button btnOk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_register, container, false);
		ViewUtils.inject(this, view);
		tvTitle = (TextView) view.findViewById(R.id.app_top_title_tv_centre);
		initView();
		return view;
	}

	private void initView() {
		tvTitle.setText("注册");
		progDialog = ProgressDialog.show(getActivity());

		slipBtn.setCheck(true);
		slipBtn.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				if (CheckState) {
					etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				else {
					etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}
			}
		});

		etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				llPsw.setSelected(hasFocus);
			}
		});
		etUsername.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				llUserId.setSelected(hasFocus);
			}
		});

	}

	@OnClick({ R.id.app_top_title_iv_left, R.id.app_top_title_iv_rigth, R.id.fragment_usr_register_ok_btn })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			((Activity) getActivity()).finish();
			break;
		case R.id.app_top_title_iv_rigth:
			((UserRegisterActivity) getActivity()).showRight();
			break;
		case R.id.fragment_usr_register_ok_btn:
			doRegister();
			break;

		default:
			break;
		}
	}

	/**
	 * 登录
	 */
	private void doRegister() {
		String url = HttpActions.register(etUsername.getText().toString().trim(), etPassword.getText().toString()
				.trim());
		ULog.d("url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				progDialog.dismiss();
				ULog.d(responseInfo.result);

				UserBean bean = new Gson().fromJson(responseInfo.result, UserBean.class);
				if (null != bean && bean.getCode() == 0) {
					// 保存token
					Toast.makeText(getActivity(), "注册成功！", Toast.LENGTH_LONG).show();

					MainActivity.changeTab = 4;
					((Activity) getActivity()).finish();

				}
				else {
					Toast.makeText(getActivity(), "注册失败！", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

}
