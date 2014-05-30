package com.dongfang.daohang.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.daohang.R;
import com.dongfang.daohang.beans.Point;
import com.dongfang.daohang.params.adp.PointAdapter;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 记录点
 * 
 * @author dongfang
 *
 */
public class PointFragment extends BaseFragment {
	@ViewInject(R.id.fragment_point_list)
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_point, container, false);
		ViewUtils.inject(this, view);

		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < 10; i++) {
			list.add(new Point());
		}

		listView.setAdapter(new PointAdapter(getActivity(), list));

		listView.setDivider(null);

		return view;
	}

	@Override
	public void onClick(View v) {}

}
