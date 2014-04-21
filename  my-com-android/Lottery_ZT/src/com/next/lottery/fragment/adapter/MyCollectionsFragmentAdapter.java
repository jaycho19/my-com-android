package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.dongfang.utils.ULog;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.fragment.MyCollectionsFragment;
import com.next.lottery.fragment.ShoppingCartALLFragment;

/**
 * 购物车viewPager适配器
 * 
 * @author gfan
 * 
 */
public class MyCollectionsFragmentAdapter extends FragmentStatePagerAdapter {
	private ArrayList<ShopCartsInfo> list;
	private static ArrayList<ShoppingCartALLFragment> fragmentList = new ArrayList<ShoppingCartALLFragment>();
	public ShoppingCartALLFragment mFragment;

	private String[] title = { "全部商品", "降价商品", "库存紧张" };

	public MyCollectionsFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyCollectionsFragmentAdapter(FragmentManager fm, ArrayList<ShopCartsInfo> shopCartslist) {
		super(fm);
		this.list = shopCartslist;
	}

	@Override
	public Fragment getItem(int position) {
		ULog.i("getItemh-->" + position);

		// if (position >= fragmentList.size() || fragmentList.get(position) == null) {
		MyCollectionsFragment mFragment = new MyCollectionsFragment();
//		mFragment.setData(list);
		// fragmentList.add(position, mFragment);
		// }
		// else
		// mFragment = fragmentList.get(position);

		return mFragment;
	}

	@Override
	public int getCount() {
		return title.length;
	}

	public void notifyDataSetChanged(int position) {
		super.notifyDataSetChanged();
	}

	public CharSequence getPageTitle(int position) {
		// return CONTENT[position % CONTENT.length].toUpperCase();
		return title[position % list.size()].toUpperCase();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
		ULog.i("destroyItem-->" + position);
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}
}
