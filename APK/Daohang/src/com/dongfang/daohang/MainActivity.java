package com.dongfang.daohang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongfang.daohang.fragment.ActivityFragment;
import com.dongfang.daohang.fragment.HomeFragment;
import com.dongfang.daohang.fragment.ListFragment;
import com.dongfang.daohang.fragment.SettingFragment;
import com.dongfang.daohang.fragment.UserFragment;
import com.dongfang.daohang.views.MyWebView;
import com.dongfang.utils.ULog;
import com.dongfang.utils.User;
import com.dongfang.v4.app.BaseActivity;
import com.dongfang.v4.app.FragmentTabHostDF;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @author dongfang
 *
 */
public class MainActivity extends BaseActivity {

	@ViewInject(android.R.id.tabhost)
	private FragmentTabHostDF fgtHost;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		initData(getIntent());
		// ViewUtils.inject(this);
		initTabhostItems();
	}

	/** 获取首页数据 */
	private void initData(Intent intent) {
		ULog.d("initData");
	}

	/** 初始化底部菜单 */
	@SuppressLint("NewApi")
	private void initTabhostItems() {
		ULog.d("initTabhostItems");
		fgtHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// fgtHost.getTabWidget().setRightStripDrawable(getResources().getColor(R.color.black));

		// TextView tab1 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab2 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab3 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab4 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);
		// TextView tab5 = (TextView) getLayoutInflater().inflate(R.layout.activity_main_tab, null);

		// tab2.setText("分类");
		// tab3.setText("品牌");
		// tab4.setText("购物车");
		// tab5.setText("个人");
		//
		// tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_fenlei_bg, 0, 0);
		// tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_pinpai_bg, 0, 0);
		// tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_gouwuche_bg, 0, 0);
		// tab5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mian_activity_tab_usercenter_bg, 0, 0);

		// tab2.setBackgroundResource(R.drawable.mian_activity_tab_live_bg);
		// tab3.setBackgroundResource(R.drawable.mian_activity_tab_vod_bg);
		// tab4.setBackgroundResource(R.drawable.mian_activity_tab_search_bg);
		// tab5.setBackgroundResource(R.drawable.mian_activity_tab_user_bg);

		// Bundle data = new Bundle();
		// data.putParcelable("homebean", homeBean);

		// fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator(tab1), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator(tab2), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator(tab3), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator(tab4), PlaceholderFragment.class, null);
		// fgtHost.addTab(fgtHost.newTabSpec("5").setIndicator(tab5), PlaceholderFragment.class, null);

		fgtHost.getTabWidget().setDividerDrawable(null);

		// fgtHost.addTab(fgtHost.newTabSpec("6").setIndicator("66"), TypeFragment.class, null);

		fgtHost.addTab(fgtHost.newTabSpec("0").setIndicator("地图"), HomeFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("1").setIndicator("列表"), ListFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("2").setIndicator("活动"), ActivityFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("3").setIndicator("我的"), UserFragment.class, null);
		fgtHost.addTab(fgtHost.newTabSpec("4").setIndicator("设置"), SettingFragment.class, null);

		User.saveToken(this, "");

		fgtHost.setOnBeforeChangeTab(new FragmentTabHostDF.OnBeforeChangeTab() {
			@Override
			public int onBeforeChangeTab(int index) {
				if (3 == index && !User.isLogined(context)) {
					context.startActivity(new Intent(context, UserLRLoginActivity.class));
					return index;
				}
				return -1;
			}
		});
		// fgtHost.setCurrentTab(0);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			MyWebView v = (MyWebView) rootView.findViewById(R.id.my_webview);

			// SVG svg;
			// try {
			// svg = SVGParser.getSVGFromAsset(getActivity().getAssets(), "test1.svg");
			// Picture picture = svg.getPicture();
			// ULog.d(picture.getHeight() + "");
			// // Drawable drawable = svg.createPictureDrawable();
			// v.setImageDrawable(new PictureDrawable(picture));
			// Bitmap bmp = v.getDrawingCache();
			// Matrix matrix = new Matrix();
			// matrix.postScale(0.05f, 0.05f);
			// Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, picture.getWidth(), picture.getHeight(), matrix, true);
			// v.setImageBitmap(newbm);
			// } catch (SVGParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			v.loadUrl("file:///android_asset/test1.svg");
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {}

}
