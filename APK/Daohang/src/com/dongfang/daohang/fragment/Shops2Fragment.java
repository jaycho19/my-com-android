package com.dongfang.daohang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.R;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;

/**
 * 商户
 * 
 * @author dongfang
 *
 */
public class Shops2Fragment extends BaseFragment {
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}

		view = inflater.inflate(R.layout.fragment_shops_2, container, false);
		ViewUtils.inject(this, view);

		ShopListFragment shoplistFragment = (ShopListFragment) getFragmentManager().findFragmentById(
				R.id.fragment_shops_2_shoplist);

		Bundle data = new Bundle();
		data.putString("name", "1");
		shoplistFragment.refresh(getActivity(), data);

		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Fragment f = getFragmentManager().findFragmentById(R.id.fragment_shops_2_shoplist);
		if (f != null)
			getFragmentManager().beginTransaction().remove(f).commit();
	}

	@Override
	public void onClick(View v) {}

}
