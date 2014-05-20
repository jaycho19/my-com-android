package com.dongfang.daohang;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dongfang.daohang.dialog.MMAlert;
import com.dongfang.daohang.dialog.MMAlert.OnAlertSelectId;
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

	@OnClick({ R.id.activity_user_info_rl_header })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_user_info_rl_header:
			MMAlert.showAlert(this, "2222222", new String[]{"11111","22222222","333333333","4444444444"}, "退出",new OnAlertSelectId(){

				@Override
				public void onClick(int whichButton) {
					
				}
				
			} );
			break;

		default:
			break;
		}
		
	}

}
