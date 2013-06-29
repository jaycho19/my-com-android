package com.dongfang.apad.net;

import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

import com.dongfang.apad.param.ComParams;
import com.dongfang.apad.util.DFException;
import com.dongfang.apad.util.ULog;

/**
 * 对平台的请求
 * 
 * @author dongfang
 */
public class HttpActions {
	public static final String	TAG	= HttpActions.class.getSimpleName();

	// private Http https;

	// private List<NameValuePair> list;

	public HttpActions(Context context) {
		// https = Http.getInstance(context);
		// list = new ArrayList<NameValuePair>();
	}

	/**
	 * 
	 * @param json
	 * @return
	 * 
	 *         1) 成功 {"TIWEI": {"MSGCODE" : "0" , "MSGCONTENT" : ""}} <br>
	 *         2) 失败 {"TIWEI" : {"MSGCODE" : "1" , "MSGCONTENT" : "错误信息"}}
	 * 
	 * @throws DFException
	 */
	public String getResponse(List<HeaderProperty> headers, String functonName, String jsonParam) throws DFException {
		if (null != headers) {
			for (HeaderProperty hp : headers) {
				ULog.d(TAG, "header = " + headers.toString());
			}
		}

		ULog.d(TAG, "functonName = " + functonName + ", jsonParam = " + jsonParam);

		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		soapEnvelope.implicitTypes = true;
		soapEnvelope.dotNet = true;
		SoapObject soapReq = new SoapObject(ComParams.NAME_SPACE, functonName);
		// SoapObject soapReq = new SoapObject("http://tempuri.org/", functonName);

		soapReq.addProperty("json", jsonParam);

		soapEnvelope.setOutputSoapObject(soapReq);
//		HttpTransportSE httpTransport = new HttpTransportSE(ComParams.URL_BASE, ComParams.HTTP_TIMEOUT);
		// HttpTransportSE httpTransport = new HttpTransportSE("http://service.jklaile.com/TiWeiService.asmx",
		// ComParams.HTTP_TIMEOUT);
		 HttpTransportSE httpTransport = new HttpTransportSE("http://192.168.0.110/TiWei/TiWeiService.asmx",
		 ComParams.HTTP_TIMEOUT);
		try {
			if (headers != null) {
				httpTransport.call(ComParams.NAME_SPACE + functonName, soapEnvelope, headers);
			}
			else {
				httpTransport.call(ComParams.NAME_SPACE + functonName, soapEnvelope);
			}

			Log.d(TAG, "++++" + soapEnvelope.getResponse().toString());

			SoapObject result = (SoapObject) soapEnvelope.bodyIn;
			if (result.hasProperty("AdminLogon")) {
				Object obj = result.getProperty("AdminLogon");
				if (obj != null && obj.getClass().equals(SoapPrimitive.class)) {
					SoapPrimitive j = (SoapPrimitive) result.getProperty("AdminLogon");
					String resultVariable = j.toString();
					return resultVariable;
				}
			}

			return soapEnvelope.getResponse().toString();

		} catch (Exception e) {
			throw new DFException(e);
		}
	}
}
