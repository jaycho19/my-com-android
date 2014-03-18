package com.next.lottery.fragment;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.ClassifyLeftListViewAdapter;
import com.next.lottery.fragment.adapter.ClassifyRightListViewAdapter;
import android.view.GestureDetector.OnGestureListener;

/**
 * 分类
 * 
 * @author dongfang
 * 
 */
public class ClassifyFragment extends BaseFragment implements OnGestureListener {
	private String[] recipes2 = { "毛衣", "上衣", "衬衫", "连衣裙", "毛衣", "上衣", "衬衫",
			"连衣裙", "毛衣", "上衣", "衬衫", "连衣裙" };
	private String[] Lefttitles = { "热销商品", "经典系列", "男士系列", "女士系列", "儿童系列",
			"箱包系列", "配件 礼品", "家居系列", "汽车用品", "母婴" };
	
	private GestureDetector gestureDetector;

	@ViewInject(R.id.listview_content_left)
	private ListView listViewleft;
	@ViewInject(R.id.listview_content_right)
	private ListView listViewRight;
	@ViewInject(R.id.layout_content_right)
	private LinearLayout lin_content_right;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_classify, container,
				false);
		ViewUtils.inject(this, view);
		initLeftListView();
		setListener();
		return view;
	}

	private void initLeftListView() {
		ClassifyLeftListViewAdapter adapter = new ClassifyLeftListViewAdapter(
				getActivity(), R.layout.fragment_classify_left, Lefttitles);
		listViewleft.setAdapter(adapter);
	}

	private void setListener() {
		listViewleft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				lin_content_right.setVisibility(View.VISIBLE);
				ClassifyRightListViewAdapter adapter = new ClassifyRightListViewAdapter(
						getActivity(), R.layout.fragment_classify_right_item,
						recipes2);
				listViewRight.setAdapter(adapter);

			}
		});

		listViewRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
