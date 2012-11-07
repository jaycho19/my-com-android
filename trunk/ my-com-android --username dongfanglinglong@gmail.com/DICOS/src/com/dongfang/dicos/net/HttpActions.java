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

	/** �������� */
	public String getPassword(String uName) {
		list.clear();
		// list.add(new BasicNameValuePair("uid", phoneNumber));
		list.add(new BasicNameValuePair("acc", uName));
		list.add(new BasicNameValuePair("from", "mobile"));
		return https.post(list, "http://www.dicos.com.cn/login/reset_pwd_ajax.php");
	}

	/** ע�� */
	public String register(String uName, String uPassword, String uNikeName) {
		list.clear();
		list.add(new BasicNameValuePair("acc", uName));
		list.add(new BasicNameValuePair("pwd", uPassword));
		list.add(new BasicNameValuePair("nick", uNikeName));
		list.add(new BasicNameValuePair("from", "mobile"));
		return https.post(list, "http://www.dicos.com.cn/login/register_ajaxV2.php");
	}

	/** ��֤ */
	// public String validate(String phoneNumber, String authCode) {
	// ULog.d(tag, "phoneNumber = " + phoneNumber);
	// ULog.d(tag, "authCode = " + authCode);
	//
	// // JSONObject js = new JSONObject();
	// // try {
	// // js.put(Actions.ACTIONS_KEY_ACT, Actions.ACTIONS_TYPE_VALIDATE);
	// // js.put(Actions.ACTIONS_KEY_MOBILE, phoneNumber);
	// // js.put(Actions.ACTIONS_KEY_CODE, authCode);
	// // js.put("from", "mobile");
	// // } catch (JSONException e) {
	// // ULog.e(tag, e.toString());
	// // }
	//
	// list.clear();
	// // list.add(new BasicNameValuePair(Actions.ACTIONS_KEY_ACT,
	// // Actions.ACTIONS_TYPE_VALIDATE));
	// list.add(new BasicNameValuePair("uid", phoneNumber));
	// list.add(new BasicNameValuePair("upwd", authCode));
	// list.add(new BasicNameValuePair("from", "mobile"));
	// // ULog.d(tag, js.toString());
	// return https.post(list, "http://www.dicos.com.cn/login/login_ajax.php");
	// }

	public String validate(String uName, String uPassword) {
		list.clear();
		list.add(new BasicNameValuePair("acc", uName));
		list.add(new BasicNameValuePair("pwd", uPassword));
		list.add(new BasicNameValuePair("from", "mobile"));
		return https.post(list, "http://www.dicos.com.cn/login/login_ajaxV2.php");
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
		return https.post(list, "http://www.dicos.com.cn/login/logout_ajax.php");
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
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/action\/8_1347870892.jpg","http:\/\/www.dicos.com.cn\/images\/app\/ac
	 *         t i o n \ / 7 _ 1 3 4 7 605021.jpg" ] <br>
	 *         ������ <br>
	 *         []
	 */
	public String getCurrentSeasonImgUrl() {
		return https.post(null, "http://www.dicos.com.cn/app/api/app_action.php");
	}

	/**
	 * 
	 * @return ���ظ�ʽ��json �ش�ֵ:<br>
	 *         cate:����<br>
	 *         key(��ֵ):�������<br>
	 *         value(ֵ):��������<br>
	 *         newest:����ͼ�n·��<br>
	 *         ��:<br>
	 *         �ɹ�<br>
	 *         {"cate": {"3":{"name":"\u4e3b\u9910","focus_img":
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal_category\/focus\/3_1349922212.jpg","blur_img":"http:\/\/www.dicos.com.cn\/images\/app\/meal_category\/blur\/3_1349
	 *         9 2 2 2 1 2 . j p g
	 *         " } , "11":{"name":"\u5e38\u6001\u5957\u9910","focus_img":
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal_category\/focus\/11_1349920688.jpg","blur_img":"http:\/\/www.dicos.com.cn\/images\/app\/meal_category\/blur\/11_134
	 *         9 9 2 0 6 8 8 . j p g " } }, "newest":
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal\/newest.jpg?1348647
	 *         3 7 0 " }
	 */
	public String getKaZiMeiWeiInfo() {
		return https.post(null, "http://www.dicos.com.cn/app/api/app_meal_category.php");
	}

	/**
	 * 
	 * @param cateId
	 * @return ���ظ�ʽ: json<br>
	 *         ��:<br>
	 *         �ɹ�<br>
	 *         [
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal\/3_1347873388.jpg","http:\/\/www.dicos.com.cn\/images\/app\/meal\/5_1
	 *         3 4 7 8 9 2 5 1 4 . j p g " ] ������<br>
	 *         []<br>
	 *         ����<br>
	 *         -999:��������
	 */
	public String getKaZiMeiWei_type(String cateId) {
		return https.get("http://www.dicos.com.cn/app/api/app_meal.php?cate=" + cateId, null);
	}

}
