/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongfang.net.http;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;
import android.text.TextUtils;

import com.dongfang.net.http.client.HttpGetCache;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.net.http.client.HttpRequest.HttpMethod;
import com.dongfang.net.http.client.callback.DefaultHttpRedirectHandler;
import com.dongfang.net.http.client.callback.FileDownloadHandler;
import com.dongfang.net.http.client.callback.HttpRedirectHandler;
import com.dongfang.net.http.client.callback.RequestCallBackHandler;
import com.dongfang.net.http.client.callback.StringDownloadHandler;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.OtherUtils;
import com.dongfang.utils.ULog;
import com.dongfang.utils.core.CompatibleAsyncTask;
import com.dongfang.yzsj.bean.LoginBean;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;

public class HttpHandler<T> extends CompatibleAsyncTask<Object, Object, Object> implements RequestCallBackHandler {

	private final AbstractHttpClient client;
	private final HttpContext context;

	private final StringDownloadHandler mStringDownloadHandler = new StringDownloadHandler();
	private final FileDownloadHandler mFileDownloadHandler = new FileDownloadHandler();

	private HttpRedirectHandler httpRedirectHandler;

	public void setHttpRedirectHandler(HttpRedirectHandler httpRedirectHandler) {
		this.httpRedirectHandler = httpRedirectHandler;
	}

	private HttpRequestBase request;
	private final RequestCallBack<T> callback;

	private int retriedTimes = 0;
	private String fileSavePath = null;
	private boolean isDownloadingFile;
	private boolean autoResume = false; // Whether the downloading could continue from the point of interruption.
	private boolean autoRename = false; // Whether rename the file by response header info when the download completely.
	private String charset; // The default charset of response header info.

	public HttpHandler(AbstractHttpClient client, HttpContext context, String charset, RequestCallBack<T> callback) {
		this.client = client;
		this.context = context;
		this.callback = callback;
		this.charset = charset;
	}

	private String _getRequestUrl;// if not get method, it will be null.
	private long expiry = HttpGetCache.getDefaultExpiryTime();

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	// 执行请求
	private Object sendRequest(HttpRequestBase request) throws HttpException {
		if (autoResume && isDownloadingFile) {
			File downloadFile = new File(fileSavePath);
			long fileLen = 0;
			if (downloadFile.isFile() && downloadFile.exists()) {
				fileLen = downloadFile.length();
			}
			if (fileLen > 0) {
				request.setHeader("RANGE", "bytes=" + fileLen + "-");
			}
		}

		boolean retry = true;
		HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
		while (retry) {
			IOException exception = null;
			try {
				if (request.getMethod().equals(HttpRequest.HttpMethod.GET.toString())) {
					_getRequestUrl = request.getURI().toString();
				}
				else {
					_getRequestUrl = null;
				}
				ULog.d(_getRequestUrl);
				// if (_getRequestUrl != null) {
				// String result = HttpUtils.sHttpGetCache.get(_getRequestUrl);
				// if (result != null) {
				// return result;
				// }
				// }

				Object responseBody = null;
				if (!isCancelled()) {
					HttpResponse response = client.execute(request, context);
					responseBody = handleResponse(response);
				}
				return responseBody;
			} catch (UnknownHostException e) {
				exception = e;
				retry = retryHandler.retryRequest(exception, ++retriedTimes, context);
			} catch (IOException e) {
				exception = e;
				retry = retryHandler.retryRequest(exception, ++retriedTimes, context);
			} catch (NullPointerException e) {
				exception = new IOException(e.getMessage());
				retry = retryHandler.retryRequest(exception, ++retriedTimes, context);
			} catch (HttpException e) {
				throw e;
			} catch (Exception e) {
				exception = new IOException(e.getMessage());
				retry = retryHandler.retryRequest(exception, ++retriedTimes, context);
			} finally {
				if (!retry && exception != null) {
					throw new HttpException(exception);
				}
			}
		}
		return null;
	}

	@Override
	protected Object doInBackground(Object... params) {
		if (params != null && params.length > 3) {
			fileSavePath = String.valueOf(params[1]);
			isDownloadingFile = fileSavePath != null;
			autoResume = (Boolean) params[2];
			autoRename = (Boolean) params[3];
		}
		try {
			publishProgress(UPDATE_START);
			request = (HttpRequestBase) params[0];
			Object responseBody = sendRequest(request);
			publishProgress(UPDATE_SUCCESS, responseBody);
		} catch (HttpException e) {
			publishProgress(UPDATE_FAILURE, e, e.getMessage());
		}

		return null;
	}

	private final static int UPDATE_START = 1;
	private final static int UPDATE_LOADING = 2;
	private final static int UPDATE_FAILURE = 3;
	private final static int UPDATE_SUCCESS = 4;

