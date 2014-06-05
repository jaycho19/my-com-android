package com.dongfang.daohang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dongfang.daohang.R;
import com.dongfang.daohang.beans.AreaEntity;
import com.dongfang.daohang.net.HttpActions;
import com.dongfang.daohang.params.adp.ShopsListAdapter;
import com.dongfang.utils.DFException;
import com.dongfang.utils.JsonAnalytic;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ShopListFragment extends BaseFragment {

	@ViewInject(R.id.fragment_shoplist_listview)
	private ListView listView;

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_shoplist, null);
		ViewUtils.inject(this, v);
		context = getActivity();
		refresh(this.getArguments());
		return v;
	}

	@Override
	public void setArguments(Bundle args) {
		// super.setArguments(args);
		refresh(args);
	}

	private void refresh(Bundle data) {
		if (null == data)
			return;

		String url = HttpActions.searchArea(context, 10, "0", 1, 10);
		ULog.d(url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ULog.d(responseInfo.result);
				try {

					AreaEntity areaEntity = JsonAnalytic.getInstance().analyseJsonTInfo(responseInfo.result,
							AreaEntity.class);

					ULog.d(areaEntity.toString());

					listView.setAdapter(new ShopsListAdapter(context, areaEntity.getList()));

				} catch (DFException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {}

		});

	}

	@Override
	public void onClick(View v) {}
}
