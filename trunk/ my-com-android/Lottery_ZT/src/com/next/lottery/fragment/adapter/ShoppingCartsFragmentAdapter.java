package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dongfang.utils.ULog;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.fragment.ShoppingCartALLFragment;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 购物车viewPager适配器
 * 
 * @author gfan
 * 
 */
public class ShoppingCartsFragmentAdapter extends FragmentStatePagerAdapter {
	private ArrayList<ShopCartsInfo> list;
	private static ArrayList<ShoppingCartALLFragment>    fragmentList = new ArrayList<ShoppingCartALLFragment>();
	public ShoppingCartALLFragment mFragment;
	
	private String[] title = {"全部商品","降价商品","库存紧张"};

	public ShoppingCartsFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	public ShoppingCartsFragmentAdapter(FragmentManager fm, ArrayList<ShopCartsInfo> shopCartslist) {
		super(fm);
		this.list = shopCartslist;
	}
	

	@Override
	public Fragment getItem(int position) {
		ULog.i("getItemh-->"+position);
		
//		if (position >= fragmentList.size() || fragmentList.get(position) == null) {
			ShoppingCartALLFragment mFragment = new ShoppingCartALLFragment();
			mFragment.setData(list);
//			fragmentList.add(position, mFragment);
//		}
//		else
//			mFragment = fragmentList.get(position);
		
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
		ULog.i("destroyItem-->"+position);
	}
	
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}
}
