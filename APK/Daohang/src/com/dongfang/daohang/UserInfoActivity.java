package com.dongfang.daohang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dongfang.daohang.dialog.MMAlert;
import com.dongfang.daohang.dialog.MMAlert.OnAlertSelectId;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 关注
 * 
 * @author dongfang
 *
 */
public class UserInfoActivity extends BaseActivity {

	@ViewInject(R.id.activity_user_info_rl_header)
	private RelativeLayout rlHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
	}

	@OnClick({ R.id.activity_user_info_rl_header, R.id.activity_user_info_rl_nickname,
			R.id.activity_user_info_rl_phonenumber, R.id.activity_user_info_rl_username,
			R.id.activity_user_info_rl_userpsw, R.id.activity_user_info_rl_usersex

	})
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (R.id.activity_user_info_rl_header == id) {
			MMAlert.showAlert(this, null, new String[] { "拍照", "相册" }, null, new OnAlertSelectId() {
				@Override
				public void onClick(int whichButton) {
					ULog.d("whichButton = " + whichButton);
				}

			});
			return;
		}

		Intent intent = new Intent(this, UserInfoModifyActivity.class);
		switch (id) {
		case R.id.activity_user_info_rl_nickname:
			intent.putExtra("type", UserInfoModifyActivity.TYPE_FRAGMENT_NICKNAME);
			break;
		case R.id.activity_user_info_rl_phonenumber:
			intent.putExtra("type", UserInfoModifyActivity.TYPE_FRAGMENT_USERPHONENUM);
			break;
		case R.id.activity_user_info_rl_username:
			intent.putExtra("type", UserInfoModifyActivity.TYPE_FRAGMENT_USERNAME);
			break;
		case R.id.activity_user_info_rl_userpsw:
			intent.putExtra("type", UserInfoModifyActivity.TYPE_FRAGMENT_USERPSW);
			break;
		case R.id.activity_user_info_rl_usersex:
			intent.putExtra("type", UserInfoModifyActivity.TYPE_FRAGMENT_USERSEX);
			break;
		default:
			break;
		}
		startActivity(intent);

	}
}