	@SuppressWarnings("unchecked")
	@Override
	protected void onProgressUpdate(Object... values) {
		int update = Integer.valueOf(String.valueOf(values[0]));
		switch (update) {
		case UPDATE_START:
			if (callback != null) {
				callback.onStart();
			}
			break;
		case UPDATE_LOADING:
			if (callback != null) {
				callback.onLoading(Long.valueOf(String.valueOf(values[1])), Long.valueOf(String.valueOf(values[2])));
			}
			break;
		case UPDATE_FAILURE:
			this.stop();
			if (callback != null) {
				callback.onFailure((HttpException) values[1], (String) values[2]);
			}
			break;
		case UPDATE_SUCCESS:
			if (callback != null) {
				callback.onSuccess((T) values[1]);
			}
			break;
		default:
			break;
		}
		super.onProgressUpdate(values);
	}

	private Object handleResponse(HttpResponse response) throws HttpException, IOException {
		if (response == null) {
			throw new HttpException("response is null");
		}
		StatusLine status = response.getStatusLine();
		int statusCode = status.getStatusCode();
		if (statusCode < 300) {
			HttpEntity entity = response.getEntity();
			Object responseBody = null;
			if (entity != null) {
				lastUpdateTime = SystemClock.uptimeMillis();
				if (isDownloadingFile) {
					String responseFileName = autoRename ? OtherUtils.getFileNameFromHttpResponse(response) : null;
					responseBody = mFileDownloadHandler.handleEntity(entity, this, fileSavePath, autoResume,
							responseFileName);
				}
				else {

					// Set charset from response header info if it's exist.
					String responseCharset = OtherUtils.getCharsetFromHttpResponse(response);
					charset = TextUtils.isEmpty(responseCharset) ? charset : responseCharset;

					String sResponseBody = mStringDownloadHandler.handleEntity(entity, this, charset);
					// -------- token 失效时，根据ip重新获取一次token ,by dongfang, 2013-10-16 17:38:40----------------------
					if (isNewToken(sResponseBody)) {
						String newUrl = _getRequestUrl.replace(User.SHAREDPREFERENCES_ACCESS_TOKEN_OLD,
								User.SHAREDPREFERENCES_ACCESS_TOKEN_NEW);
						request.setURI(URI.create(newUrl));
						return handleResponse(client.execute(request, context));
					}
					// ---------------------------------------------------------------------------------------------------
					// 不缓存数据 by dongfang, 2013-10-16 17:38:40
					// HttpUtils.sHttpGetCache.put(_getRequestUrl, (String) responseBody, expiry);
					// ---------------------------------------------------------------------------------------------------
					responseBody = sResponseBody;
				}
			}
			return responseBody;
		}
		else if (statusCode == 301 || statusCode == 302) {
			if (httpRedirectHandler == null) {
				httpRedirectHandler = new DefaultHttpRedirectHandler();
			}
			HttpRequestBase request = httpRedirectHandler.getDirectRequest(response);
			if (request != null) {
				return this.sendRequest(request);
			}
		}
		else if (statusCode == 416) {
			throw new HttpException(statusCode, "maybe the file has downloaded completely");
		}
		else {
			throw new HttpException(statusCode, status.getReasonPhrase());
		}
		return null;
	}

	private boolean mStop = false;

	/**
	 * stop request task.
	 */
	@Override
	public void stop() {
		this.mStop = true;
		if (request != null && !request.isAborted()) {
			request.abort();
		}
		if (!this.isCancelled()) {
			this.cancel(true);
		}
	}

	public boolean isStop() {
		return mStop;
	}

	private long lastUpdateTime;

	@Override
	public boolean updateProgress(long total, long current, boolean forceUpdateUI) {
		if (mStop) {
			return !mStop;
		}
		if (callback != null) {
			if (forceUpdateUI) {
				publishProgress(UPDATE_LOADING, total, current);
			}
			else {
				long currTime = SystemClock.uptimeMillis();
				if (currTime - lastUpdateTime >= callback.getRate()) {
					lastUpdateTime = currTime;
					publishProgress(UPDATE_LOADING, total, current);
				}
			}
		}
		return !mStop;
	}

	/**token过期重新获取token*/
	private boolean isNewToken(String result) {
		try {
			LoginBean bean = new com.google.gson.Gson().fromJson(result, LoginBean.class);
			if (!bean.isResult() && !bean.isSuccess()) {
				String url_token = ComParams.HTTP_GET_TOKEN_BY_UUID + UUID.randomUUID().toString().replace("-", "");
				HttpRequest request = new HttpRequest(HttpMethod.GET, url_token);

				HttpResponse response = client.execute(request, context);
				String responseBody = (String) handleResponse(response);

				LoginBean newbean = new com.google.gson.Gson().fromJson(responseBody, LoginBean.class);

				if (null != newbean && newbean.isSuccess() && !TextUtils.isEmpty(newbean.getToken())) {
					// 保存token
					User.SHAREDPREFERENCES_ACCESS_TOKEN_NEW = newbean.getToken();
					return true;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return false;
	}

}
