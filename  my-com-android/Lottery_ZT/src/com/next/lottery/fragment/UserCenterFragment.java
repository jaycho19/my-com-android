package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.next.lottery.LRLoginActivity;
import com.next.lottery.R;
import com.next.lottery.utils.User;

public class UserCenterFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (!User.isLogined(getActivity()))
		{
			startActivity(new Intent(getActivity(), LRLoginActivity.class));
			return null;
		}
			
		
		
		View view = inflater.inflate(R.layout.fragment_usercenter, container, false);
		// initView(view);
		return view;
	}
}
