package com.dongfang.dicos.store;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
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

	private BMapManager			mBMapMan			= null;
	private MapView				mMapView			= null;
	private LocationListener	mLocationListener	= null;										// onResume时注册此listener，onPause时需要Remove
	private MyLocationOverlay	mLocationOverlay	= null;										// 定位图层
	private String				mStrKey				= "1E8E5C6D90D947149E83C10ECA133911A9DE8ECC";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_detail_map);

		Intent intent = getIntent();
		x = intent.getExtras().getDouble("x");
		y = intent.getExtras().getDouble("y");

		ULog.d(tag, "onCreate x = " + x + " ; y = " + y);

		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);//设置通知间隔

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
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);

		bBack = (Button) findViewById(R.id.button_store_detail_map_back);
		bBack.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.textview_store_detail_map_title);

//		GeoPoint point = new GeoPoint((int) (y * 1e6), (int) (x * 1e6));
//		mMapView.getController().setCenter(point);

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
					// * 1e6),
					// (int) (location.getLongitude() * 1e6));
					GeoPoint point = new GeoPoint((int) (y * 1e6), (int) (x * 1e6));
					mMapView.getController().animateTo(point);
					mMapView.getController().setZoom(12);
					
				}
			}
		};
	}

	@Override
	protected void onResume() {
		ULog.d(tag, "onResume");
		if (mBMapMan != null) {
			mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
			mLocationOverlay.enableMyLocation();
			mLocationOverlay.enableCompass(); // 打开指南针
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		ULog.d(tag, "onPause");

		if (mBMapMan != null) {
			mBMapMan.getLocationManager().removeUpdates(mLocationListener);
			mLocationOverlay.disableMyLocation();
			mLocationOverlay.disableCompass(); // 关闭指南针
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
		Intent intent;
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

}
