package com.next.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.next.lottery.UserLRLoginActivity;
import com.next.lottery.UserRegisterActivity;
import com.next.lottery.beans.UserBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.User;
import com.next.lottery.view.SlipButton;
import com.next.lottery.view.SlipButton.OnChangedListener;

public class UserLRLoginFragment extends BaseFragment {

	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView ivBack; // 返回
	@ViewInject(R.id.activity_lr_login_et_psw)
	private EditText etPassword; // 密码
	@ViewInject(R.id.activity_lr_login_et_userid)
	private EditText etUsername; // 用户名
	@ViewInject(R.id.activity_lr_login_psw_slipbtn)
	private SlipButton slipBtn; // 是否显示密码
	@ViewInject(R.id.activity_lr_login_tv_forgetpsw)
	private TextView tvForgetPsw; // 忘记密码
	@ViewInject(R.id.activity_lr_login_tv_login)
	private TextView tvLogin; // 忘记密码
	@ViewInject(R.id.activity_lr_login_tv_register)
	private TextView tvRegister; // 忘记密码

	@ViewInject(R.id.activity_lr_login_ll_userid)
	private LinearLayout llUserId; // 用户名布局
	@ViewInject(R.id.activity_lr_login_ll_psw)
	private LinearLayout llPsw; // 密码布局

	@ViewInject(R.id.activity_lr_login_sina)
	private ImageView ivSina;
	@ViewInject(R.id.activity_lr_login_qq)
	private ImageView ivQQ;
	@ViewInject(R.id.activity_lr_login_rr)
	private ImageView ivRR;
	@ViewInject(R.id.activity_lr_login_baidu)
	private ImageView ivBaidu;
	@ViewInject(R.id.activity_lr_login_zhifubao)
	private ImageView ivZhiFuBao;
	@ViewInject(R.id.activity_lr_login_mm)
	private ImageView ivMM;

