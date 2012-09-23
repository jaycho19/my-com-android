package com.dongfang.dicos.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.dongfang.dicos.util.ULog;

public class HttpActions {

	public static final String	tag	= "HttpActions";

	private Https				https;
	private List<NameValuePair>	list;

	private static final String	KEY	= "key";

	public HttpActions(Context context) {
		https = new Https(context);
		list = new ArrayList<NameValuePair>();
	}

	/**
	 * ��¼��ȡ��֤��
	 */
	public String login(String phoneNumber) {
		ULog.d(tag, "phoneNumber = " + phoneNumber);

		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOGIN);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}
		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ��֤ */
	public String validate(String phoneNumber, String authCode) {
		ULog.d(tag, "phoneNumber = " + phoneNumber);
		ULog.d(tag, "authCode = " + authCode);

		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_VALIDATE);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
			js.put(Actions.ACTIONS_KEY_CODE, authCode);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** �ǳ� */
	public String logout(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOGOUT);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** �齱����СƱ��Ҫ������ */
	public String lottery(String code, String amount, String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERY);
			js.put(Actions.ACTIONS_KEY_CODE, code);
			js.put(Actions.ACTIONS_KEY_AMOUNT, amount);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** �г��ҵĳ齱��Ϣ Ҫ������ */
	public String lotteryHistory(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERYHISTORY);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ��⵱ǰ�齱��Ƿ���� */
	public String lotteryLegal(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERYLEGAL);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** �齱�������а� */
	public String lotteryRateList(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERYRATELIST);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** �񽱹��� */
	public String lotteryWinner(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERYWINNER);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ��Ʒ���� */
	public String lotteryDistributionInfo(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_LOTTERYDISTRIBUTIONINFO);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ��ȡ�ŵ��б� */
	public String restaurentList(String phoneNumber, String province, String city) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_RESTAURENTLIST);
			// js.put(Actions.ACTIONS_KEY_MOBILE, "15921890763");
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
			js.put(Actions.ACTIONS_KEY_PROVINCE, province);
			js.put(Actions.ACTIONS_KEY_CITY, city);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		String s = js.toString();
		ULog.d(tag, s);
		list.add(new BasicNameValuePair(KEY, s));
		return https.post(list, Https.URL_RESTAURENTLIST);
	}

	/** ǩ�� */
	public String sign(String id, String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_SIGN);
			js.put(Actions.ACTIONS_KEY_ID, id);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ǩ����ʷ */
	public String signHistory(String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_SIGNHISTORY);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/** ������� Ҫ������ */
	public String advice(String msg, String phoneNumber) {
		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_ADVICE);
			js.put(Actions.ACTIONS_KEY_MSG, msg);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}

		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list, Https.URL_ADVICE);
	}

	/**
	 * ��ȡip���ڵ�ַ
	 */
	public String ipArea(String phoneNumber) {
		ULog.d(tag, "phoneNumber = " + phoneNumber);

		JSONObject js = new JSONObject();
		try {
			js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_IPAREA);
			js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
		} catch (JSONException e) {
			ULog.e(tag, e.toString());
		}
		list.clear();
		list.add(new BasicNameValuePair(KEY, js.toString()));
		ULog.d(tag, js.toString());
		return https.post(list);
	}

	/**
	 * 6�� �����
	 * 
	 * @return �ɹ� <br>
	 *         [
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/action\/8_1347870892.jpg","http:\/\/www.dicos.com.cn\/images\/app\/action\/7_1347605021.jp
	 *         g " ] <br>
	 *         ������ <br>
	 *         []
	 */
	public String getCurrentSeasonImgUrl() {
		return https.post(null, "http://www.dicos.com.cn/app/api/app_action.php");
	}

}
