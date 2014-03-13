package com.next.lottery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 个人中心
 * 
 * @author dongfang
 * 
 */
public class UserInfoActivity extends BaseActivity {

	@ViewInject(R.id.app_top_title_iv_left)
	private ImageView ivBack;

	@ViewInject(R.id.activity_user_info_iv_edit)
	private ImageView ivEdit;
	@ViewInject(R.id.activity_user_info_tv_edit)
	private TextView tvEdit;

	@ViewInject(R.id.activity_user_info_et_nickname)
	private EditText etNickName;
	@ViewInject(R.id.activity_user_info_et_sex)
	private EditText etSex;
	@ViewInject(R.id.activity_user_info_et_birthday)
	private EditText etBrithday;
	@ViewInject(R.id.activity_user_info_et_email)
	private EditText etEmail;
	@ViewInject(R.id.activity_user_info_tv_photo)
	private TextView etPhoto;

	@ViewInject(R.id.activity_user_info_tv_user_name)
	private TextView tvUserName;

	private boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		ViewUtils.inject(this);
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_left, R.id.activity_user_info_tv_edit, R.id.activity_user_info_iv_edit })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_user_info_tv_edit:
			if (isEdit)
				toSave();
			else
				toEdit();
			break;
		case R.id.activity_user_info_iv_edit:
			if (!isEdit)
				toEdit();
			break;
		case R.id.app_top_title_iv_left:
			finish();
			break;
		default:
			break;
		}
	}

	private void toEdit() {
		isEdit = true;

		etNickName.setEnabled(true);
		etSex.setEnabled(true);
		etBrithday.setEnabled(true);
		etEmail.setEnabled(true);
		etPhoto.setVisibility(View.VISIBLE);

		etNickName.setHint(etNickName.getText());
		etNickName.setText("");
		etSex.setHint(etSex.getText());
		etSex.setText("");
		etBrithday.setHint(etBrithday.getText());
		etBrithday.setText("");
		etEmail.setHint(etEmail.getText());
		etEmail.setText("");

		tvEdit.setText("保存");
	}

	private void toSave() {
		isEdit = false;

		tvEdit.setText("编辑");

		etNickName.setText(TextUtils.isEmpty(etNickName.getText()) ? etNickName.getHint() : etNickName.getText());
		etSex.setText(TextUtils.isEmpty(etSex.getText()) ? etSex.getHint() : etSex.getText());
		etBrithday.setText(TextUtils.isEmpty(etBrithday.getText()) ? etBrithday.getHint() : etBrithday.getText());
		etEmail.setText(TextUtils.isEmpty(etEmail.getText()) ? etEmail.getHint() : etEmail.getText());

		etNickName.setEnabled(false);
		etSex.setEnabled(false);
		etBrithday.setEnabled(false);
		etEmail.setEnabled(false);
		etPhoto.setVisibility(View.GONE);

		tvUserName.setText(etNickName.getText());
	}
}
