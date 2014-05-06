package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.GoodsDetailFragment;

/**
 * 详情页
 * 
 * @author dongfang
 * 
 */
public class GoodsDetailActivity extends BaseSlidingMenuActivity {
	private GoodsDetailFragment centerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		centerFragment = new GoodsDetailFragment(this);
		centerFragment.setArguments(this.getIntent().getExtras());
		setCenterFragment(centerFragment);
		setTopTitle("宝贝详情");

	}

}