	private ProgressDialog progDialog;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_lr_login, container, false);
		context = getActivity();

		ViewUtils.inject(this, view);
		initView();
		initlogin(savedInstanceState);
		return view;
	}

	private void initView() {
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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("phonenum", etUsername.getText().toString());
		outState.putString("pwd", etPassword.getText().toString());
		outState.putBoolean("saveinfo", true);
	}

	private void initlogin(Bundle savedInstanceState) {
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);

		User.saveUserId(this.context, "");// 注销清除用户信息
		User.saveUserPassword(this.context, "");

	}

	/**
	 * 显示PopupWindow窗口
	 * 
	 * @param popupwindow
	 */
	public void popupWindwShowing() {
		// 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移1pix，
		// 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
	}

	public void dismiss() {}

	// @Override
	// protected void onRestoreInstanceState(Bundle savedInstanceState) {
	// super.onRestoreInstanceState(savedInstanceState);
	// if (savedInstanceState != null) {
	// if (null != etUsername)
	// etUsername.setText(savedInstanceState.getString("phonenum") + "feng");
	// if (null != etPassword)
	// etPassword.setText(savedInstanceState.getString("pwd"));
	// // if (null != cbSaveInfo)
	// // cbSaveInfo.setSelected(savedInstanceState.getBoolean("saveinfo"));
	// }
	// }

	@OnClick({ R.id.app_top_title_iv_left, R.id.activity_lr_login_tv_login, R.id.activity_lr_login_tv_register,
			R.id.activity_lr_login_sina, R.id.activity_lr_login_qq, R.id.activity_lr_login_rr,
			R.id.activity_lr_login_baidu, R.id.activity_lr_login_zhifubao, R.id.activity_lr_login_mm,
			R.id.app_top_title_iv_rigth })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			getActivity().finish();
			break;
		case R.id.activity_lr_login_tv_login:
			login();
			break;
		case R.id.activity_lr_login_tv_register:
			this.startActivity(new Intent(getActivity(), UserRegisterActivity.class));
			break;
		case R.id.activity_lr_login_tv_forgetpsw:
			break;
		case R.id.activity_lr_login_sina:
			break;
		case R.id.activity_lr_login_qq:
			break;
		case R.id.activity_lr_login_rr:
			break;
		case R.id.activity_lr_login_baidu:
			break;
		case R.id.activity_lr_login_zhifubao:
			break;
		case R.id.activity_lr_login_mm:
			break;
		case R.id.app_top_title_iv_rigth:
			((UserLRLoginActivity) getActivity()).showRight();
			break;

		// case R.id.btn_login:
		// /** 登录页登录按钮点击 */
		// ActionReport actionReport1 = new ActionReport(ActionType.LOGIN, null);
		// BaseApplication.getInstance().getActionList().add(actionReport1);
		// /** --------- end------------------ */
		// String phone = etUsername.getText().toString();
		// String password = etPassword.getText().toString();
		// if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
		// // new DialogFactory(getActivity()).showDialog("提示",
		// // "用户名或密码不能为空", "确定", null);
		// new DialogFactory(getApplication()).showToast(getString(R.string.dialog_content_input_empty),
		// Toast.LENGTH_SHORT);
		// return;
		// }
		// ULog.i(LRLoginActivity.class.getSimpleName(), "etPhoneNum = " + etUsername + ", etPwd = " + etPassword);
		// Bundle b = new Bundle();
		// b.putString(Keys.KEY_USERNAME, phone);
		// b.putString(Keys.KEY_USERPASSWORD, password);
		// b.putBoolean("isSave", true);
		//
		// if (!TextUtils.isEmpty(ComeFromTag))
		// b.putString("ComeFromTag", ComeFromTag);
		//
		// new UserLoginTask(LRLoginActivity.this).execute(b);
		// break;
		// case R.id.btn_one_reister:
		// Intent intent = new Intent(getApplication(), AKeyRegisterActivity.class);
		// intent.putExtra(Keys.IS_FROM_USER_CENTER, isFromUserCenter);
		// if (!TextUtils.isEmpty(ComeFromTag))
		// intent.putExtra("ComeFromTag", ComeFromTag);
		// this.startActivity(intent);
		// ActionReport actionReport2 = new ActionReport(ActionType.REGISTER, null);
		// BaseApplication.getInstance().getActionList().add(actionReport2);
		// /** --------- end------------------ */
		// // handleOneKeyRegister(LoginAndRegisterActivity.this);
		// break;
		// case R.id.tv_forgetpwd:
		// // startActivity(new Intent(LoginAndRegisterActivity.this,
		// // ResetPasswordActivity.class));
		// new CategoryPopupWindow(tv_forgepwd).showResetPwdDropDown(0, 0);
		// Util.hideInput(tv_forgepwd);
		// break;
		// case R.id.tv_email_register:
		// // this.finish();
		// Intent intent1 = new Intent(LRLoginActivity.this, EmailRegisterActivity.class);
		// intent1.putExtra(Keys.IS_FROM_USER_CENTER, isFromUserCenter);
		// if (!TextUtils.isEmpty(ComeFromTag))
		// intent1.putExtra("ComeFromTag", ComeFromTag);
		// startActivity(intent1);
		// break;
		// case R.id.title_back_btn:
		// Util.hideInput(tv_back);
		// this.finish();
		// break;
		// case R.id.login_username:
		// if (misPopwindow == true && etUsername.getText().toString().contains("@")) {
		// if (etUsername != null && etUsername.length() > 0 && mAdapter.getCount() != 0) {
		// popupWindwShowing();
		// misPopwindow = false;
		// }
		// }
		// break;
		default:
			break;
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		String url = HttpActions.login(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
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
					User.saveToken(context, bean.getInfo().getUserToken());
					User.savePhone(context, etUsername.getText().toString());
					Toast.makeText(context, "登陆成功！", Toast.LENGTH_LONG).show();

					MainActivity.changeTab = 4;
					getActivity().finish();

				}
				else {
					// 保存token
					User.saveToken(context, "");
					User.savePhone(context, "");
					Toast.makeText(context, "登陆失败！", Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

	// private class CategoryPopupWindow extends BetterPopupWindow implements OnClickListener {
	//
	// public CategoryPopupWindow(View anchor) {
	// super(anchor);
	// // TODO Auto-generated constructor stub
	// }
	//
	// @Override
	// protected void onCreate() {
	// LayoutInflater inflater = (LayoutInflater) this.anchor.getContext().getSystemService(
	// Context.LAYOUT_INFLATER_SERVICE);
	// ViewGroup root = (ViewGroup) inflater.inflate(R.layout.popup_sub_menu, null);
	// root.removeAllViews();
	//
	// LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	// LinearLayout.LayoutParams.WRAP_CONTENT);
	// btnParams.setMargins(20, 20, 20, 20);
	//
	// Button btnByPhone = new Button(this.anchor.getContext());
	// btnByPhone.setText(R.string.btn_find_pwd_by_phone);
	// btnByPhone.setGravity(Gravity.CENTER);
	// btnByPhone.setTextSize(12.5f);
	// btnByPhone.setTag(0);
	// btnByPhone.setBackgroundResource(R.drawable.btn_reset_pwd_bg);
	// btnByPhone.setOnClickListener(this);
	//
	// Button btnByEmail = new Button(this.anchor.getContext());
	// btnByEmail.setText(R.string.btn_find_pwd_by_email);
	// btnByEmail.setGravity(Gravity.CENTER);
	// btnByEmail.setTextSize(12.5f);
	// btnByEmail.setTag(1);
	// btnByEmail.setBackgroundResource(R.drawable.btn_reset_pwd_bg);
	// btnByEmail.setOnClickListener(this);
	//
	// Button btnCancle = new Button(this.anchor.getContext());
	// btnCancle.setText(R.string.cancel);
	// btnCancle.setGravity(Gravity.CENTER);
	// btnCancle.setTextSize(12.5f);
	// btnCancle.setTag(2);
	// btnCancle.setBackgroundResource(R.drawable.btn_reset_pwd_bg);
	// btnCancle.setOnClickListener(this);
	//
	// root.setBackgroundResource(R.drawable.bg_reset_password);
	// root.addView(btnByPhone, btnParams);
	// root.addView(btnByEmail, btnParams);
	// root.addView(btnCancle, btnParams);
	// this.setContentView(root);
	// }
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// if (v instanceof Button) {
	// Button b = (Button) v;
	// int tag = Integer.parseInt(b.getTag().toString().trim());
	// if (tag == 0) {
	// startActivity(new Intent(LRLoginActivity.this, FindPasswordByPhoneNumberActivity.class));
	// this.dismiss();
	// }
	// else if (tag == 1) {
	// startActivity(new Intent(LRLoginActivity.this, FindPasswordByEmailActivity.class));
	// this.dismiss();
	// }
	// else if (tag == 2) {
	// this.dismiss();
	// }
	//
	// }
	// }
	// }

	// private void autoAddEmails(String input) {
	// String autoEmail = "";
	// // 下拉框选项数据源
	// String[] AUTO_EMAILS = getResources().getStringArray(R.array.email_arrays);
	// if (input.length() > 0) {
	// for (int i = 0; i < AUTO_EMAILS.length; ++i) {
	// if (input.contains("@")) {// 包含“@”则开始过滤
	// String filter = input.substring(input.indexOf("@") + 1, input.length());// 获取过滤器，即根据输入“@”之后的内容过滤出符合条件的邮箱
	// if (AUTO_EMAILS[i].contains(filter)) {// 符合过滤条件
	// autoEmail = input.substring(0, input.indexOf("@")) + AUTO_EMAILS[i];// 用户输入“@”之前的内容加上自动填充的内容即为最后的结果
	// mAdapter.mList.add(autoEmail);
	// }
	// }
	// else {
	// autoEmail = input + AUTO_EMAILS[i];
	// mAdapter.mList.add(autoEmail);
	// }
	// }
	// }
	//
	// }

	// @Override
	// public void afterTextChanged(Editable s) {
	// String input = s.toString();
	// et_username.setSelection(et_username.length());// 首次进来将输入框光标放到最后
	// if (mAdapter == null) { // 防止首次进去，会调用该方法但是mAdapter为null的情况
	// return;
	// }
	// mAdapter.mList.clear();
	// autoAddEmails(input);
	// mAdapter.notifyDataSetChanged();
	// // 如果输入@则触发下拉框
	// if (input.contains("@") && input.length() > 0 && misPopwindow == true) {
	// popupWindwShowing();
	// misPopwindow = false;
	// }
	// else {
	// dismiss();
	// }
	// if (input.length() == 0) {
	// dismiss();
	// }
	// if (mAdapter.getCount() == 0) {
	// dismiss();
	// }
	//
	// }

	// @Override
	// public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	// // TODO Auto-generated method stub
	//
	// }

	// @Override
	// public void onTextChanged(CharSequence s, int start, int before, int count) {
	// // TODO Auto-generated method stub
	//
	// }

	// @Override
	// public boolean handleMessage(Message message) {
	// Bundle data = message.getData();
	// switch (message.what) {
	// case RESPONSE_HANDLERINT:
	// // 选中下拉项，下拉框消失
	// String selIndex = data.getString(OCLICK_OK_STRING);
	// et_username.setText(selIndex);
	// dismiss();
	// et_username.setSelection(et_username.length());// 将输入框光标放到最后
	// break;
	// }
	// return false;
	// }

	// public boolean onKeyUp(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (misPopwindow == false) {
	// dismiss();
	// }
	// else
	// this.finish();
	// return true;
	// }
	// return super.onKeyUp(keyCode, event);
	// }

	// @Override
	// public void onFocusChange(View v, boolean hasFocus) {
	// if (!hasFocus) {// 失去焦点取消下拉框
	// dismiss();
	// }
	// }

}
