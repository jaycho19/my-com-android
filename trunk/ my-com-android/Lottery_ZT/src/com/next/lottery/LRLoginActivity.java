package com.next.lottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.next.lottery.utils.User;
import com.next.lottery.view.SlipButton;

public class LRLoginActivity extends BaseActivity {

	private SlipButton slipBtn;
	private EditText et_password;
	private EditText et_username;
	private Button btn_login, btn_oneregiest;
	private TextView tv_title, tv_forgepwd, tv_emailregiest, tv_back;
	private String ComeFromTag = null;

	private LinearLayout mParent_Login; // PopupWindow 依附的控件
	private int mParent_Width; // PopupWindow 宽度
	private boolean flag = false; // 是否初始化完成标志
	private PopupWindow mPopupWindow; // popupWindow对象
	// private EmailTextViewAdapter mAdapter; //自定义Adapter
	private ListView mListView = null; // 用来处理选中或者删除下拉项消息
	private Handler mHandler; // 用来处理接受消息
	private Boolean misPopwindow = true; // popupWindow 弹起状态
	private static final String OCLICK_OK_STRING = "selIndex"; // 接受消息标识
	private static final int RESPONSE_HANDLERINT = 1;
	public boolean isFromUserCenter = false;
	public Bundle mBundle;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lr_login);
		ViewUtils.inject(this);

		initlogin(savedInstanceState);
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			inintview();// onWindowFocusChanged方法中调用获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
			flag = true;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if (null != intent.getExtras() && intent.getExtras().containsKey("ComeFromTag"))
			ComeFromTag = intent.getExtras().getString("ComeFromTag");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("phonenum", et_username.getText().toString());
		outState.putString("pwd", et_password.getText().toString());
		outState.putBoolean("saveinfo", true);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ComeFromTag = null;
	}

	private void inintview() {
		// mParent_Login = (LinearLayout) findViewById(R.id.login_parent);
		// mParent_Width = mParent_Login.getWidth();
		// initPopuWindow();

	}

	private void initlogin(Bundle savedInstanceState) {

		// slipBtn.setCheck(true);
		// slipBtn.SetOnChangedListener(new OnChangedListener() {
		// @Override
		// public void OnChanged(boolean CheckState) {
		// // TODO Auto-generated method stub
		// if (CheckState) {
		// et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		// }
		// else {
		// et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		//
		// }
		// }
		//
		// });

		// if (!TextUtils.isEmpty(User.getUserId(this))) {
		// et_username.setText(User.getUserId(this));
		// // etPwd.requestFocus();
		// }
		// if (!TextUtils.isEmpty(User.getUserPassword(this))) {
		// et_password.setText(User.getUserPassword(this));
		// }

		User.saveUserId(this, "");// 注销清除用户信息
		User.saveUserPassword(this, "");
	}

	// class OnForgetListener implements View.OnClickListener {
	// @Override
	// public void onClick(View v) {
	// startActivity(new Intent(getApplication(), FindPasswordByPhoneNumberActivity.class));
	// }
	// }

	// private void initPopuWindow() {
	// View loginwindow = (View) this.getLayoutInflater().inflate(R.layout.email_options_listview, null);
	// mListView = (ListView) loginwindow.findViewById(R.id.register_list);
	//
	// // 设置自定义Adapter
	// mAdapter = new EmailTextViewAdapter(this, mHandler);
	// mListView.setAdapter(mAdapter);
	// mPopupWindow = new PopupWindow(loginwindow, mParent_Width, LayoutParams.WRAP_CONTENT, false);
	// mPopupWindow.setOutsideTouchable(true);
	// // 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
	// // 没有这一句则效果不能出来，但并不会影响背景
	// // mpopupWindow.setBackgroundDrawable(new BitmapDrawable());
	// }

	/**
	 * 显示PopupWindow窗口
	 * 
	 * @param popupwindow
	 */
	public void popupWindwShowing() {
		// 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移1pix，
		// 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
		mPopupWindow.showAsDropDown(mParent_Login, 0, -1);
	}

	public void dismiss() {
		mPopupWindow.dismiss();
		misPopwindow = true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			if (null != et_username)
				et_username.setText(savedInstanceState.getString("phonenum") + "feng");
			if (null != et_password)
				et_password.setText(savedInstanceState.getString("pwd"));
			// if (null != cbSaveInfo)
			// cbSaveInfo.setSelected(savedInstanceState.getBoolean("saveinfo"));
		}
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// switch (v.getId()) {
	// case R.id.btn_login:
	// /** 登录页登录按钮点击 */
	// ActionReport actionReport1 = new ActionReport(ActionType.LOGIN, null);
	// BaseApplication.getInstance().getActionList().add(actionReport1);
	// /** --------- end------------------ */
	// String phone = et_username.getText().toString();
	// String password = et_password.getText().toString();
	// if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
	// // new DialogFactory(getActivity()).showDialog("提示",
	// // "用户名或密码不能为空", "确定", null);
	// new DialogFactory(getApplication()).showToast(getString(R.string.dialog_content_input_empty),
	// Toast.LENGTH_SHORT);
	// return;
	// }
	// ULog.i(LRLoginActivity.class.getSimpleName(), "etPhoneNum = " + et_username + ", etPwd = "
	// + et_password);
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
	// if (misPopwindow == true && et_username.getText().toString().contains("@")) {
	// if (et_username != null && et_username.length() > 0 && mAdapter.getCount() != 0) {
	// popupWindwShowing();
	// misPopwindow = false;
	// }
	// }
	// break;
	// default:
	// break;
	// }
	// }

	/** login 异步线程 */
	// class LoginTask extends AsyncTask<Bundle, Integer, Bundle> {
	// private String TAG = LoginTask.class.getSimpleName();
	// boolean isSave = false;
	// String phone;
	// String password;
	// MyProgressDialog mpd;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// mpd = MyProgressDialog.show(getApplication(), "", getApplication().getString(R.string.user_loging), true);
	// mpd.setOnCancelListener(new OnCancelListener() {
	//
	// @Override
	// public void onCancel(DialogInterface dialog) {
	// LoginTask.this.cancel(true);
	// }
	// });
	// mpd.show();
	// }
	//
	// @Override
	// protected Bundle doInBackground(Bundle... params) {
	// phone = params[0].getString(Keys.KEY_USERNAME);
	// password = params[0].getString(Keys.KEY_USERPASSWORD);
	// isSave = params[0].getBoolean("isSave");
	// ULog.v(TAG, "LoginTask#doInBackground");
	// Bundle bundle = null;
	// try {
	// String loginResult = new HttpActions(getApplication()).login(getApplication(), phone, password);
	// ULog.v(TAG, loginResult);
	// bundle = JsonAnalytic.getInstance().analyseLogin(loginResult);
	// // ComParams.isAutoLogin = false;
	// } catch (TVException e) {
	// if (e.getStatusCode() != TVException.JSON_ANALYTIC_LACK_TOKEN) {
	// bundle = new Bundle();
	// bundle.putString("msg", e.getMessage());
	// bundle.putInt("erroCode", e.getStatusCode());
	// }
	// }
	// return bundle;
	// }
	//
	// @Override
	// protected void onPostExecute(Bundle result) {
	// super.onPostExecute(result);
	//
	// if (null != mpd)
	// mpd.dismiss();
	//
	// if (result.containsKey("erroCode")) {
	// if (926 != result.getInt("erroCode"))
	// new DialogFactory(getApplication()).showDialog("信息提示", result.getString("msg"), "确定", null);
	// return;
	// }
	// if (isSave) {
	// Util.saveUserName(getApplication(), phone);
	// Util.saveUserPassword(getApplication(), password);
	// }
	// Util.saveUserLoginStatu(getApplication(), true);
	// MainActivity.getInstance().handler.sendEmptyMessage(1);
	// Util.saveUserId(getApplication(), result.getString(Keys.KEY_USERID));
	// Util.saveUserBindId(getApplication(), result.getString(Keys.KEY_USERBINDID));
	//
	// new DialogFactory(getApplication()).showToast("登陆成功！", Toast.LENGTH_SHORT);
	// LRLoginActivity.this.finish();
	//
	// }
	//
	// @Override
	// protected void onCancelled() {
	// super.onCancelled();
	// if (null != mpd)
	// mpd.cancel();
	// }
	// }

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
