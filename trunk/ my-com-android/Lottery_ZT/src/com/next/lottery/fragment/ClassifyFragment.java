package com.next.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.R;
import com.next.lottery.SearchAcitivity;
import com.next.lottery.beans.BaseEntity;
import com.next.lottery.beans.CategoryBean;
import com.next.lottery.dialog.ProgressDialog;
import com.next.lottery.fragment.adapter.ClassifyLeftListViewAdapter;
import com.next.lottery.fragment.adapter.ClassifyRightListViewAdapter;
import com.next.lottery.nets.HttpActions;

/**
 * 分类
 * 
 * @author dongfang gfan
 * 
 */
public class ClassifyFragment extends BaseFragment {
	private String[][] recipes2 = { { "连衣裙", "半身裙", "包袋专区", "西裤" }, { "凉鞋", "单鞋" } };
	private String[] Lefttitles = { "服装", "女鞋" };

	@ViewInject(R.id.listview_content_left)
	private ListView listViewleft;
	@ViewInject(R.id.listview_content_right)
	private ListView listViewRight;
	@ViewInject(R.id.layout_content_right)
	private LinearLayout lin_content_right;

	private ClassifyLeftListViewAdapter leftAdapter;
	private ProgressDialog progDialog;

	private float oldX = 0;// 记录滑动时横坐标
	private float oldY = 0;// 记录滑动时竖坐标

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.i("ClassifyFragment oncreat");
		View view = inflater.inflate(R.layout.fragment_classify, container, false);
		ViewUtils.inject(this, view);
		init(view);

		if (null != savedInstanceState) {
			initLeftListView();
		}
		else {
			getLeftData();
		}
		setListener();
		return view;
	}

	private void init(View view) {
		progDialog = ProgressDialog.show(getActivity());
		progDialog.setCancelable(true);
	}

	private void getLeftData() {
		new HttpUtils().send(HttpMethod.GET, HttpActions.GetCategory(), new RequestCallBack<String>() {

			@Override
			public void onStart() {
				progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// CategoryBean
				CategoryBean bean = new Gson().fromJson(responseInfo.result,CategoryBean.class);
				ULog.d(bean.toString());

				progDialog.dismiss();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				progDialog.dismiss();
			}
		});
	}

	private void initLeftListView() {
		leftAdapter = new ClassifyLeftListViewAdapter(getActivity(), R.layout.fragment_classify_left, Lefttitles);
		listViewleft.setAdapter(leftAdapter);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putStringArray("Lefttitles", Lefttitles);
		// outState.putStringArrayList("recipes2", recipes2);
	}

	private void setListener() {
		listViewleft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position == leftAdapter.getSelectPosition()) {
					lin_content_right.setVisibility(View.GONE);
					leftAdapter.setIsRightShowAndPosition(false, position);
				}
				else {
					lin_content_right.setVisibility(View.VISIBLE);
					leftAdapter.setIsRightShowAndPosition(true, position);
					leftAdapter.notifyDataSetChanged();
					ClassifyRightListViewAdapter adapter = new ClassifyRightListViewAdapter(getActivity(),
							R.layout.fragment_classify_right_item, recipes2[position]);
					listViewRight.setAdapter(adapter);
				}
			}
		});

		listViewRight.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {}

		});

		listViewRight.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ULog.i("event-->" + event.getAction());
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					ULog.i("起始x坐标-->" + event.getX());
					oldX = event.getX();
					oldY = event.getY();
					break;
				case MotionEvent.ACTION_CANCEL:
					oldX = 0F;
					oldY = 0F;
					break;
				case MotionEvent.ACTION_MOVE:
					ULog.i("正在活动的x坐标-->" + event.getX());
					break;
				case MotionEvent.ACTION_UP:
					ULog.i("结束x坐标-->" + event.getX());
					if (oldX > 0 && event.getX() - oldX > 120 && event.getY() - oldY < 200) {
						ULog.i("左划，消失右边Listview");
						lin_content_right.setVisibility(View.GONE);
						leftAdapter.setIsRightShowAndPosition(false, -1);
						leftAdapter.notifyDataSetChanged();
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
	@OnClick({ R.id.app_top_title_iv_rigth, R.id.app_top_title_iv_left })
	public void onClick(View v) {
		ULog.d("v.id = " + v.getId());
		switch (v.getId()) {
		case R.id.app_top_title_iv_left:
			/*
			 * Intent intent = new Intent(); intent.setClass(getActivity(), MCaptureActivity.class);
			 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivityForResult(intent, 1); break;
			 */
			startActivity(new Intent(getActivity(), SearchAcitivity.class));
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @return 右边的Listview是否为打开状态
	 */
	public boolean getRightLinearlayoutIsShow() {
		return lin_content_right.isShown();

		// int visible = lin_content_right.getVisibility();
		// if (visible == View.VISIBLE)
		// return true;
		// else
		// return false;
	}

}
