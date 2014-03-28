package com.next.lottery.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.fragment.adapter.ClassifyLeftListViewAdapter;
import com.next.lottery.fragment.adapter.TradeMarkListViewAdapter;
import com.next.lottery.listener.OnClickTypeListener;
import com.next.lottery.view.MyHorizontalScrollView;
import com.next.lottery.view.MyHorizontalScrollView.ItemClickListener;

@SuppressLint("ValidFragment")
public class GoosDetailBottomFragment extends BaseFragment {
	
	@SuppressLint("ValidFragment")
	private OnClickTypeListener OnClickTypeListener;
	
	@ViewInject(R.id.btn_buy_now)
	private TextView btnBuyNow;
	@ViewInject(R.id.btn_add_shopping_cart)
	private TextView btnAddShopCart;
	
	@SuppressLint("ValidFragment")
	public GoosDetailBottomFragment(OnClickTypeListener onClickTypeListener) {
		this.OnClickTypeListener = onClickTypeListener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_bottom_bar, container, false);
		ViewUtils.inject(this, view);
		
		btnBuyNow.setOnClickListener(new itemClick());
		btnAddShopCart.setOnClickListener(new itemClick());
		return view;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	class itemClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_buy_now:
				OnClickTypeListener.onClickType(new Bundle());
				break;
			case R.id.btn_add_shopping_cart:
				
				Toast.makeText(getActivity(), "添加成功", 3000).show();
				break;

			default:
				break;
			}
		}
		
	}
	
}

