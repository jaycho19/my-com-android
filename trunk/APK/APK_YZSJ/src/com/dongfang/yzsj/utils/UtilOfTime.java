package com.dongfang.yzsj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

import com.dongfang.utils.ULog;

/**
 * 此工具类主要处理 时间，日期格式化输出，还有关于时间判断、应用的一些方法。 使用详情可以参考 例子 main()
 * 
 * @author yanghua
 */
public class UtilOfTime {
	private static final String TAG = UtilOfTime.class.getSimpleName();

	// 常用日期输出格式
	public final static String YYYYMMDD = "yyyyMMdd";
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	public final static String YYYYMMDDHHMM = "yyyyMMddHHmm";
	public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public final static String HHMMSS = "HHmmss";
	public final static String HH_MM_SS = "HH:mm:ss";

	/**
	 * 获取当前时间日期 以参数pattern格式返回
	 * 
	 * @param pattern
	 *            类似 "yyyy-MM-dd HH:mm"
	 * @return 类似 "yyyy-MM-dd HH:mm"
	 */
	public static String getDateWithSpecialFromat(String pattern) {
		return getDateWithSpecialFromat(pattern, 0);
	}

	/**
	 * 获取 以今天偏移了多少天 时间日期 以参数pattern格式返回
	 * 
	 * @param pattern
	 *            类似 "yyyy-MM-dd HH:mm"
	 * @param offsetDay
	 *            偏移的天数 类似 -1 1
	 * @return 类似 "yyyy-MM-dd HH:mm"
	 */
	public static String getDateWithSpecialFromat(String pattern, int offsetDay) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offsetDay);
		return new SimpleDateFormat(pattern).format(cal.getTime());
	}

	/**
	 * 获取当前时间日期
	 * 
	 * @return Date
	 */
	public static Date CurrentDate() {
		return new Date();
	}

	/**
	 * 把毫秒转换为以参数pattern格式输出的String
	 * 
	 * @param pattern
	 *            类似 "yyyy-MM-dd HH:mm"
	 * @param millisecond
	 *            毫秒
	 * @return 类似 "yyyy-MM-dd HH:mm"
	 */
	public static String getDateByMillisecondWithSpecialFromat(String pattern, String millisecond) {
		try {

			if (TextUtils.isEmpty(millisecond))
				return "";
			Date date = new Date(Long.valueOf(millisecond));
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String time = format.format(date);
			return time;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将秒转换成标准时分秒格式--> 时：分：秒 (00:00:00)
	 * 
	 * @param seconds
	 * @return 类似 hh:mm:ss
	 */
	public static String formatSeconds2Date(long seconds) {
		int hour = 0;
		int min = 0;
		int sec = 0;

		hour = (int) seconds / 3600;
		if (hour != 0) {
			int i = (int) seconds % 3600;
			min = i / 60;
			sec = i % 60;
		}
		else {
			min = (int) seconds / 60;
			sec = (int) seconds % 60;
		}

		String hourStr = (9 < hour) ? String.valueOf(hour) : "0" + hour;
		String minStr = (9 < min) ? String.valueOf(min) : "0" + min;
		String secStr = (9 < sec) ? String.valueOf(sec) : "0" + sec;

		return hourStr + ":" + minStr + ":" + secStr;
	}

	/**
	 * 将HH:mm:ss转换成秒数
	 * 
	 * @param time
	 * @return
	 */
	public static int formatDate2Seconds(String time) {
		String[] mytime = time.split(":");
		int hour = Integer.parseInt(mytime[0]);
		int min = Integer.parseInt(mytime[1]);
		int sec = Integer.parseInt(mytime[2]);
		int totalsec = hour * 3600 + min * 60 + sec;
		return totalsec;
	}

	/**
	 * 是否参数millisecond比当前的时间早
	 * 
	 * @param millisecond
	 *            类似 "2013-07-27 21:15:00"
	 * @return 如果返回true 就是比当前时间早，否则比当前时间晚
	 */
	public static boolean isBeforeCurrentTime(String millisecond) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			long time = sdf.parse(millisecond).getTime();
			long currentTime = new Date().getTime();
			ULog.i("11compare date: " + millisecond + "  compare time: " + time + "=========>  current time: "
					+ currentTime);
			return time < currentTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 输出现在或者预告显示样式
	 * 
	 * @param str_star
	 *            开始时间
	 * @param str_end
	 *            结束时间
	 * @param isForecast
	 *            是否是预告的时间
	 * @return 类似 07月24日09:20am-01:29pm
	 */
	public static String strDataDuration(String str_star, String str_end, boolean isForecast) {
		SimpleDateFormat sdfFormat = new java.text.SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		String rt = "";
		String start_MM = "";
		String start_DD = "";
		String start_H = "";
		String start_M = "";
		String end_H = "";
		String end_M = "";
		try {
			if (isForecast) {
				String str_time = str_star.split(" ")[0];
				start_MM = str_time.split("-")[1];
				start_DD = str_time.split("-")[2];
				start_H = sdfFormat.parse(str_star).getHours() + "";
				start_M = sdfFormat.parse(str_star).getMinutes() + "";
				end_H = sdfFormat.parse(str_end).getHours() + "";
				end_M = sdfFormat.parse(str_end).getMinutes() + "";

				ULog.d(returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M));
				rt = start_MM + "月" + start_DD + "日 " + returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M);
			}
			else {
				start_H = sdfFormat.parse(str_star).getHours() + "";
				start_M = sdfFormat.parse(str_star).getMinutes() + "";
				end_H = sdfFormat.parse(str_end).getHours() + "";
				end_M = sdfFormat.parse(str_end).getMinutes() + "";
				ULog.d(returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M));
				rt = returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M);
			}
		} catch (Throwable e) {
			e.printStackTrace();
			rt = "** -  **";
		}
		return rt;
	}

	/**
	 * 把24小时制，转成12小时制，并后面添加am或者pm
	 * 
	 * @param str_H
	 *            小时
	 * @param str_M
	 *            分钟
	 * @return 类似 09:20am
	 */
	public static String returnAPM2(String str_H, String str_M) {
		String returnValue = "";
		// if (Integer.parseInt(str_H) < 13) {
		// returnValue = formatData(str_H) + ":" + formatData(str_M) + "am";
		// }
		// else {
		// returnValue = formatData((Integer.parseInt(str_H) - 12) + "") + ":" + formatData(str_M) + "pm";
		// }
		returnValue = formatData(str_H) + ":" + formatData(str_M);
		return returnValue;
	}

	/**
	 * 
	 * @param str_H
	 *            小时
	 * @param str_M
	 *            分钟
	 * @return 类似 09:20am
	 */
	public static String returnAPM(String str_H, String str_M) {
		String returnValue = "";
		// if (Integer.parseInt(str_H) < 13) {
		returnValue = formatData(str_H) + ":" + formatData(str_M);
		// }
		// else {
		// returnValue = formatData((Integer.parseInt(str_H) - 12) + "") + ":" + formatData(str_M) + "pm";
		// }
		return returnValue;
	}

	/**
	 * 把各位数的时间 如7，前面加上0，输出为07
	 * 
	 * @param data
	 * @return 类似 07
	 */
	public static String formatData(String data) {
		if (data.length() < 2) {
			return ("0" + data);
		}
		else {
			return data;
		}
	}

	/**
	 * 使用各个工具类例子
	 * 
	 * @param args
	 */
	public void test() {
		ULog.d("getDateWithSpecialFromat(YYYY_MM_DD) = " + getDateWithSpecialFromat(YYYY_MM_DD));
		ULog.d("getDateWithSpecialFromat(YYYY_MM_DD, -1) = " + getDateWithSpecialFromat(YYYY_MM_DD, -1));
		ULog.d("getDateWithSpecialFromat(YYYY_MM_DD, 1) = " + getDateWithSpecialFromat(YYYY_MM_DD, 1));
		ULog.d("CurrentDate() = " + CurrentDate());
		String currentTimeMS = String.valueOf(CurrentDate().getTime());
		ULog.d("getDateByMillisecondWithSpecialFromat(YYYY_MM_DD_HH_MM, currentTimeMS) = "
				+ getDateByMillisecondWithSpecialFromat(YYYY_MM_DD_HH_MM, currentTimeMS));

		long currentS = CurrentDate().getHours() * 3600 + CurrentDate().getMinutes() * 60 + CurrentDate().getSeconds();
		ULog.d("formatSeconds2Date(currentS) = " + formatSeconds2Date(currentS));
		String time = formatSeconds2Date(currentS);
		ULog.d("formatDate2Seconds(time) = " + formatDate2Seconds(time));
		ULog.d("isBeforeCurrentTime(2013-07-25 21:15:00) = " + isBeforeCurrentTime("2013-07-25 21:15:00"));

		ULog.d("returnAPM(9,20) = " + returnAPM("9", "20"));
		ULog.d("returnAPM(18,20) = " + returnAPM("9", "20"));

		ULog.d("formatData(9) = " + formatData("9"));
		ULog.d("formatData(12) = " + formatData("12"));

	}

}