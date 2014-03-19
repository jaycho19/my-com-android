package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.HomeFragmentRecommendGridViewAdapter;
import com.next.lottery.listener.OnClickTypeListener;

/**
 * 首页推荐 fragment。
 * 
 * @author gfan
 */
public class HomeFragmentRecommendFragment extends BaseFragment implements OnItemClickListener {
	protected static String		TAG		= HomeFragmentRecommendFragment.class.getSimpleName();

	private List<String>	list	= new ArrayList<String>();

	private OnClickTypeListener onClickTypeListener;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
			GridView gridView = (GridView) view.findViewById(R.id.fragment_home_recommend_gridview);
			HomeFragmentRecommendGridViewAdapter gridadapter = new HomeFragmentRecommendGridViewAdapter(getActivity(), list);
			gridView.setAdapter(gridadapter);
			gridView.setOnItemClickListener(this);
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(List<String>	list, OnClickTypeListener onClickTypeListener) {
		this.list = list;
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
//		AreacodeFragmentUtil.dealWithClickType(getActivity(), Util.setClickTypeData(list.get(position)));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
