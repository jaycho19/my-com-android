package com.next.lottery.fragment;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.dongfang.views.MyImageView;
import com.next.lottery.R;
import com.next.lottery.beans.SizeBean;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.utils.Util;

/**
 * 首页销售冠军 Fragment
 * 
 * @author gfan
 * 
 */
public class HomeFragmentSaleChampionFragment extends BaseFragment {
	protected static String		TAG	= HomeFragmentSaleChampionFragment.class.getSimpleName();

	private OnClickTypeListener	onClickTypeListener;

	private TableLayout			tableLayout;

	private List<String> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home_sale_champion, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
			tableLayout = (TableLayout) view.findViewById(R.id.fragment_home_sale_champion_tl);

			int rowCount = list.size() / 2 + (list.size() % 2 == 0 ? 0 : 1);
			for (int i = 0; i < rowCount; i++) {
				TableRow tr = (TableRow) View.inflate(getActivity().getBaseContext(), R.layout.fragment_home_sale_champion_tablerow, null);
				tableLayout.addView(tr);
			}
			int currentRowIndex = 0;
			for (int i = 0; i < rowCount * 2; i++) {
				if (i >= (currentRowIndex + 1) * 2) {
					currentRowIndex++;
				}
				int rowPosition = i - currentRowIndex * 2;
				TableRow tr = (TableRow) tableLayout.getChildAt(currentRowIndex);
				View itemView = tr.getChildAt(rowPosition);
				
				itemView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onClickTypeListener.onClickType(new Bundle());
					}
				});
				if (i < list.size()) {
					MyImageView coverIV = (MyImageView) itemView
							.findViewById(R.id.fragment_home_item_pic);
					TextView titleTV = (TextView) itemView.findViewById(R.id.fragment_home_item_title);
					
					coverIV.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_home_fragment_sale_champion_test));
					titleTV.setText(list.get(i));
					ULog.i("saleChampionlist-->"+list.get(i));
				}
				else {
					itemView.setVisibility(View.INVISIBLE);
				}
			}
		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}


	public void setData(List<String> list, OnClickTypeListener onClickTypeListener) {
		this.list = list;
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}