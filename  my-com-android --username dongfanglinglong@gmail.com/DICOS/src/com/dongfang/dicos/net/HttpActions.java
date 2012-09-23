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
	 * 登录获取验证码
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

	/** 验证 */
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

	/** 登出 */
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

	/** 抽奖（输小票）要传号码 */
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

	/** 列出我的抽奖信息 要传号码 */
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

	/** 检测当前抽奖活动是否结束 */
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

	/** 抽奖次数排行榜 */
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

	/** 获奖公布 */
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

	/** 奖品发放 */
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

	/** 获取门店列表 */
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

	/** 签到 */
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

	/** 签到历史 */
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

	/** 意见反馈 要传号码 */
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
	 * 获取ip所在地址
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
	 * 6． 当季活动
	 * 
	 * @return 成功 <br>
	 *         [
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/action\/8_1347870892.jpg","http:\/\/www.dicos.com.cn\/images\/app\/action\/7_1347
	 *         605021.jpg" ] <br>
	 *         无资料 <br>
	 *         []
	 */
	public String getCurrentSeasonImgUrl() {
		return https.post(null, "http://www.dicos.com.cn/app/api/app_action.php");
	}

	/**
	 * 
	 * @return 返回格式：json 回传值:<br>
	 *         cate:分类<br>
	 *         key(键值):分类号码<br>
	 *         value(值):分类名称<br>
	 *         newest:最新图檔路径<br>
	 *         例:<br>
	 *         成功<br>
	 *         {"cate":{"1":"\u65e9\u9910"},"newest":
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal\/3_1347873388.jpg"}
	 */
	public String getKaZiMeiWeiInfo() {
		return https.post(null, "http://www.dicos.com.cn/app/api/app_meal_category.php");
	}

	/**
	 * 
	 * @param cateId
	 * @return 返回格式: json<br>
	 *         例:<br>
	 *         成功<br>
	 *         [
	 *         "http:\/\/www.dicos.com.cn\/images\/app\/meal\/3_1347873388.jpg","http:\/\/www.dicos.com.cn\/images\/app\/meal\/5_1347892514.jp
	 *         g " ] 无资料<br>
	 *         []<br>
	 *         其他<br>
	 *         -999:参数错误
	 */
	public String getKaZiMeiWei_type(String cateId) {
		return https.get("http://www.dicos.com.cn/app/api/app_meal.php?cate=" + cateId, null);
	}

}
