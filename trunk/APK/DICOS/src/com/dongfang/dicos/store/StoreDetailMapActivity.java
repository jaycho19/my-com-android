package com.dongfang.dicos.store;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;

/**
 * 门店详细信息
 * 
 * @author dongfang
 * */
public class StoreDetailMapActivity extends MapActivity implements OnClickListener {

	private static final String	tag					= "StoreDetailMapActivity";

	private Button				bBack;

	private TextView			tvTitle;

	private Double				x, y;
	private String				name, add, tel;

	private BMapManager			mBMapMan			= null;
	private MapView				mMapView			= null;
	private LocationListener	mLocationListener	= null;										// onResume时注册此listener，onPause时需要Remove
	private MyLocationOverlay	mLocationOverlay	= null;										// 定位图层

	static View					mPopView			= null;										// 点击mark时弹出的气泡View
	OverItemT					overitem			= null;

	private String				mStrKey				= "1E8E5C6D90D947149E83C10ECA133911A9DE8ECC";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_detail_map);

		Intent intent = getIntent();
		x = intent.getExtras().getDouble("x");
		y = intent.getExtras().getDouble("y");
		name = intent.getExtras().getString("store_name");
		add = intent.getExtras().getString("store_add");
		tel = intent.getExtras().getString("store_tel");

		ULog.d(tag, "onCreate x = " + x + " ; y = " + y + " ; name = " + name);

		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);// 设置通知间隔

		mBMapMan.start();
		// 如果使用地图SDK，请初始化地图Activity
		long iTime = System.nanoTime();
		super.initMapActivity(mBMapMan);
		iTime = System.nanoTime() - iTime;
		ULog.d(tag, "the init time is  " + iTime);

		mMapView = (MapView) findViewById(R.id.mapview_store_detail_map);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapView.setDrawOverlayWhenZooming(true); // 设置在缩放动画过程中也显示overlay,默认为不绘制
		mMapView.setAlwaysDrawnWithCacheEnabled(true);

		// MapController mMapController = mMapView.getController(); //
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		// GeoPoint point = new GeoPoint((int) (x * 1E6), (int) (y * 1E6)); //
		// 用给定的经纬度构造一个GeoPoint，单位是微度
		// // (度
		// // *
		// // 1E6)
		//
		// mMapController.setCenter(point); // 设置地图中心点
		// mMapController.setZoom(12); // 设置地图zoom级别
		//
		// mLocationOverlay = new MyLocationOverlay(this, mMapView);
		// mMapView.getOverlays().add(mLocationOverlay);

		final GeoPoint point = new GeoPoint((int) (y * 1e6), (int) (x * 1e6));

		Drawable marker = getResources().getDrawable(R.drawable.iconmarka); // 得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight()); // 为maker定义位置和边界

		overitem = new OverItemT(marker, new OverlayItem(point, name, name));
		mMapView.getOverlays().add(overitem); // 添加ItemizedOverlay实例到mMapView

		// 创建点击mark时的弹出泡泡
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		((TextView) mPopView.findViewById(R.id.textview_popview_store_name)).setText(name);
		if (!TextUtils.isEmpty(add)) {
			TextView tv = ((TextView) mPopView.findViewById(R.id.textview_popview_store_add));
			tv.setText(add);
			tv.setVisibility(View.VISIBLE);
		}
		if (!TextUtils.isEmpty(tel)) {
			TextView tv = ((TextView) mPopView.findViewById(R.id.textview_popview_store_tel));
			tv.setText(tel);
			tv.setVisibility(View.VISIBLE);
		}

		mMapView.addView(mPopView, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);

		bBack = (Button) findViewById(R.id.button_store_detail_map_back);
		bBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.textview_store_detail_map_title);

		// GeoPoint point = new GeoPoint((int) (y * 1e6), (int) (x * 1e6));
		// mMapView.getController().setCenter(point);

		// 注册定位事件
		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					// GeoPoint pt = new GeoPoint((int) (x * 1e6), (int) (y *
					// 1e6));
					// ULog.d(tag, "onLocationChanged x = " +
					// location.getLatitude() + " ; y = " +
					// location.getLongitude());
					// GeoPoint pt = new GeoPoint((int) (location.getLatitude()
					// * 1e6), (int) (location.getLongitude() * 1e6));

					mMapView.getController().animateTo(point);

				}
			}
		};
	}

	@Override
	protected void onResume() {
		ULog.d(tag, "onResume");
		if (mBMapMan != null) {
			ULog.d(tag, "onResume mBMapMan != null");
			mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
			// mLocationOverlay.enableMyLocation();
			// mLocationOverlay.enableCompass(); // 打开指南针
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		ULog.d(tag, "onPause");
		if (mBMapMan != null) {
			mBMapMan.getLocationManager().removeUpdates(mLocationListener);
			// mLocationOverlay.disableMyLocation();
			// mLocationOverlay.disableCompass(); // 关闭指南针
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		ULog.d(tag, "onDestroy");
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_store_detail_map_back:
			finish();
			break;
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			ULog.d(tag, "onGetNetworkState error is " + iError);
			Toast.makeText(StoreDetailMapActivity.this, "您的网络出错啦！", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			ULog.d(tag, "onGetPermissionState error is " + iError);
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(StoreDetailMapActivity.this, "请在BMapApiDemoApp.java文件输入正确的授权Key！", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	class OverItemT extends ItemizedOverlay<OverlayItem> {

		public List<OverlayItem>	mGeoList	= new ArrayList<OverlayItem>();
		private Drawable			marker;

		public OverItemT(Drawable marker, OverlayItem overlayitem) {
			super(boundCenterBottom(marker));

			this.marker = marker;

			// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
			mGeoList.add(overlayitem);
			populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
		}

		public void updateOverlay() {
			populate();
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {

			// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
			Projection projection = mapView.getProjection();
			for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
				OverlayItem overLayItem = getItem(index); // 得到给定索引的item

				String title = overLayItem.getTitle();
				// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
				Point point = projection.toPixels(overLayItem.getPoint(), null);

				// 可在此处添加您的绘制代码
				// Paint paintText = new Paint();
				// paintText.setColor(Color.BLUE);
				// paintText.setTextSize(15);
				// canvas.drawText(title, point.x - 30, point.y, paintText); //
				// 绘制文本
			}

			super.draw(canvas, mapView, shadow);
			// 调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
			boundCenterBottom(marker);
		}

		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return mGeoList.get(i);
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mGeoList.size();
		}

		@Override
		// 处理当点击事件
		protected boolean onTap(int i) {
			setFocus(mGeoList.get(i));
			// 更新气泡位置,并使之显示
			GeoPoint pt = mGeoList.get(i).getPoint();
			mMapView.updateViewLayout(mPopView, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, pt, MapView.LayoutParams.BOTTOM_CENTER));
			mPopView.setVisibility(View.VISIBLE);
			// Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
			// Toast.LENGTH_SHORT).show();
			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			// TODO Auto-generated method stub
			// 消去弹出的气泡
			mPopView.setVisibility(View.GONE);
			return super.onTap(arg0, arg1);
		}
	}
}
