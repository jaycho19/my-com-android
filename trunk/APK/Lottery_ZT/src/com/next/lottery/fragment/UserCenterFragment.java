package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.MyCollectionsActivity;
import com.next.lottery.MyOrderListActivity;
import com.next.lottery.R;
import com.next.lottery.UserCouponActivity;
import com.next.lottery.UserHelpCenterActivity;
import com.next.lottery.UserInfoActivity;
import com.next.lottery.UserLRLoginActivity;
import com.next.lottery.UserPassWordModifyActivity;
import com.next.lottery.UserSettingActivity;
import com.next.lottery.utils.User;

/**
 * 个人中心
 * 
 * @author dongfang
 * 
 */
public class UserCenterFragment extends BaseFragment {

	@ViewInject(R.id.app_top_title_iv_rigth)
	private ImageView ivSetting;
	@ViewInject(R.id.usercenter_iv_user_head)
	private ImageView ivHead;

	@ViewInject(R.id.usercenter_tv_user_name)
	private TextView tvUserName;
	@ViewInject(R.id.usercenter_tv_user_tishenghuiyuan)
	private TextView tvTiShengHuiYuan;
	@ViewInject(R.id.fragment_usercenter_tv_myhelp)
	private TextView tvHelpCenter;//帮助中心
	@ViewInject(R.id.fragment_usercenter_tv_mycoupon)
	private TextView tvMyCoupon; // 我的优惠券
	@ViewInject(R.id.fragment_usercenter_tv_myorder)
	private TextView tvMyOrder; // 我的订单
	@ViewInject(R.id.fragment_usercenter_tv_myfavorites)
	private TextView tvMyCollections; // 我的收藏
	@ViewInject(R.id.fragment_usercenter_tv_logout)
	private TextView tvLogout; // 注销用户
	@ViewInject(R.id.fragment_usercenter_tv_mypassowrd)
	private TextView tvMyPassWord; // 注销用户

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!User.isLogined(getActivity())) {
			startActivity(new Intent(getActivity(), UserLRLoginActivity.class));
			return null;
		}
		View view = inflater.inflate(R.layout.fragment_usercenter, container, false);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	@OnClick({ R.id.app_top_title_iv_rigth, R.id.usercenter_iv_user_head, R.id.usercenter_tv_user_name,
			R.id.usercenter_tv_user_tishenghuiyuan
			,R.id.fragment_usercenter_tv_myhelp
			,R.id.fragment_usercenter_tv_mycoupon
			,R.id.fragment_usercenter_tv_myorder
			,R.id.fragment_usercenter_tv_logout
			,R.id.fragment_usercenter_tv_mypassowrd
			,R.id.fragment_usercenter_tv_myfavorites
			

	})
	public void onClick(View v) {
		ULog.d("id = " + v.getId());
		switch (v.getId()) {
		case R.id.fragment_usercenter_tv_mycoupon:
			startActivity(new Intent(getActivity(), UserCouponActivity.class));
			break;
		case R.id.app_top_title_iv_rigth:
			startActivity(new Intent(getActivity(), UserSettingActivity.class));
			break;
		case R.id.fragment_usercenter_tv_myhelp:
			startActivity(new Intent(getActivity(), UserHelpCenterActivity.class));
			break;
		case R.id.usercenter_iv_user_head:
		case R.id.usercenter_tv_user_name:
		case R.id.usercenter_tv_user_tishenghuiyuan:
			startActivity(new Intent(getActivity(), UserInfoActivity.class));
			break;
		case R.id.fragment_usercenter_tv_myorder:
			startActivity(new Intent(getActivity(), MyOrderListActivity.class));
			break;
		case R.id.fragment_usercenter_tv_myfavorites:
			startActivity(new Intent(getActivity(), MyCollectionsActivity.class));
			break;
		case R.id.fragment_usercenter_tv_mypassowrd:
			startActivity(new Intent(getActivity(), UserPassWordModifyActivity.class));
			break;
		case R.id.fragment_usercenter_tv_logout:
			User.saveUserId(getActivity(), "");// 注销清除用户信息
			User.saveUserPassword(getActivity(), "");
			startActivity(new Intent(getActivity(), UserLRLoginActivity.class));
			break;
		default:
			break;
		}
	}
}
