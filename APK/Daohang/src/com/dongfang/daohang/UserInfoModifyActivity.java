package com.dongfang.daohang;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongfang.v4.app.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 修改用户信息
 * 
 * @author dongfang
 *
 */
public class UserInfoModifyActivity extends BaseActivity {

	public static final int TYPE_FRAGMENT_USERNAME = 0;
	public static final int TYPE_FRAGMENT_NICKNAME = 1;
	public static final int TYPE_FRAGMENT_USERSEX = 2;
	public static final int TYPE_FRAGMENT_USERPSW = 3;
	public static final int TYPE_FRAGMENT_USERPHONENUM = 4;

	@ViewInject(R.id.fragment_userinfo_modify_ll_name)
	private LinearLayout llUserName;
	@ViewInject(R.id.fragment_userinfo_modify_ll_nickname)
	private LinearLayout llUserNickName;
	@ViewInject(R.id.fragment_userinfo_modify_ll_usersex)
	private LinearLayout llUserSex;
	@ViewInject(R.id.fragment_userinfo_modify_ll_phonenum)
	private LinearLayout llUserPhoneNum;
	@ViewInject(R.id.fragment_userinfo_modify_ll_userpsw)
	private LinearLayout llUserPsw;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_modify);

		
		switch (getIntent().getIntExtra("type", TYPE_FRAGMENT_USERNAME)) {
		case TYPE_FRAGMENT_NICKNAME:
			llUserNickName.setVisibility(View.VISIBLE);
			break;
		case TYPE_FRAGMENT_USERSEX:
			llUserSex.setVisibility(View.VISIBLE);
			break;
		case TYPE_FRAGMENT_USERPSW:
			llUserPsw.setVisibility(View.VISIBLE);
			break;
		case TYPE_FRAGMENT_USERPHONENUM:
			llUserPhoneNum.setVisibility(View.VISIBLE);
			break;
		default:
			llUserName.setVisibility(View.VISIBLE);
			break;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@OnClick({ R.id.activity_user_info_rl_header })
	@Override
	public void onClick(View v) {}

}
