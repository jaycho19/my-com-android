package com.dongfang.dicos.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;

import com.dongfang.dicos.util.ULog;

/**
 * ����http����
 * 
 * @author dongfang
 * */
public class Https {
	private static final String	tag								= "Https";

	private static final String	HTTP_HEADER_CONTENT_TYPE_KEY	= "Content-Type";
	private static final String	HTTP_HEADER_CONTENT_TYPE_VALUE	= "application/x-www-form-urlencoded";

	private static final int	SET_CONNECTION_TIMEOUT			= 50000;
	private static final int	SET_SOCKET_TIMEOUT				= 200000;

	private static final String	url								= "http://222.73.127.95:8080/dicos/dicos/interface.jsp";

	private Context				context;

	public Https(Context context) {
		this.context = context;
	}

	/**
	 * ���ݲ���url����HTTP GET ���󣬷���InputStream
	 * 
	 * @param url
	 * @return InputStream
	 * */
	public String get(String jsToString) {
		try {

			ULog.d(tag, url + "?key=" + jsToString);
			// ����HttpGet����
			HttpGet request = new HttpGet(url + "?key=" + jsToString);
			ULog.d(tag, url + "?key=" + jsToString);

			// ����HttpClient����
			HttpClient client = this.getNewHttpClient();
			ULog.d(tag, url + "?key=" + jsToString);
			HttpResponse httpResponse = client.execute(request);
			ULog.d(tag, "get " + httpResponse.getStatusLine().getStatusCode());

			if (null != httpResponse && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ULog.d(tag, "post 200");
				return read(httpResponse);
			}
		} catch (Exception e) {
			ULog.d(tag, "get " + e.toString());
		}
		return "";
	}

	/**
	 * ���ݲ���url����HTTP POST ���󣬷���InputStream
	 * 
	 * @param url
	 * @return String
	 * */
	public String post(List<NameValuePair> list) {
		ULog.d(tag, url);
		try {
			// ����HttpPost����
			HttpPost request = new HttpPost(url);
			// ULog.d(tag,"null != request = " + (null != request));
			request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			// Header header = new BasicHeader(HTTP_HEADER_CONTENT_TYPE_KEY ,
			// HTTP_HEADER_CONTENT_TYPE_VALUE);
			// request.setHeader(header);

			// �������Ӷ���
			HttpClient client = this.getNewHttpClient();
			// ULog.d(tag,"null != client = " + (null != client));
			// ִ������
			HttpResponse httpResponse = client.execute(request);
			// ULog.d(tag,"null != httpResponse = " + (null != httpResponse));
			// ULog.d(tag,"HttpStatus = " +
			// httpResponse.getStatusLine().getStatusCode());

			// for (Header h : httpResponse.getAllHeaders()) {
			// ULog.d(tag, h.getName() + " ++ " + h.getValue());
			// for (HeaderElement s : h.getElements()) {
			// ULog.d(tag, s.getParameterCount() + " -- " + s.getValue());
			// }
			// }

			if (null != httpResponse && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				ULog.d(tag, "post 200");
				return read(httpResponse);
			}

		} catch (Exception e) {
			ULog.d(tag, "post " + e.toString());
		}
		return "";
	}

	private HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			HttpParams params = new BasicHttpParams();

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			// Set the default socket timeout (SO_TIMEOUT) // in
			// milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setConnectionTimeout(params, SET_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);
			HttpClient client = new DefaultHttpClient(ccm, params);
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			if (!wifiManager.isWifiEnabled()) {
				// ��ȡ��ǰ����ʹ�õ�APN�����
				Uri uri = Uri.parse("content://telephony/carriers/preferapn");
				Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
				if (mCursor != null && mCursor.moveToFirst()) {
					// �α�������һ����¼����ȻҲֻ��һ��
					String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
					if (proxyStr != null && proxyStr.trim().length() > 0) {
						HttpHost proxy = new HttpHost(proxyStr, 80);
						client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
					}
					mCursor.close();
				}
			}
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	/**
	 * Read http requests result from response .
	 * 
	 * @param response
	 *            : http response by executing httpclient
	 * 
	 * @return String : http response content
	 */
	private String read(HttpResponse response) {
		String result = "";
		HttpEntity entity = response.getEntity();

		InputStream inputStream;
		try {
			inputStream = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			result = new String(content.toByteArray(), "gbk").trim();
			ULog.d(tag, "read = " + result);
		} catch (Exception e) {
			ULog.e(tag, "read " + e.toString());
		}
		return result;
	}
}
