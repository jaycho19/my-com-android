package com.dongfang.apad.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.DFException;
import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

/**
 * 各种http请求
 * 
 * @author dongfang
 * */
public class Http {
	private static final String	TAG								= Http.class.getSimpleName();
	private static final String	HTTP_HEADER_CONTENT_TYPE_KEY	= "Content-Type";
	private static final String	HTTP_HEADER_CONTENT_TYPE_VALUE	= "application/x-www-form-urlencoded";
	private static final int	SET_CONNECTION_TIMEOUT			= 5000;
	private static final int	SET_SOCKET_TIMEOUT				= 20000;
	private static Http			http;
	private Context				context;

	private Http(Context context) {
		this.context = context;
	}

	/** 获取Https类实例对象 */
	public static Http getInstance(Context context) {
		if (null == http) {
			http = new Http(context);
		}
		return http;
	}

	/**
	 * Prior to Android 2.2 (Froyo), this class had some frustrating bugs. In particular, calling close() on a readable
	 * InputStream could poison the connection pool. Work around this by disabling connection pooling:
	 */
	private void disableConnectionReuseIfNecessary() {
		// Work around pre-Froyo bugs in HTTP connection reuse.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * 拼接接口参数
	 * 
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口参数集
	 * @return
	 */
	private String initGetUrl(String url, List<NameValuePair> list) {
		if (url.contains("?")) {
			return url + "&" + Util.encodeUrl(list, true);
		}
		else {
			return url + "?" + Util.encodeUrl(list, true);
		}
	}

	private String initGetUrl(String url, String entity) {
		if (url.contains("?")) {
			return url + "&" + entity;
		}
		else {
			return url + "?" + entity;
		}
	}

	private String read(HttpURLConnection httpURLConnection) throws DFException {
		String result = "";
		try {
			InputStream inputStream = null;

			int statusCode = httpURLConnection.getResponseCode();
			if (statusCode < 200 || 300 < statusCode) {
				inputStream = httpURLConnection.getErrorStream();
				ULog.d(TAG, "httpURLConnection.getContentLength() = " + httpURLConnection.getContentLength());
				if (null == inputStream || httpURLConnection.getContentLength() < 1)
					throw new DFException(statusCode);
			}
			else {
				inputStream = httpURLConnection.getInputStream();
			}
			String contentEncoding = httpURLConnection.getContentEncoding();
			if (contentEncoding != null && contentEncoding.toLowerCase(Locale.US).indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			byte[] sBuffer = new byte[1024];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}

			String contentType = httpURLConnection.getContentType();
			String charset = ((contentType.indexOf("charset=") != -1) && !TextUtils.isEmpty(charset = contentType
					.substring(contentType.indexOf("charset=") + 8))) ? charset : "UTF-8";
			ULog.v(TAG, "charset=" + charset);

			result = new String(content.toByteArray(), charset).trim();
		} catch (IOException e) {
			throw new DFException(context, DFException.HTTP_READ_IO_EXCEPTION);
		}

		return result;
	}

	private InputStream getInputStream(HttpURLConnection httpURLConnection) throws DFException {
		InputStream inputStream = null;
		try {
			int statusCode = httpURLConnection.getResponseCode();
			if (statusCode < 200 || 300 < statusCode) {
				inputStream = httpURLConnection.getErrorStream();
				ULog.d(TAG, "httpURLConnection.getContentLength() = " + httpURLConnection.getContentLength());
				if (null == inputStream || httpURLConnection.getContentLength() < 1)
					throw new DFException(statusCode);
			}
			else {
				inputStream = httpURLConnection.getInputStream();
			}
			String contentEncoding = httpURLConnection.getContentEncoding();
			if (contentEncoding != null && contentEncoding.toLowerCase(Locale.US).indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}
		} catch (IOException e) {
			throw new DFException(context, DFException.HTTP_READ_IO_EXCEPTION);
		}
		return inputStream;
	}

	/**
	 * @param type
	 *            http类型：get 或 post
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @param conTimeOut
	 *            连接超时时间
	 * @param socTimeOut
	 *            socket超时时间
	 * @return
	 * @throws DFException
	 */
	private HttpsURLConnection getHttpsURLConnection(String type, String url, String entity,
			List<NameValuePair> addHeader, int conTimeOut, int socTimeOut) throws DFException {

		ULog.i(TAG, "getHttpsURLConnection type        = " + type);
		ULog.i(TAG, "getHttpsURLConnection url         = " + url);
		ULog.i(TAG, "getHttpsURLConnection conTimeOut  = " + conTimeOut);
		ULog.i(TAG, "getHttpsURLConnection socTimeOut  = " + socTimeOut);

		disableConnectionReuseIfNecessary();

		if ("GET".equalsIgnoreCase(type) && !TextUtils.isEmpty(entity)) {
			url = initGetUrl(url, entity);
			ULog.i(TAG, "getHttpsURLConnection url         = " + url);
		}

		try {
			HttpsURLConnection connection = (HttpsURLConnection) (new URL(url)).openConnection(java.net.Proxy.NO_PROXY);
			connection.setRequestMethod(type); // 默认就是GET
			if ("POST".equalsIgnoreCase(type)) {
				connection.setDoInput(true);
				connection.setDoOutput(true);
				if (!TextUtils.isEmpty(entity)) {
					connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE_KEY, HTTP_HEADER_CONTENT_TYPE_VALUE);
					connection.getOutputStream().write(entity.getBytes());
					connection.getOutputStream().flush();
					connection.getOutputStream().close();
				}
			}

			if (null != addHeader && addHeader.size() > 0) {
				for (NameValuePair nvp : addHeader) {
					connection.setRequestProperty(nvp.getName(), nvp.getValue());
					ULog.i(TAG,
							"getHttpsURLConnection AddHeaderList        = " + nvp.getName() + " : " + nvp.getValue());
				}
			}

			if (null != addHeader && addHeader.size() > 0) {
				for (NameValuePair nvp : addHeader) {
					connection.setRequestProperty(nvp.getName(), nvp.getValue());
					ULog.i(TAG, "getHttpsURLConnection list        = " + nvp.getName() + "=" + nvp.getValue());
				}
			}

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new MyTrustManager[] { new MyTrustManager() }, new SecureRandom());
			connection.setSSLSocketFactory(sslContext.getSocketFactory());
			connection.setHostnameVerifier(new AllowAllHostnameVerifier());

			conTimeOut = (1 > conTimeOut) ? SET_CONNECTION_TIMEOUT : conTimeOut;
			socTimeOut = (1 > socTimeOut) ? SET_SOCKET_TIMEOUT : socTimeOut;

			connection.setConnectTimeout(conTimeOut);
			connection.setReadTimeout(socTimeOut);

			connection.setDefaultUseCaches(false);
			connection.setInstanceFollowRedirects(true);// 设置本次连接是否自动处理重定向

			// if (null != list && list.size() > 0) {
			// StringBuilder sb = new StringBuilder();
			// for (NameValuePair nvp : list) {
			// sb.append("\r\n").append(nvp.getName()).append(nvp.getValue()).append("&");
			// }
			// sb.deleteCharAt(sb.length() - 1);
			// connection.setRequestProperty("Content-Length",
			// String.valueOf(sb.toString().getBytes().length));
			// OutputStream os = connection.getOutputStream();
			// os.write(sb.toString().getBytes());
			// os.flush();
			// os.close();
			// }
			connection.connect();

			int statusCode = connection.getResponseCode();
			ULog.i(TAG, "getHttpURLConnection statusCode = " + statusCode);

			/** 手动重定向操作 */
			if (HttpURLConnection.HTTP_MOVED_TEMP == statusCode || HttpURLConnection.HTTP_MOVED_PERM == statusCode) {
				return getHttpsURLConnection(connection.getHeaderField("Location"), entity, addHeader);
			}

			// if (200 > statusCode || statusCode > 300) {
			// throw new TVException(context, statusCode);
			// }

			return connection;
		} catch (MalformedURLException e) {
			throw new DFException(context, DFException.HTTP_GET_HTTPS_URL_CONN_MALFORMED_URL_EXCEPTION);
		} catch (IOException e) {
			throw new DFException(context, DFException.HTTP_GET_HTTPS_URL_CONN_IO_EXCEPTION);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new DFException(context, DFException.HTTP_GET_HTTPS_URL_CONN_NO_SUCH_ALG_EXCEPTION);
		} catch (KeyManagementException e) {
			e.printStackTrace();
			throw new DFException(context, DFException.HTTP_GET_HTTPS_URL_CONN_KEYMANAGEMENT_EXCEPTION);
		}
	}

	/**
	 * @param type
	 *            http类型：get 或 post
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @param conTimeOut
	 *            连接超时时间
	 * @param socTimeOut
	 *            socket超时时间
	 * @return
	 * @throws DFException
	 */
	private HttpURLConnection getHttpURLConnection(String type, String url, String entity,
			List<NameValuePair> addHeader, int conTimeOut, int socTimeOut) throws DFException {
		ULog.i(TAG, "getHttpURLConnection type        = " + type);
		ULog.i(TAG, "getHttpURLConnection url         = " + url);
		ULog.i(TAG, "getHttpURLConnection conTimeOut  = " + conTimeOut);
		ULog.i(TAG, "getHttpURLConnection socTimeOut  = " + socTimeOut);

		disableConnectionReuseIfNecessary();
		if ("GET".equalsIgnoreCase(type) && !TextUtils.isEmpty(entity)) {
			url = initGetUrl(url, entity);
			ULog.i(TAG, "getHttpsURLConnection url         = " + url);
		}
		try {
			HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection(java.net.Proxy.NO_PROXY);
			connection.setRequestMethod(type); // 默认就是GET
			if ("POST".equalsIgnoreCase(type)) {
				connection.setDoInput(true);
				connection.setDoOutput(true);
				if (!TextUtils.isEmpty(entity)) {
					connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE_KEY, HTTP_HEADER_CONTENT_TYPE_VALUE);
					connection.getOutputStream().write(entity.getBytes());
					connection.getOutputStream().flush();
					connection.getOutputStream().close();
				}
			}

			if (null != addHeader && addHeader.size() > 0) {
				for (NameValuePair nvp : addHeader) {
					connection.setRequestProperty(nvp.getName(), nvp.getValue());
					ULog.i(TAG,
							"getHttpsURLConnection AddHeaderList        = " + nvp.getName() + " : " + nvp.getValue());
				}
			}

			conTimeOut = (1 > conTimeOut) ? SET_CONNECTION_TIMEOUT : conTimeOut;
			socTimeOut = (1 > socTimeOut) ? SET_SOCKET_TIMEOUT : socTimeOut;

			connection.setConnectTimeout(conTimeOut);
			connection.setReadTimeout(socTimeOut);

			connection.setDefaultUseCaches(false);
			connection.setInstanceFollowRedirects(true);// 设置本次连接是否自动处理重定向
			connection.connect();

			int statusCode = connection.getResponseCode();
			ULog.i(TAG, "getHttpURLConnection statusCode = " + statusCode);

			/** 手动重定向操作 */
			if (HttpURLConnection.HTTP_MOVED_TEMP == statusCode || HttpURLConnection.HTTP_MOVED_PERM == statusCode) {
				return getHttpURLConnection(connection.getHeaderField("Location"), entity, addHeader);
			}

			// if (200 > statusCode || statusCode > 300) {
			// throw new TVException(context, statusCode);
			// }

			return connection;
		} catch (MalformedURLException e) {
			throw new DFException(context, DFException.HTTP_GET_HTTP_URL_CONN_MALFORMED_URL_EXCEPTION);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DFException(context, DFException.HTTP_GET_HTTP_URL_CONN_IO_EXCEPTION);
		}
	}

	/**
	 * 
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @param conTimeOut
	 *            连接超时时间
	 * @param socTimeOut
	 *            socket超时时间
	 * @return
	 * @throws DFException
	 */
	public HttpsURLConnection getHttpsURLConnection(String url, String entity, List<NameValuePair> addHheader,
			int conTimeOut, int socTimeOut) throws DFException {
		return getHttpsURLConnection("GET", url, entity, addHheader, conTimeOut, socTimeOut);
	}

	/**
	 * 
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @return
	 * @throws DFException
	 */
	public HttpsURLConnection getHttpsURLConnection(String url, String entity, List<NameValuePair> addHheader)
			throws DFException {
		return getHttpsURLConnection(url, entity, addHheader, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	/**
	 * 
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @param conTimeOut
	 *            连接超时时间
	 * @param socTimeOut
	 *            socket超时时间
	 * @return
	 * @throws DFException
	 */
	public HttpURLConnection getHttpURLConnection(String url, String entity, List<NameValuePair> addHheader,
			int conTimeOut, int socTimeOut) throws DFException {
		return getHttpURLConnection("GET", url, entity, addHheader, conTimeOut, socTimeOut);
	}

	/**
	 * 
	 * @param url
	 *            接口地址
	 * @param list
	 *            接口请求参数
	 * @param addHeader
	 *            Http协议头参数
	 * @return
	 * @throws DFException
	 */
	public HttpURLConnection getHttpURLConnection(String url, String entity, List<NameValuePair> addHheader)
			throws DFException {
		return getHttpURLConnection(url, entity, addHheader, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	/**
	 * 根据参数url?param进行HTTP GET 请求，返回String
	 * 
	 * @param url
	 * @param param
	 * @param timeout
	 *            为true链接超时时间为5s，否则为2s;可参照get(List<NameValuePair> list)默认链接超时时间为5s
	 * @return String
	 * @throws DFException
	 * */
	public String get(String url, String entity, int conTimeOut, int soketTimeout) throws DFException {
		if (url.startsWith("https"))
			return read(getHttpsURLConnection(url, entity, null, conTimeOut, soketTimeout));
		return read(getHttpURLConnection(url, entity, null, conTimeOut, soketTimeout));
	}

	/**
	 * 根据参数url?param进行HTTP GET 请求，返回String
	 * 
	 * @param url
	 * @param param
	 * @param timeout
	 *            为true链接超时时间为5s，否则为2s;可参照get(List<NameValuePair> list)默认链接超时时间为5s
	 * @return String
	 * @throws DFException
	 * */
	public String get(String url, String entity, List<NameValuePair> addHeaders, int conTimeOut, int soketTimeout)
			throws DFException {
		if (url.startsWith("https"))
			return read(getHttpsURLConnection(url, entity, addHeaders, conTimeOut, soketTimeout));
		return read(getHttpURLConnection(url, entity, addHeaders, conTimeOut, soketTimeout));
	}

	public String get(String url, String entity) throws DFException {
		return get(url, entity, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	public String get(String url, String entity, List<NameValuePair> addheaders) throws DFException {
		return get(url, entity, addheaders, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	public String get(String entity) throws DFException {
		return get(ComParams.URL_GET, entity, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	/**
	 * 根据参数url进行HTTP POST 请求，返回String
	 * 
	 * @param list
	 * @param url
	 * @param conTimeOut
	 * @param soketTimeout
	 * 
	 * @return String
	 * @throws DFException
	 * */
	public String post(String url, String entity, int conTimeOut, int soketTimeout) throws DFException {
		ULog.d(TAG, url);
		if (url.startsWith("https"))
			return read(getHttpsURLConnection("POST", url, entity, null, conTimeOut, soketTimeout));
		return read(getHttpURLConnection("POST", url, entity, null, conTimeOut, soketTimeout));
	}

	public String post(String url, String entity, List<NameValuePair> addHheader) throws DFException {
		if (url.startsWith("https"))
			return read(getHttpsURLConnection("POST", url, entity, addHheader, SET_CONNECTION_TIMEOUT,
					SET_SOCKET_TIMEOUT));
		return read(getHttpURLConnection("POST", url, entity, addHheader, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT));

	}

	public InputStream post(String url, String entity) throws DFException {
		if (url.startsWith("https"))
			return getInputStream(getHttpsURLConnection("POST", url, entity, null, SET_CONNECTION_TIMEOUT,
					SET_SOCKET_TIMEOUT));
		return getInputStream(getHttpURLConnection("POST", url, entity, null, SET_CONNECTION_TIMEOUT,
				SET_SOCKET_TIMEOUT));
	}

	public String post(String entity) throws DFException {
		return post(ComParams.URL_POST, entity, SET_CONNECTION_TIMEOUT, SET_SOCKET_TIMEOUT);
	}

	// public HttpClient getNewHttpClient(Context context) {
	// try {
	// KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	// trustStore.load(null, null);
	//
	// SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	// sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	//
	// HttpParams params = new BasicHttpParams();
	//
	// HttpConnectionParams.setConnectionTimeout(params, 10000);
	// HttpConnectionParams.setSoTimeout(params, 10000);
	//
	// HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	// HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	//
	// SchemeRegistry registry = new SchemeRegistry();
	// registry.register(new Scheme("http",
	// PlainSocketFactory.getSocketFactory(), 80));
	// registry.register(new Scheme("https", sf, 443));
	//
	// ClientConnectionManager ccm = new ThreadSafeClientConnManager(params,
	// registry);
	//
	// // Set the default socket timeout (SO_TIMEOUT) // in
	// // milliseconds which is the timeout for waiting for data.
	// HttpConnectionParams.setConnectionTimeout(params, 5000);
	// HttpConnectionParams.setSoTimeout(params, 5000);
	// HttpClient client = new DefaultHttpClient(ccm, params);
	// WifiManager wifiManager = (WifiManager)
	// context.getSystemService(Context.WIFI_SERVICE);
	// if (!wifiManager.isWifiEnabled()) {
	// Uri uri = Uri.parse("content://telephony/carriers/preferapn");
	// Cursor mCursor = context.getContentResolver().query(uri, null, null,
	// null, null);
	// if (mCursor != null && mCursor.moveToFirst()) {
	// String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
	// if (proxyStr != null && proxyStr.trim().length() > 0) {
	// HttpHost proxy = new HttpHost(proxyStr, 80);
	// client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
	// }
	// mCursor.close();
	// }
	// }
	// return client;
	// } catch (Exception e) {
	// return new DefaultHttpClient();
	// }
	// }

	// public String readHttpResponse(HttpResponse response) throws TVException
	// {
	// String result = "";
	// try {
	// InputStream inputStream = response.getEntity().getContent();
	// Header header = response.getFirstHeader("Content-Encoding");
	// if (header != null &&
	// header.getValue().toLowerCase(Locale.US).indexOf("gzip") > -1) {
	// inputStream = new GZIPInputStream(inputStream);
	// }
	// ByteArrayOutputStream content = new ByteArrayOutputStream();
	//
	// // Read response into a buffered stream
	// int readBytes = 0;
	// byte[] sBuffer = new byte[1024 * 2];
	// while ((readBytes = inputStream.read(sBuffer)) != -1) {
	// content.write(sBuffer, 0, readBytes);
	// }
	//
	// String content_type =
	// response.getFirstHeader(HTTP.CONTENT_TYPE).getValue();
	// String charset = null;
	// if (-1 != content_type.indexOf("charset=")) {
	// charset = content_type.substring(content_type.indexOf("charset=") + 8);
	// }
	// if (TextUtils.isEmpty(charset))
	// result = new String(content.toByteArray(), HTTP.UTF_8).trim();
	// else
	// result = new String(content.toByteArray(), charset).trim();
	// } catch (IOException e) {
	// throw new TVException(context,
	// TVException.HTTP_READ_HTTP_RESPONSE_IO_EXCEPTION);
	// }
	// return result;
	// }

	private class MyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	// private class MySSLSocketFactory extends SSLSocketFactory {
	// SSLContext sslContext = SSLContext.getInstance("TLS");
	//
	// public MySSLSocketFactory(KeyStore truststore) throws
	// NoSuchAlgorithmException, KeyManagementException,
	// KeyStoreException, UnrecoverableKeyException {
	// super(truststore);
	//
	// TrustManager tm = new X509TrustManager() {
	// public void checkClientTrusted(X509Certificate[] chain, String authType)
	// throws CertificateException {}
	//
	// public void checkServerTrusted(X509Certificate[] chain, String authType)
	// throws CertificateException {}
	//
	// public X509Certificate[] getAcceptedIssuers() {
	// return null;
	// }
	// };
	//
	// sslContext.init(null, new TrustManager[] { tm }, null);
	// }
	//
	// @Override
	// public Socket createSocket(Socket socket, String host, int port, boolean
	// autoClose) throws IOException,
	// UnknownHostException {
	// return sslContext.getSocketFactory().createSocket(socket, host, port,
	// autoClose);
	// }
	//
	// @Override
	// public Socket createSocket() throws IOException {
	// return sslContext.getSocketFactory().createSocket();
	// }
	//
	// }

	// public String post(String url, List<NameValuePair> list, final String
	// param, int conTimeOut, int soketTimeout)
	// throws TVException {
	// if (null != list && list.size() > 0)
	// url = url + "?" + Util.encodeUrl(list,true);
	// ULog.d(TAG, "post url=" + url);
	// try {
	// // 创建HttpPost对象
	// HttpPost request = new HttpPost(url);
	// request.addHeader(HTTP_HEADER_CONTENT_TYPE_KEY,
	// HTTP_HEADER_CONTENT_TYPE_VALUE);
	// ULog.v("QAZWSX", "param =" + param.length());
	// if (!TextUtils.isEmpty(param)) {
	// request.setEntity(new StringEntity(param, HTTP.UTF_8));
	// }
	// // 创建连接对象
	// HttpClient client = this.getNewHttpClient(conTimeOut, soketTimeout);
	// // 执行连接
	// HttpResponse httpResponse = client.execute(request);
	// int statusCode = httpResponse.getStatusLine().getStatusCode();
	// ULog.v("QAZWSX", "param =" +
	// httpResponse.getEntity().getContentLength());
	// if (HttpStatus.SC_OK != statusCode) {
	// throw new TVException(context, statusCode);
	// }
	// return read(httpResponse);
	// } catch (TVException e) {
	// throw new TVException(e);
	// } catch (UnsupportedEncodingException e) {
	// throw new TVException(context,
	// TVException.HTTP_POST_UNSUPPORTED_ENCODING_EXCEPTION);
	// } catch (ClientProtocolException e) {
	// throw new TVException(context,
	// TVException.HTTP_POST_CLIENT_PROTOCOL_EXCEPTION);
	// } catch (IOException e) {
	// throw new TVException(context, TVException.HTTP_POST_IO_EXCEPTION);
	// }
	// }
	/**
	 * Read http requests result from response .
	 * 
	 * @param response
	 *            : http response by executing httpclient
	 * 
	 * @return String : http response content
	 */
	// private String read(HttpResponse response) throws TVException {
	// String result = "";
	// try {
	// InputStream inputStream = response.getEntity().getContent();
	// Header header = response.getFirstHeader("Content-Encoding");
	// if (header != null && header.getValue().toLowerCase().indexOf("gzip") >
	// -1) {
	// inputStream = new GZIPInputStream(inputStream);
	// }
	// ByteArrayOutputStream content = new ByteArrayOutputStream();
	//
	// // Read response into a buffered stream
	// int readBytes = 0;
	// byte[] sBuffer = new byte[1024];
	// while ((readBytes = inputStream.read(sBuffer)) != -1) {
	// content.write(sBuffer, 0, readBytes);
	// }
	//
	// String content_type =
	// response.getFirstHeader(HTTP.CONTENT_TYPE).getValue();
	// int index = content_type.indexOf("charset=");
	// String charset = null;
	// if (index != -1) {
	// charset = content_type.substring(content_type.indexOf("charset=") + 8);
	// }
	//
	// // ULog.v(TAG, "charset=" + charset);
	//
	// if (TextUtils.isEmpty(charset))
	// result = new String(content.toByteArray(), HTTP.UTF_8).trim();
	// else
	// result = new String(content.toByteArray(), charset).trim();
	//
	// // ULog.v(TAG, "read = " + result);
	// } catch (IOException e) {
	// throw new TVException(context, TVException.HTTP_READ_IO_EXCEPTION);
	// }
	// return result;
	// }

	// private HttpClient getNewHttpClient(int conTimeOut, int soketTimeout) {
	// HttpParams params = new BasicHttpParams();
	// HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	// HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	// HttpConnectionParams.setConnectionTimeout(params, conTimeOut);
	// HttpConnectionParams.setSoTimeout(params, soketTimeout);
	//
	// SchemeRegistry registry = new SchemeRegistry();
	// registry.register(new Scheme("http",
	// PlainSocketFactory.getSocketFactory(), 80));
	// ClientConnectionManager ccm = new ThreadSafeClientConnManager(params,
	// registry);
	//
	// HttpClient client = new DefaultHttpClient(ccm, params);
	//
	// WifiManager wifiManager = (WifiManager)
	// context.getSystemService(Context.WIFI_SERVICE);
	// if (!wifiManager.isWifiEnabled()) {
	// // 获取当前正在使用的APN接入点
	// Uri uri = Uri.parse("content://telephony/carriers/preferapn");
	// Cursor mCursor = context.getContentResolver().query(uri, null, null,
	// null, null);
	// if (mCursor != null && mCursor.moveToFirst()) {
	// // 游标移至第一条记录，当然也只有一条
	// String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
	// if (proxyStr != null && proxyStr.trim().length() > 0) {
	// HttpHost proxy = new HttpHost(proxyStr, 80);
	// client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
	// }
	// mCursor.close();
	// }
	// }
	// return client;
	// }
}
