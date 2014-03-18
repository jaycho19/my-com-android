package com.next.lottery.utils;

import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dongfang.utils.ULog;
import com.next.lottery.utils.ImageCache.BitmapLoader;

import android.annotation.SuppressLint;
import android.util.SparseArray;

public class ImageManager {
	private static final String												TAG					= "ImageManager";
	private static ImageManager												IMAGEMANAGER;
	private LinkedHashMap<String, SparseArray<SparseArray<BitmapLoader>>>	mImageViewContainer	= new LinkedHashMap<String, SparseArray<SparseArray<BitmapLoader>>>();
	private ExecutorService													mExecutorService	= Executors
																										.newFixedThreadPool(2);										// 固定5个线程来执行任务
	public static final int													MODEL_CACHE_VIEW	= 1;
	public static final int													MODEL_NO_CACHE_VIEW	= 2;
	private LinkedHashMap<String,SparseArray<String>>							mCacheUrls = new LinkedHashMap<String, SparseArray<String>>();
	private ImageManager() {}

	public static ImageManager getInstance() {
		if (IMAGEMANAGER == null) {
			IMAGEMANAGER = new ImageManager();
		}
		return IMAGEMANAGER;
	}

	public ExecutorService getFixedThreadPool() {
		return mExecutorService;
	}

	public void add(String key, BitmapLoader value, int index, int position) {
		add(key, value, index, MODEL_NO_CACHE_VIEW, position);
	}

