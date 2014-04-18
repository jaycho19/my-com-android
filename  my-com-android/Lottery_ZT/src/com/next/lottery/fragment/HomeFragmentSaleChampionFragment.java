package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.views.MyImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.next.lottery.GoodsDetailActivity;
import com.next.lottery.R;
import com.next.lottery.SearchNewAcitivity;
import com.next.lottery.beans.CategoryEntity;
import com.next.lottery.beans.HomeStaticBean;
import com.next.lottery.beans.HomeStaticBean.Category;
import com.next.lottery.beans.HomeStaticBean.Data;
import com.next.lottery.listener.OnClickTypeListener;

/**
 * 首页销售冠军 Fragment
 * 
 * @author gfan
 * 
 */
public class HomeFragmentSaleChampionFragment extends BaseFragment {
	protected static String		TAG		= HomeFragmentSaleChampionFragment.class.getSimpleName();

	private OnClickTypeListener	onClickTypeListener;

	private TableLayout			tableLayout;

	@ViewInject(R.id.fragment_home_custom_title_name)
	private TextView			mTagTitileTv;
	@ViewInject(R.id.fragment_home_custom_title_tag1)
	private TextView			mTag1Tv;
	@ViewInject(R.id.fragment_home_custom_title_tag2)
	private TextView			mTag2Tv;
	@ViewInject(R.id.fragment_home_custom_title_more)
	private TextView			mTagMoreTv;

	private ArrayList<Data>		list	= new ArrayList<Data>();
	private Category			category;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_sale_champion, container, false);
		ViewUtils.inject(this, view);
		inteTitleView(view);
		initView(view);
		return view;
	}

	private void inteTitleView(View view) {
		try {
			mTagTitileTv.setText(category.getName());
			for (int i = 0; i < category.getTags().size(); i++) {
				if (i == 0) {
					mTag1Tv.setText(category.getTags().get(i).getName());
				}
				else if (i == 1) {
					mTag2Tv.setText(category.getTags().get(i).getName());
				}
			}
			mTagMoreTv.setText(category.getMore().getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initView(View view) {
		try {
			tableLayout = (TableLayout) view.findViewById(R.id.fragment_home_sale_champion_tl);

			int rowCount = list.size() / 2 + (list.size() % 2 == 0 ? 0 : 1);
			for (int i = 0; i < rowCount; i++) {
				TableRow tr = (TableRow) View.inflate(getActivity().getBaseContext(),
						R.layout.fragment_home_sale_champion_tablerow, null);
				tableLayout.addView(tr);
			}
			int currentRowIndex = 0;
			for (int i = 0; i < rowCount * 2; i++) {
				if (i >= (currentRowIndex + 1) * 2) {
					currentRowIndex++;
				}
				final int index =i;
				int rowPosition = i - currentRowIndex * 2;
				TableRow tr = (TableRow) tableLayout.getChildAt(currentRowIndex);
				View itemView = tr.getChildAt(rowPosition);

				itemView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ULog.i("itemId-->"+list.get(index).getClickParam().getItemid());
						ULog.i("itemId-->"+index);
						getActivity().startActivity(new Intent(getActivity(), GoodsDetailActivity.class).putExtra("id",
								list.get(index).getClickParam().getItemid()));
					}
				});
				if (i < list.size()) {
					MyImageView coverIV = (MyImageView) itemView.findViewById(R.id.fragment_home_item_pic);
					TextView titleTV = (TextView) itemView.findViewById(R.id.fragment_home_item_title);

					coverIV.setImage(list.get(i).getCover());
					titleTV.setText(list.get(i).getTitle());
					ULog.i("saleChampionlist-->" + list.get(i));
				}
				else {
					itemView.setVisibility(View.INVISIBLE);
				}
			}
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(HomeStaticBean homeBean, OnClickTypeListener onClickTypeListener) {
		this.category = homeBean.getCategory();
		this.list = homeBean.getData();
		this.onClickTypeListener = onClickTypeListener;
	}
	@OnClick({R.id.fragment_home_custom_title_tag1, R.id.fragment_home_custom_title_tag2,R.id.fragment_home_custom_title_more})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_home_custom_title_tag1:
			
			break;
		case R.id.fragment_home_custom_title_tag2:
			
			break;
		case R.id.fragment_home_custom_title_more:
			Intent intent = new Intent(getActivity(),SearchNewAcitivity.class);
			CategoryEntity entity = new CategoryEntity();
			entity.setId(category.getMore().getClickParam().getCategoryId());
			intent.putExtra("values", entity);
			getActivity().startActivity(intent);
			break;

		default:
			break;
		}

	}
}