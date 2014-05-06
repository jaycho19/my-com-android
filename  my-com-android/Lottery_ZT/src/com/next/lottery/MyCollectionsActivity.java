package com.next.lottery;

import android.os.Bundle;

import com.next.lottery.fragment.MyCollectionsMainFragment;

/**
 * 我的收藏
 * 
 * @author dongfang
 * 
 */
public class MyCollectionsActivity extends BaseSlidingMenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCenterFragment(new MyCollectionsMainFragment());
		setTopTitle("我的收藏");

	}
}