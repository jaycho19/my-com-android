package com.next.lottery.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.SearchNewAcitivity;
import com.next.lottery.beans.CategoryEntity;
import com.next.lottery.beans.HomeStaticBean;
import com.next.lottery.beans.HomeStaticBean.Data;
import com.next.lottery.fragment.adapter.HomeFragmentRecommendGridViewAdapter;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.utils.ClickType;

/**
 * 首页推荐 fragment。
 * 
 * @author gfan
 */
public class HomeFragmentRecommendFragment extends BaseFragment implements OnItemClickListener {
	protected static String		TAG				= HomeFragmentRecommendFragment.class.getSimpleName();
	@ViewInject(R.id.fragment_home_recommend_gridview)
	private GridView			gridView;
	@ViewInject(R.id.vd_pic)
	private ImageView			mImageView;
	@ViewInject(R.id.tv_goods_title)
	private TextView			mtitletv;
	@ViewInject(R.id.home_fragment_season_ll)
	private LinearLayout		seasonHotll;

	private ArrayList<Data>		mGridData		= new ArrayList<Data>();								//
	private ArrayList<Data>		mSeasonHotData	= new ArrayList<Data>();								// 当季热抢数据

	private OnClickTypeListener	onClickTypeListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
			HomeFragmentRecommendGridViewAdapter gridadapter = new HomeFragmentRecommendGridViewAdapter(getActivity(),
					mGridData);
			gridView.setAdapter(gridadapter);
			gridView.setOnItemClickListener(this);

			new BitmapUtils(getActivity()).display(mImageView, mSeasonHotData.get(0).getCover());
			mtitletv.setText(mSeasonHotData.get(0).getName());
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(HomeStaticBean homeBean, OnClickTypeListener onClickTypeListener) {
		for (Data dataBean : homeBean.getData()) {
			if (dataBean.getClickType() == ClickType.CLICK_TYPE_HOME_FRAGMENT_RECOMMEND) {
				mGridData.add(dataBean);
			}
			else if (dataBean.getClickType() == ClickType.CLICK_TYPE_HOME_FRAGMENT_SEASON_HOT_SALE) {
				mSeasonHotData.add(dataBean);
			}
		}
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), SearchNewAcitivity.class);
		CategoryEntity entity = new CategoryEntity();
		entity.setKeyword(mGridData.get(position).getClickParam().getKeyword());
		intent.putExtra("values", entity);
		getActivity().startActivity(intent);
		// AreacodeFragmentUtil.dealWithClickType(getActivity(),
		// Util.setClickTypeData(list.get(position)));
	}

	@OnClick(R.id.home_fragment_season_ll)
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), SearchNewAcitivity.class);
		CategoryEntity entity = new CategoryEntity();
		if (mSeasonHotData.size()>0&&null!=mSeasonHotData.get(0).getClickParam()) {
			entity.setKeyword(mSeasonHotData.get(0).getClickParam().getKeyword());
		}
		intent.putExtra("values", entity);
		getActivity().startActivity(intent);
	}
}
