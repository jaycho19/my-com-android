package com.next.lottery.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.fragment.adapter.ClassifyLeftListViewAdapter;
import com.next.lottery.fragment.adapter.ClassifyRightListViewAdapter;

/**
 * 分类
 * 
 * @author dongfang
 * 
 */
public class ClassifyFragment extends BaseFragment {
	private String[] recipes2 = { "毛衣", "上衣", "衬衫", "连衣裙", "毛衣", "上衣", "衬衫",
			"连衣裙", "毛衣", "上衣", "衬衫", "连衣裙" };
	private String[] Lefttitles = { "热销商品", "经典系列", "男士系列", "女士系列", "儿童系列",
			"箱包系列", "配件 礼品", "家居系列", "汽车用品", "母婴" };

	@ViewInject(R.id.listview_content_left)
	private ListView listViewleft;
	@ViewInject(R.id.listview_content_right)
	private ListView listViewRight;
	@ViewInject(R.id.layout_content_right)
	private LinearLayout lin_content_right;
	
	private float oldX=0;//记录滑动时横坐标
	private float oldY=0;//记录滑动时竖坐标

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
		
		listViewRight.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				ULog.i("event-->"+event.getAction());
				
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						ULog.i("起始x坐标-->"+event.getX());
						oldX = event.getX();
						oldY = event.getY();
						break;
					case MotionEvent.ACTION_CANCEL:
						oldX = 0F;
						oldY = 0F;
						break;
					case MotionEvent.ACTION_MOVE:
						ULog.i("正在活动的x坐标-->"+event.getX());
						break;
					case MotionEvent.ACTION_UP:
						ULog.i("结束x坐标-->"+event.getX());
						if (oldX>0&&event.getX()-oldX>120&&event.getY()-oldY<200) {
							ULog.i("左划，消失右边Listview");
							lin_content_right.setVisibility(View.GONE);
						}
						oldX = 0F;
						oldY = 0F;
						break;

					default:
						break;
					}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
