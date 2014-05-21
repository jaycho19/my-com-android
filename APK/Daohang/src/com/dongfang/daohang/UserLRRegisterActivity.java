package com.dongfang.daohang;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dongfang.daohang.beans.UserBean;
import com.dongfang.daohang.net.HttpActions;
import com.dongfang.utils.DFException;
import com.dongfang.utils.JsonAnalytic;
import com.dongfang.utils.ULog;
import com.dongfang.utils.User;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserLRRegisterActivity extends BaseActivity {

	@ViewInject(R.id.activity_userlr_et_name)
	private EditText etName;
	@ViewInject(R.id.activity_userlr_et_psw)
	private EditText etPsw;
	@ViewInject(R.id.activity_userlr_et_phonenum)
	private EditText etPhonenum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlr_register);
	}

	protected boolean isNotInputed() {
		return TextUtils.isEmpty(etName.getText().toString().trim())
				|| TextUtils.isEmpty(etPsw.getText().toString().trim())
				|| TextUtils.isEmpty(etPhonenum.getText().toString().trim());
	}

	@OnClick({ R.id.activity_userlr_bt_rigister })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_userlr_bt_rigister: {
			if (isNotInputed()) {
				Toast.makeText(UserLRRegisterActivity.this, "请输入用户名,密码和手机号码", Toast.LENGTH_SHORT).show();
				return;
			}

			new HttpUtils().send(HttpMethod.GET, HttpActions.register("df", "111111", "09876543216", "123456"),
					new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							try {
								UserBean user = JsonAnalytic.getInstance().analyseJsonTInfo(responseInfo.result,
										UserBean.class);
								ULog.d(user.toString());
								User.saveToken(UserLRRegisterActivity.this, user.getUserToken());
								User.savePhone(UserLRRegisterActivity.this, user.getMobile());

							} catch (DFException e) {
								if (e.getStatusCode() == 1000)
									Toast.makeText(UserLRRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
											.show();
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							// TODO Auto-generated method stub

						}
					});
		}
			break;
		}

	}
}
