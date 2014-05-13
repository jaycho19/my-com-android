/*
 * Copyright 2011 Sina.
 *
 * Licensed under the Apache License and Weibo License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.open.weibo.com
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dongfang.dicos.sina;

import java.io.IOException;

import android.content.Context;

/**
 * Encapsulation main Weibo APIs, Include: 1. getRquestToken , 2.
 * getAccessToken, 3. url request. Implements a weibo api as a asynchronized
 * way. Every object used this runner should implement interface
 * RequestListener.
 * 
 * @author (luopeng@staff.sina.com.cn zhangjie2@staff.sina.com.cn 官方微博：WBSDK
 *         http://weibo.com/u/2791136085)
 */
public class AsyncWeiboRunner {

	private Weibo	mWeibo;

	public AsyncWeiboRunner(Weibo weibo) {
		this.mWeibo = weibo;
	}

	public void request(final Context context, final String url, final WeiboParameters params, final String httpMethod,
			final RequestListener listener) {

		new MyThread(context, url, params, httpMethod, listener).start();
		// new Thread(){
		// @Override public void run() {
		// try {
		// String resp = mWeibo.request(context, url, params, httpMethod,
		// mWeibo.getAccessToken());
		// listener.onComplete(resp);
		// } catch (WeiboException e) {
		// listener.onError(e);
		// }
		// }
		// }.run();

	}

	public void post(final Context context, final String url, String msg, String key, String token,
			final RequestListener listener) {
		new MyPost(context, url, msg, key, token, listener).start();
	}

	class MyPost extends Thread {
		final Context			context;
		final String			url;
		final String			msg;
		final String			key;
		final String			token;
		final RequestListener	listener;

		public MyPost(Context context, String url, String msg, String key, String token, RequestListener listener) {
			this.context = context;
			this.url = url;
			this.msg = msg;
			this.key = key;
			this.token = token;
			this.listener = listener;
		}

		@Override
		public void run() {
			try {
				mWeibo.loadStorage(context);

				String resp = UtilSina.post(context, url, msg, key, token);

				listener.onComplete(resp);
			} catch (WeiboException e) {
				listener.onError(e);
			}
		}
	}

	class MyThread extends Thread {

		final Context			context;
		final String			url;
		final WeiboParameters	params;
		final String			httpMethod;
		final RequestListener	listener;

		public MyThread(Context context, String url, WeiboParameters params, String httpMethod, RequestListener listener) {
			this.context = context;
			this.url = url;
			this.params = params;
			this.httpMethod = httpMethod;
			this.listener = listener;
		}

		@Override
		public void run() {
			try {
				mWeibo.loadStorage(context);
				String resp = mWeibo.request(context, url, params, httpMethod, mWeibo.getAccessToken());
				listener.onComplete(resp);
			} catch (WeiboException e) {
				listener.onError(e);
			}
		}

	}

	public static interface RequestListener {

		public void onComplete(String response);

		public void onIOException(IOException e);

		public void onError(WeiboException e);

	}

}
