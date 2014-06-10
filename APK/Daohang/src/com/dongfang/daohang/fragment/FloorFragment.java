package com.dongfang.daohang.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongfang.daohang.R;
import com.dongfang.daohang.TakeMeActivity;
import com.dongfang.daohang.beans.TextNavBean;
import com.dongfang.daohang.bridge.ProxyBridge;
import com.dongfang.daohang.params.ComParams;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 楼层
 * 
 * @author dongfang
 *
 */
public class FloorFragment extends BaseFragment {
	@ViewInject(R.id.top_bar_tv_title)
	private TextView title;
	@ViewInject(R.id.top_bar_btn_back)
	private View vBack;

	@ViewInject(R.id.fragment_floor_myWebView)
	private MyWebView webView;

	@ViewInject(R.id.fragment_floor_text_navigation_ll)
	private LinearLayout ll;
	@ViewInject(R.id.fragment_floor_text_navigation_tv)
	private TextView tvNavigation;

	private ProxyBridge proxyBridge;
	private ArrayList<TextNavBean> listData;
	private int i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_floor, container, false);
		ViewUtils.inject(this, v);

		title.setText("楼层");
		vBack.setVisibility(View.INVISIBLE);
		proxyBridge = new ProxyBridge(getActivity(), webView);
		webView.addJavascriptInterface(proxyBridge);
		webView.loadUrl(ComParams.BASE_URL);

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ULog.d(requestCode + " --- " + resultCode);
		if (0x00E0 == requestCode && resultCode == Activity.RESULT_OK) {
			proxyBridge.setPosition(data.getStringExtra("start"), 1);
			proxyBridge.setPosition(data.getStringExtra("end"), 2);
			listData = data.getParcelableArrayListExtra("textlist");
			tvNavigation.setText(listData.get(i).getText());
			ll.setVisibility(View.VISIBLE);
		}
	}

	@OnClick({ R.id.fragment_floor_iv_dwq, R.id.fragment_floor_iv_test, R.id.fragment_floor_text_navigation_btn_right,
			R.id.fragment_floor_text_navigation_btn_left })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_floor_text_navigation_btn_left:
			i = i < 1 ? 0 : --i;
			tvNavigation.setText(listData.get(i).getText());
			proxyBridge.nextRoadTextList(listData.get(i).getFloor(), listData.get(i).getX(), listData.get(i).getY());
			break;
		case R.id.fragment_floor_text_navigation_btn_right:
			i = i > (listData.size() - 2) ? listData.size() - 1 : ++i;
			tvNavigation.setText(listData.get(i).getText());
			proxyBridge.nextRoadTextList(listData.get(i).getFloor(), listData.get(i).getX(), listData.get(i).getY());
			break;
		case R.id.fragment_floor_iv_dwq:
			startActivityForResult(new Intent(getActivity(), TakeMeActivity.class), 0x00E0);
			break;
		case R.id.fragment_floor_iv_test:
			// RecordAlert.show(getActivity(), "33333").show();
			// proxyBridge.openDialog(1,"1234567890");
			proxyBridge.setPosition("359", 1);
			proxyBridge.setPosition("361", 2);
			proxyBridge.getRoadTextList();
			// Intent intent = new Intent(getActivity(), ShopInfoActivity.class);
			// intent.putExtra("title", "优衣库");
			// intent.putExtra("placeId", 10);
			// intent.putExtra("shopId", 1);
			// startActivity(intent);

			break;
		default:
			break;
		}

	}

}
