package com.next.lottery.utils;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.dongfang.utils.ULog;
public class ImageCache {
	private static final String			TAG					= "ImageCache";
	private static int					CACHE_SIZE			= 10 * 1024 * 1024;
	private static final int			MAX_MEMORY			= 64 * 1024 * 1024;
	private LruCache<String, Bitmap>	mMemeoryCache;
	private static ImageCache			IMAGECACHE;
	private static final int			CLEAR_IMAGE_CACHE	= 1;
	private ClearImageCacheHandler		mHandler;
	private List<String>				mUrls				= new LinkedList<String>();
	private Map<String, BitmapLoader>	mBitmapRecycles		= new LinkedHashMap<String, BitmapLoader>();
	private String						mPage;
	private int							DELAY_TIME			= 30;
	private int							MAX_MEMORY_SIZE		= 30*1024*1024;

	private ImageCache() {
		if (mMemeoryCache == null) {
//			mHandler = new ClearImageCacheHandler(this);
//			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//
//				@Override
//				public void run() {
//					ULog.d( "totalMemory:" + Runtime.getRuntime().totalMemory());
//					if (MAX_MEMORY - Runtime.getRuntime().totalMemory() > getAvailMemory(BaseApplication.getInstance())
//							|| CACHE_SIZE - IMAGECACHE.mMemeoryCache.size() > getAvailMemory(BaseApplication.getInstance())
//							|| Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory() > MAX_MEMORY_SIZE) {
//						if (IMAGECACHE.mMemeoryCache.size() > 0) {
//							ULog.d( "开始清除图片缓存");
//							mHandler.sendEmptyMessage(CLEAR_IMAGE_CACHE);
//						}
//					}
//
//				}
//			}, DELAY_TIME, DELAY_TIME, TimeUnit.SECONDS);
			mMemeoryCache = new LruCache<String, Bitmap>(CACHE_SIZE) {
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes()*value.getHeight();
//					return 1;
				};

				@Override
				protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
//					ULog.d( "url:" + key + " value.getRowBytes()*value.getHeight():" + oldValue.getRowBytes()
//							* oldValue.getHeight());
					if (evicted) {
//						ULog.d( "entryRemoved : evicted :---" + evicted + "key --" + key + " oldValue--" + oldValue
//								+ "newValue--" + newValue);
						BitmapLoader bitmapLoader = mBitmapRecycles.get(key);
						if (bitmapLoader != null) {
							bitmapLoader.recycle(key);
						}
						if (oldValue != null && !oldValue.isRecycled()) {
							oldValue.recycle();
							oldValue = null;
						}
						mMemeoryCache.remove(key);
						mBitmapRecycles.remove(key);
						ULog.d(" mBitmapRecycles size :" + mBitmapRecycles.size() + " mMemeoryCache size :" + mMemeoryCache.size());
//						ULog.d(" mMemeoryCache size :" + mMemeoryCache.size());
//						for(BitmapLoader loader : mBitmapRecycles.values()) {
//							ULog.d( "mpage :" + loader.getPage() + " url :" + loader.getUrl());
//						}
					}
				}
			};
		}
	};

	private static long getAvailMemory(Context context) {// 获取android当前可用内存大小  
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem;// 将获取的内存大小规格化  
	}

	public static synchronized ImageCache getImageCache() {
		if (IMAGECACHE == null) {
			IMAGECACHE = new ImageCache();
		}
		return IMAGECACHE;
	}
	public int cacheSize(){
		return IMAGECACHE.mMemeoryCache.size();
	}

	public synchronized boolean hasUrl(String url) {
		return mUrls.contains(url);
	}

	public synchronized void addUrl(String url) {
		mUrls.add(url);
	}

	public synchronized void clearUrls() {
		mUrls.clear();
	}

	public synchronized void removeUrl(String url) {
		mUrls.remove(url);
	}

	public synchronized void add(String key, Bitmap bitmap, BitmapLoader bitmapRecycle) {
		if (!TextUtils.isEmpty(key) && bitmap != null) {
			mMemeoryCache.put(key, bitmap);
			mBitmapRecycles.put(key, bitmapRecycle);
		}
	}

	public synchronized void remove(String key) {
		if (!TextUtils.isEmpty(key)) {
			BitmapLoader bitmapLoader = mBitmapRecycles.get(key);
			if (bitmapLoader != null) {
				bitmapLoader.recycle();
				bitmapLoader = null;
			}
			mBitmapRecycles.remove(key);
			Bitmap bitmap = mMemeoryCache.get(key);
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
			}
			mMemeoryCache.remove(key);
		}
	}

	public synchronized Bitmap get(String key) {
		if (!TextUtils.isEmpty(key)) {
			return mMemeoryCache.get(key);
		}
		return null;
	}

	private class ClearImageCacheHandler extends Handler {
		private WeakReference<ImageCache>	mImageCacheReference;

		public ClearImageCacheHandler(ImageCache imageCache) {
			this.mImageCacheReference = new WeakReference<ImageCache>(imageCache);
		}

		@Override
		public void handleMessage(Message msg) {
			ImageCache imageCache = mImageCacheReference.get();
			if (imageCache != null) {
				switch (msg.what) {
				case CLEAR_IMAGE_CACHE:
					/*Activity topActivity = ActivityStack.getInstance().topActivity();
					if (topActivity != null) {
						mPage = topActivity.toString();
						ULog.d( "current activity " + mPage);
						for (int i = 0; i <  ActivityStack.getInstance().size(); i++) {
							Activity activity = ActivityStack.getInstance().ActivityAt(i);
							if(activity != null && !mPage.equals(activity.toString())){
								ImageManager.getInstance().recycleAllWithCache(activity.toString());
							}
						}
						for (int i = 0; i < TabActivityManager.getInstance().size(); i++) {
							ImageManager.getInstance().recycleAllWithCache(TabActivityManager.getInstance().get(i));
						}
						
					}
					else {
						mPage = BaseActivity.getCurrentActivity().toString();
						ULog.d( "current activity " + mPage);
						for (int i = 0; i < TabActivityManager.getInstance().size(); i++) {
							String activityName = TabActivityManager.getInstance().get(i);
							ULog.d( "activityName " + activityName);
							if(!mPage.equals(activityName)){
								ImageManager.getInstance().recycleAllWithCache(activityName);
							}
						}
					}
					ULog.d(" mBitmapRecycles size :" + mBitmapRecycles.size() + " mMemeoryCache size :" + mMemeoryCache.size());
					Iterator<BitmapLoader> values =  mBitmapRecycles.values().iterator();
					while (values.hasNext()) {
						BitmapLoader bitmapLoader = values.next();
						ULog.d( "page :" + bitmapLoader.getPage() + " url :" + bitmapLoader.getUrl());
						
					}*/
					break;

				default:
					break;
				}
			}
		}
	}

	public interface BitmapLoader {
		public void recycle(String url);

		public void recycle();

		public void reload();

		public void load();

		public String getUrl();

		public void reload(String url);

		public String getPage();
	}
}