	public void add(String key, BitmapLoader value, int index, int model, int position) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page == null) {
			page = new SparseArray<SparseArray<BitmapLoader>>();
			mImageViewContainer.put(key, page);
		}
		SparseArray<BitmapLoader> indexData = page.get(index);
		if (indexData == null) {
			indexData = new SparseArray<ImageCache.BitmapLoader>();
			page.put(index, indexData);
		}

		if (model == MODEL_NO_CACHE_VIEW) {
			if (indexData.indexOfValue(value) == -1) {
				indexData.put(indexData.size(), value);
			}
			ULog.d("MODEL_NO_CACHE_VIEW add key == " + key + "index == " + index + " value == " + value
					+ " url = " + value.getUrl() + " position :" + indexData.size());
		}
		else if (model == MODEL_CACHE_VIEW) {
			indexData.put(position, value);
			ULog.d("MODEL_CACHE_VIEW put key == " + key + "index == " + index + " value == " + value + " url = "
					+ value.getUrl() + " position : " + position);
		}
	}

	public void removeAll(String key) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null && page.size() > 0) {
			for (int i = 0; i < page.size(); i++) {
				int indexKey  = page.keyAt(i);
				SparseArray<BitmapLoader> indexData = page.get(indexKey);
				if (indexData != null && indexData.size() > 0) {
					for (int j = 0; j < indexData.size(); j++) {
						if (indexData.get(j) != null) {
							indexData.get(j).recycle();
						}
					}
					indexData.clear();
				}
				indexData = null;
			}
			page.clear();
			page = null;
		}
		mImageViewContainer.remove(key);
	}

	public void removeAll(String key, int index) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null && page.size() > 0) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.size() > 0) {
				for (int j = 0; j < indexData.size(); j++) {
					if (indexData.get(j) != null) {
						indexData.get(j).recycle();
					}
				}
				indexData.clear();
			}
			indexData = null;
		}
		page.remove(index);
		page.put(index, null);
	}

	public void recycleAll(String key) {
		ULog.d("recycleAll key == " + key);
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null && page.size() > 0) {
			for (int i = 0; i < page.size(); i++) {
				SparseArray<BitmapLoader> indexData = page.get(i);
				if (indexData != null && indexData.size() > 0) {
					for (int j = 0; j < indexData.size(); j++) {
						if (indexData.get(j) != null) {
							indexData.get(j).recycle();
						}
					}
				}

			}
		}
	}

	public void recycleAllWithCache(String key) {
		ULog.d("recycleAll key == " + key);
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null && page.size() > 0) {
			for (int i = 0; i < page.size(); i++) {
				int indexKey  = page.keyAt(i);
				SparseArray<BitmapLoader> indexData = page.get(indexKey);
				if (indexData != null && indexData.size() > 0) {
					for (int j = 0; j < indexData.size(); j++) {
						if (indexData.get(j) != null) {
							indexData.get(j).recycle();
							ImageCache.getImageCache().remove(indexData.get(j).getUrl());
							ULog.d("key " + key + "position " + indexKey + "indexData.get(j).getUrl()" + indexData.get(j).getUrl());
						}
					}
				}

			}
			//			ULog.d("recycleAllWithCache("+ key +") cacheSize :" + ImageCache.getImageCache().cacheSize() + " imageSize : " + pageSize);
		}
	}

	public void recycleAll(String... key) {
		ULog.d("recycleAll key == " + key);
		if (key != null && key.length > 0) {
			for (int k = 0; k < key.length; k++) {
				SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key[k]);
				if (page != null && page.size() > 0) {
					for (int i = 0; i < page.size(); i++) {
						SparseArray<BitmapLoader> indexData = page.get(i);
						if (indexData != null && indexData.size() > 0) {
							for (int j = 0; j < indexData.size(); j++) {
								if (indexData.get(j) != null) {
									indexData.get(j).recycle();
								}
							}
						}

					}
				}
			}
		}
	}

	public void recycleAll(String key, int index) {
		ULog.d("recycleAll key == " + key + " index === " + index);
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.size() > 0) {
				for (int i = 0; i < indexData.size(); i++) {
					if (indexData.get(i) != null) {
						indexData.get(i).recycle();
					}
				}
			}
		}
	}

	public void recycleAllWithCache(String key, int index) {
		ULog.d("recycleAll key == " + key + " index === " + index);
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.size() > 0) {
				for (int i = 0; i < indexData.size(); i++) {
					if (indexData.get(i) != null) {
						indexData.get(i).recycle();
						ImageCache.getImageCache().remove(indexData.get(i).getUrl());
					}
				}
			}
		}
	}

	@SuppressLint("NewApi")
	public void remove(String key, BitmapLoader value, int index) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null) {
				indexData.removeAt(indexData.indexOfValue(value));
			}
		}
	}

	public void reloadAll(String key) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null && page.size() > 0) {
			for (int i = 0; i < page.size(); i++) {
				SparseArray<BitmapLoader> indexData = page.get(i);
				if (indexData != null && indexData.size() > 0) {
					for (int j = 0; j < indexData.size(); j++) {
						if (indexData.get(j) != null) {
							indexData.get(j).reload();
						}
					}
				}

			}
		}
	}

	public void reloadAll(String key, int index) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.size() > 0) {
				for (int i = 0; i < indexData.size(); i++) {
					if (indexData.get(i) != null) {
						indexData.get(i).reload();
					}
				}
			}
		}
	}

	public void loadAll(String key, int index) {
		ULog.d("loadAll key == " + key + " index === " + index);
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.size() > 0) {
				for (int i = 0; i < indexData.size(); i++) {
					if (indexData.get(i) != null) {
						indexData.get(i).load();
					}
				}
			}
		}
	}

	public void recycle(String key, BitmapLoader loader, int index) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && loader != null) {
				loader.recycle();
			}
		}
	}

	public void recycle(String key, int index, int position) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.get(position) != null) {
				indexData.get(position).recycle();
			}
		}
	}
	public void recycleWithCache(String key, int index, int position) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.get(position) != null) {
				indexData.get(position).recycle();
				ImageCache.getImageCache().remove(indexData.get(position).getUrl());
			}
		}
	}

	public void load(String key, int index, int position) {
		SparseArray<SparseArray<BitmapLoader>> page = mImageViewContainer.get(key);
		if (page != null) {
			SparseArray<BitmapLoader> indexData = page.get(index);
			if (indexData != null && indexData.get(position) != null) {
				indexData.get(position).load();
			}
		}
	}
	
	public void addCacheUrl(String key,String url){
		SparseArray<String> page = mCacheUrls.get(key);
		if (page == null) {
			page = new SparseArray<String>();
			mCacheUrls.put(key, page);
		}
		if(page.indexOfValue(url) == -1){
			page.put(page.size(), url);
		}
	}
	public void recycleCacheUrl(String key){
		SparseArray<String> page = mCacheUrls.get(key);
		if (page != null && page.size() >0 ) {
			for (int i = 0; i < page.size(); i++) {
				ULog.d("key " + key +  "url()" + page.get(i));
				ImageCache.getImageCache().remove(page.get(i));
			}
			page.clear();
		}
		if(page != null){
			mCacheUrls.remove(page);
		}
	}
}
