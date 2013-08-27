package com.dongfang.yzsj.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.view.PullToRefreshView;
import com.dongfang.view.PullToRefreshView.OnFooterRefreshListener;
import com.dongfang.view.PullToRefreshView.OnHeaderRefreshListener;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.MediaBean;
import com.dongfang.yzsj.bean.VODItem;
import com.dongfang.yzsj.fragment.adp.FavoriteAdp;

/**
 * 个人搜藏
 * 
 * @author dongfang
 * 
 */
public class FavoriteFragment extends Fragment implements OnHeaderRefreshListener, OnFooterRefreshListener {

	private ListView mList;
	private PullToRefreshView mPullToRefreshView;
	private FavoriteAdp mFavoriteAdp;
	private List<MediaBean> mediaBeanList; // = new ArrayList<MediaBean>;
	private ArrayList<VODItem> listVODItem = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_favorite, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		MediaBean bean = new MediaBean();
		bean.setCid("99999");
		bean.setDes("111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		bean.setActor("刘德华");
		bean.setImageUrl("");
		bean.setTitle("TEST");
		mediaBeanList = new ArrayList<MediaBean>();
		for (int i = 0; i < 10; i++)
			mediaBeanList.add(bean);

		mFavoriteAdp = new FavoriteAdp(getActivity(), mediaBeanList);

		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.favorite_PullToRefreshView);
		mList = (ListView) view.findViewById(R.id.favorite_listview);
		mList.setAdapter(mFavoriteAdp);

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}
}
