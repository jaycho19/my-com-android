package com.dongfang.apad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dongfang.apad.net.ApnManager;
import com.dongfang.apad.param.ComParams;

/**
 * 
 * @author dongfang, Emerson,zhangjiao
 * 
 */
@SuppressLint("SimpleDateFormat")
public class Util {
	private static final String	TAG					= Util.class.getSimpleName();
	private static final int	ERROR				= -1;
	public static boolean		isPlayWarning		= false;						// 视频播放的网络提醒
	public static boolean		isDownloadWarning	= false;						// 下载的网络提醒

	/**
	 * 更新视频下载路径值
	 * 
	 * @param context
	 * @param path
	 * @return 更新是否成功
	 */
	public static boolean saveDownloadPath(Context context, String path) {
		ULog.d(TAG, "DownloadPath change to : " + path);
		return context.getSharedPreferences(ComParams.SHAREDPREFERENCES_USER_SETTINGS, Context.MODE_PRIVATE).edit()
				.putString(ComParams.SHAREDPREFERENCES_USER_DOWNLOAD_PATH, path).commit();
	}

	/**
	 * 获取视频下载路径值
	 * 
	 * @param context
	 * @return 视频下载路径，默认返回 {@link ComParams#DOWNLOAD_DEFAULT_SDCARD_PATH}
	 */
	public static String getDownloadPath(Context context) {
		SharedPreferences sp = context.getSharedPreferences(ComParams.SHAREDPREFERENCES_USER_SETTINGS,
				Context.MODE_PRIVATE);
		return sp.getString(ComParams.SHAREDPREFERENCES_USER_DOWNLOAD_PATH, ComParams.DOWNLOAD_DEFAULT_SDCARD_PATH);
	}

	/** 判断是否有网络 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable());

	}

	public static String getNetType(Context context) {
		String netType = "";

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if (info != null && info.isAvailable()) {
			String name = info.getTypeName();
			ULog.d(TAG, "Network is " + name);
			if (name.equalsIgnoreCase("mobile")) {
				netType = new ApnManager(context).getCurrentAPNType();
			}
			else {
				netType = name;
			}
		}

		return netType;
	}

	/** 转换dip为px **/
	public static int convertDIP2PX(Context context, int dip) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
	}

	/** 转换px为dip **/
	public static int convertPX2DIP(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * SDCARD是否存
	 */
	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机内部剩余存储空间
	 * 
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * 获取手机内部总的存储空间
	 * 
	 * @return
	 */
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * 获取SDCARD剩余存储空间
	 * 
	 * @return
	 */
	public static long getAvailableExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		}
		else {
			return ERROR;
		}
	}

	/**
	 * 获取SDCARD总的存储空间
	 * 
	 * @return
	 */
	public static long getTotalExternalMemorySize() {
		if (externalMemoryAvailable()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		}
		else {
			return ERROR;
		}
	}

	/** 终端名称 */
	public static String getTerminalName() {
		return Build.MODEL;
	}

	/** 终端android 版本 */
	public static String getTerminalVersion() {
		return "Android " + Build.VERSION.RELEASE;
	}

	/** 客户端版本 */
	public static String getAPKVersion(Context context) {
		PackageManager localPackageManager;
		try {
			localPackageManager = context.getPackageManager();
			String str1 = context.getPackageName();
			String str2 = localPackageManager.getPackageInfo(str1, 0).versionName;
			return str2;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查是否有外部存储
	 * 
	 * @return 是否挂载外部存储
	 */
	public static boolean isExternalStorageAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 客户端语种
	 * 
	 * @param context
	 * @return 1: 简体中文 ;2: 繁体中文; 3: 英文
	 */
	public static String getLanguage(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage(); // 获得语言码
		if (language.equalsIgnoreCase("zh")) {
			if (locale.getCountry().equalsIgnoreCase("TW"))
				return "2";
			return "1";
		}
		else if (language.equalsIgnoreCase("en")) {
			return "3";
		}
		else {
			return "1";
		}

	}

	/**
	 * 获取 IMSI id
	 * <p>
	 * Requires Permission: {@link android.Manifest.permission#READ_PHONE_STATE
	 * READ_PHONE_STATE}
	 * 
	 * @param context
	 * @return Returns the unique subscriber ID, for example, the IMSI for a GSM
	 *         phone. Return null if it is unavailable.
	 * */
	public static String getIMSI(Context context) {
		String imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		return TextUtils.isEmpty(imsi) ? "" : imsi;
	}

	/**
	 * 获取终端号
	 * 
	 * Returns the unique device ID, for example, the IMEI for GSM and the MEID
	 * for CDMA phones. Return null if device ID is not available.
	 * 
	 * <p>
	 * Requires Permission: {@link android.Manifest.permission#READ_PHONE_STATE
	 * READ_PHONE_STATE}
	 */
	public static String getIMEI(Context context) {
		String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return TextUtils.isEmpty(imei) ? "NULL" : imei;
	}

	/**
	 * 获取当前日期，格式为yyyyMMdd
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate() {
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
		return sdf.format(dt);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getMoreTime(int d) {
		Date date = new Date();
		new SimpleDateFormat("yyyy-MM-dd").format(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, d);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * 获取时间，格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateWithHMS() {
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前日期，格式为yyyy年MM月dd日 HH:mm
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDefDate() {
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH：mm:ss");
		// SimpleDateFormat sdf = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
		return sdf.format(dt);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTimeStamp() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	}

	public static Date CurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/** 毫秒转成： 年-月-日 */
	/*
	 * public static final String stringToDate(String string) { Date date =
	 * null; try { long dt = Long.parseLong(string); date = new Date();
	 * date.setTime(dt); } catch (Exception e) { e.printStackTrace(); } return
	 * new SimpleDateFormat("yyyy-MM-dd").format(date); }
	 */
	/**
	 * 返回一个有逗号(,)隔开的字符串
	 * 
	 * @param array
	 * @return the {@code String} representation of {@code array}.
	 */
	public static String toString(String[] array) {
		if (array == null) {
			return "null"; //$NON-NLS-1$
		}
		if (array.length == 0) {
			return ""; //$NON-NLS-1$
		}
		StringBuilder sb = new StringBuilder(array.length * 5);
		sb.append(array[0]);
		for (int i = 1; i < array.length; i++) {
			sb.append(","); //$NON-NLS-1$
			sb.append(array[i]);
		}
		return sb.toString();
	}

	public static String toString(List<String> list) {
		if (list == null) {
			return "null"; //$NON-NLS-1$
		}
		int length = list.size();
		if (length == 0) {
			return ""; //$NON-NLS-1$
		}
		StringBuilder sb = new StringBuilder(length * 5);
		sb.append(list.get(0));
		for (int i = 1; i < length; i++) {
			sb.append(","); //$NON-NLS-1$
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	public static String getDateTimeByMillisecond(String str) {
		Date date = new Date(Long.valueOf(str));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}

	/**
	 * 将秒转换成标准时分秒格式--> 时：分：秒 (00:00:00)
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {
		int hour = 0;
		int min = 0;
		int sec = 0;

		hour = (int) time / 3600;
		if (hour != 0) {
			int i = (int) time % 3600;
			min = i / 60;
			sec = i % 60;
		}
		else {
			min = (int) time / 60;
			sec = (int) time % 60;
		}

		String hourStr = (9 < hour) ? String.valueOf(hour) : "0" + hour;
		String minStr = (9 < min) ? String.valueOf(min) : "0" + min;
		String secStr = (9 < sec) ? String.valueOf(sec) : "0" + sec;

		return hourStr + ":" + minStr + ":" + secStr;
	}

	/**
	 * /** Check whether the characters are all numbers
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsPhoneNumber(String str) {
		ULog.v(TAG, TAG + "  IsPhoneNumber(): " + str);
		if (Pattern.compile("^\\+?[0-9]*").matcher(str).matches())
			return true;
		else
			return false;
	}

	/**
	 * Checking whether the characters is a email
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsEmailAddr(String str) {
		ULog.v(TAG, "IsEmailAddr(): " + str);
		String estr = "^[a-zA-Z0-9._-]{1,}@[a-zA-Z0-9_-]{1,}(.([a-zA-Z0-9]){2,3}){1,3}$";
		if (Pattern.compile(estr).matcher(str).matches()) {
			String[] suffix = str.substring(str.indexOf("@") + 1).split("\\.");
			ULog.v(TAG, "suffix.length = " + suffix.length);
			if (suffix.length <= 1) {
				return false;
			}
			for (int i = 0; i < COUNTRY_SUFFIX.length; i++) {
				if (COUNTRY_SUFFIX[i].equals(suffix[suffix.length - 1])) {
					return true;
				}
			}
			return false;
		}
		else
			return false;
	}

	/**
	 * Checking whether the characters is legal characters
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsLegalName(String str) {
		String estr = "[a-zA-Z0-9.'-_ \u4e00-\u9fa5]{0,16}";
		if (Pattern.compile(estr).matcher(str).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Get the date
	 * 
	 * @param distance
	 *            The number of days from today, Positive value represents the
	 *            days after today, Negative value represents the days before
	 *            today, Zero represents today.
	 * @return
	 */
	public static String getDate(int distance, boolean stripe) {
		String date = "";
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, distance);
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		if (month.length() == 1) {
			month = "0" + month;
		}

		if (day.length() == 1) {
			day = "0" + day;
		}

		if (stripe) {
			date = year + "-" + month + "-" + day;
		}
		else {
			date = year + month + day;
		}
		return date;
	}

	/**
	 * Get the external Ip address of the devices.
	 * 
	 * @return
	 */
	public static String getExternalIpAddr() {
		String ipAddr = "";
		Process pro;
		try {
			pro = Runtime.getRuntime().exec("curl ifconfig.me");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			if (!TextUtils.isEmpty(ipAddr = buf.readLine())) {
				ULog.d(TAG, "curl ifconfig.me --> " + ipAddr);
				return ipAddr;
			}
			pro.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (TextUtils.isEmpty(ipAddr)) {
			HttpURLConnection http = null;
			try {
				// 27.120.101.66
				URL url = new URL("http://ifconfig.me/ip");
				http = (HttpURLConnection) url.openConnection();
				http.setRequestMethod("GET");
				BufferedReader buf = new BufferedReader(new InputStreamReader(http.getInputStream()));
				while (!TextUtils.isEmpty(ipAddr = buf.readLine())) {
					ULog.d(TAG, " IP --> " + ipAddr);
					return ipAddr;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (http != null)
					http.disconnect();
			}

		}
		return ipAddr;
	}

	public static final String[]	COUNTRY_SUFFIX	= { "aero", "asia", "biz", "cat", "com", "coop", "edu", "eu",
			"gov", "info", "int", "jobs", "mil", "mobi", "museum", "name", "net", "org", "pro", "tel", "travel", "xxx",
			"ac", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "aw", "ax", "az", "ba", "bb",
			"bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca",
			"cc", "cd", "cf", "cg", "ch", "ck", "cl", "cm", "cn", "co", "cr", "cs", "cu", "cv", "cx", "cy", "cz", "de",
			"dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr",
			"ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw",
			"gy", "hk", "hm", "hn", "ht", "hu", "id", "ie", "il", "in", "io", "iq", "ir", "je", "jm", "jo", "jp", "kg",
			"ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv",
			"ly", "ma", "mc", "md", "me", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu",
			"mv", "mw", "mx", "my", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa",
			"pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru",
			"rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su",
			"sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv",
			"tw", "tz", "ua", "ug", "uk", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye",
			"yt", "za", "zm", "zw"					};

	/** 隐藏键盘 */
	public static void hideInput(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	/** 毫秒转成年-月-日 */
	public static String stringToDate(String string) {
		if (TextUtils.isEmpty(string))
			return "";
		Date date = null;
		try {
			long dt = Long.parseLong(string);
			date = new Date();
			date.setTime(dt);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	/** 毫秒转成年-月-日 小时-分 */
	public static String stringToDateWithHourMin(String string) {
		if (TextUtils.isEmpty(string))
			return "";
		Date date = null;
		try {
			long dt = Long.parseLong(string);
			date = new Date();
			date.setTime(dt);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

	/** 日期比较(日期为毫秒形式) */
	public static boolean isValidTime(String string) {
		if (TextUtils.isEmpty(string))
			return false;
		Date dateNow = new Date();
		Date date = null;
		try {
			long dt = Long.parseLong(string);
			date = new Date();
			date.setTime(dt);
			if (dateNow.before(date)) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 秒转成 时：分：秒 (00:00:00) */
	public static String secondToTime(int num) {
		String str = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (num >= 3600) {
			hour = num / 3600;
			if (num % 3600 > 60) {
				minute = (num % 3600) / 60;
				second = (num % 3600) % 60;
			}
			else {
				minute = 0;
				second = num % 3600;
			}

		}
		else if (num < 60) {
			hour = 0;
			minute = 0;
			second = num;
		}
		else {
			hour = 0;
			minute = num / 60;
			second = num % 60;
		}

		str = "0" + hour + ":";

		if (minute < 10) {
			str = str + "0" + minute + ":";
		}
		else {
			str = str + minute + ":";
		}

		if (second < 10) {
			str = str + "0" + second;
		}
		else {
			str = str + second;
		}

		return str;
	}

	/** 判断是否需要升级 */
	public static boolean isUpdate(Context context, String version) {
		String v = getAPKVersion(context);
		if (v.equals(version))
			return false;
		else {
			try {
				String[] vv = version.split("\\.");
				String[] _v = v.split("\\.");

				for (int i = 0; i < (Math.min(3, Math.min(vv.length, _v.length))); i++) {
					if (Integer.valueOf(vv[i]) > Integer.valueOf(_v[i]))
						return true;
				}
			} catch (Exception e) {
				return false;
			}
		}

		return false;
	}

	/**
	 * 缩小图片
	 * <p>
	 * if width willing be zoom is bigger than default draw width, then return
	 * default draw immediately;
	 * </P>
	 * 
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		if (null == drawable) {
			return drawable;
		}

		int width = drawable.getIntrinsicWidth();
		if (w > width) {
			return drawable;
		}

		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);

		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
		oldbmp.recycle();
		BitmapDrawable bd = new BitmapDrawable(newbmp);

		return bd;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * ListView item 的间隔背景设置 供listView 的 Adapter 的getView()方法调用
	 */
	public static void setListViewItemIntervalBackground(int position, View convertView) {
		if (0 == position % 2) {
			convertView.setBackgroundColor(0);
		}
		else {
			convertView.setBackgroundColor(0);
		}
	}

	/**
	 * ListView item 的间隔背景设置 供listView调用
	 */
	public static void setListViewItemIntervalBackground(ListView lv) {
		int itemCount = lv.getCount();
		for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
			View itemView = lv.getChildAt(itemIndex);
			setListViewItemIntervalBackground(itemIndex, itemView);
		}
	}

	/**
	 * 设置listview的高度为所有item的和，不显示滑动进度条 <br>
	 * 注意：listview的item布局的最外层layout必须是Linearlayout,
	 * 如是其它在measure时会爆NullPointerException,因为其他layout无onmeasure()方法
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {// listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);// 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight();// 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		params.width = LayoutParams.FILL_PARENT;
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 转换为网页显示的字符串 返回的CharSequence通过toString方法转换后将失去网页的字体效果。
	 * 
	 * @param text
	 *            待转换的字符串
	 * @param color
	 *            字体颜色
	 * @param style
	 *            字体样式 eg: normal | bold | bolder | lighter | number(100-199)
	 * @return
	 */
	public static CharSequence getHtmlText(String text, String color, String style) {
		String ss;
		if (!TextUtils.isEmpty(color) && !TextUtils.isEmpty(style))
			ss = "<font color=" + color + " weight=" + style + ">" + text + "</font>";
		else if (TextUtils.isEmpty(color))
			ss = "<font weight=" + style + ">" + text + "</font>";
		else if (TextUtils.isEmpty(style))
			ss = "<font color=" + color + ">" + text + "</font>";
		else
			return null;
		return Html.fromHtml(ss);
	}

	/**
	 * 将long值转换成HH:MM:SS的形式
	 * 
	 * @param longTime
	 * @return
	 */
	public static String changeLongValueToTimeStyle(long longTime) {
		String ss = null;
		if (longTime < 10L) {
			ss = "00:0" + longTime;
		}
		else if (longTime < 60L) {
			ss = "00:" + longTime;
		}
		else if (longTime < 3600L) {
			int minutes = (int) (longTime / 60);
			ss = minutes + ":";
			int seconds = (int) (longTime % 60);
			if (seconds < 10)
				ss += "0";
			ss += seconds;
		}
		else {
			int hours = (int) (longTime / 3600);
			ss = hours + ":";
			int minutes = (int) (longTime % 3600) / 60;
			if (minutes < 10)
				ss += "0";
			ss += minutes + ":";
			int seconds = (int) (longTime % 3600) % 60;
			if (seconds < 10)
				ss += "0";
			ss += seconds;
		}
		return ss;
	}

	public static String getImageName(String imgUrl) {
		if (TextUtils.isEmpty(imgUrl))
			return "";
		int index = imgUrl.lastIndexOf("/");
		if (index == (imgUrl.length() - 1))
			return "";
		return imgUrl.substring(index + 1);
	}

	public static String getImgePath(String imgName) {
		if (TextUtils.isEmpty(imgName))
			return "";
		return ComParams.DOWNLOAD_DEFAULT_SDCARD_PATH + ComParams.IMAGECACHE_URL + "/" + imgName;
	}

	/**
	 * MD5加密字符串
	 * 
	 * @param str
	 * @return
	 * @throws DFException
	 */
	public static String EncoderByMd5(String str) throws DFException {
		ULog.v(TAG, str);
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = str.getBytes("utf-8");
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str_char[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str_char[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str_char[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str_char);
		} catch (Exception e) {
			throw new DFException(e.toString(), DFException.UTIL_ENCODERBYMD5_EXCEPTION);
		}
	}

	/**
	 * 
	 * @param list
	 *            需要添加的参数列表
	 * @param encode
	 *            true时，会的参数进行URLEncoder.encode(...)操作
	 * @return
	 */
	public static String encodeUrl(List<NameValuePair> list, boolean encode) {
		if (list == null || 0 == list.size()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int loc = 0, lenght = list.size(); loc < lenght; loc++) {
			if (first)
				first = false;
			else
				sb.append("&");
			if (encode) {
				if (TextUtils.isEmpty(list.get(loc).getValue()) || TextUtils.isEmpty(list.get(loc).getValue().trim()))
					sb.append(URLEncoder.encode(list.get(loc).getName()) + "=null");
				else
					sb.append(URLEncoder.encode(list.get(loc).getName()) + "="
							+ URLEncoder.encode(list.get(loc).getValue().trim().replace("+", "plus")));
			}
			else {
				if (TextUtils.isEmpty(list.get(loc).getValue()) || TextUtils.isEmpty(list.get(loc).getValue().trim()))
					sb.append(list.get(loc).getName() + "=null");
				else
					sb.append(list.get(loc).getName() + "=" + list.get(loc).getValue().trim().replace("+", "plus"));
			}
		}
		return sb.toString();
	}

	/**
	 * 检测path的APP是否安装
	 * 
	 * @param context
	 * @param appPath
	 * @return
	 */
	public static boolean checkAppInstall(Context context, String appPath) {
		boolean flag = false;
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(appPath, PackageManager.GET_META_DATA);
		if (null == info)
			flag = false;
		List<PackageInfo> infos = pm.getInstalledPackages(0);
		String packageName = null == info ? "" : info.packageName;
		for (PackageInfo pi : infos) {
			if (packageName.equals(pi.packageName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static String strDataDuration(String str_star, String str_end) {
		SimpleDateFormat sdfFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start_H = "";
		String start_M = "";
		String end_H = "";
		String end_M = "";
		try {
			start_H = sdfFormat.parse(str_star).getHours() + "";
			start_M = sdfFormat.parse(str_star).getMinutes() + "";
			end_H = sdfFormat.parse(str_end).getHours() + "";
			end_M = sdfFormat.parse(str_end).getMinutes() + "";
			ULog.e("time ", returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M));
			return returnAPM(start_H, start_M) + "-" + returnAPM(end_H, end_M);
		} catch (Throwable e) {
			e.printStackTrace();
			return "** -  **";
		}
	}

	public static String returnAPM(String str_H, String str_M) {
		String returnValue = "";
		if (Integer.parseInt(str_H) < 13) {
			returnValue = formatData(str_H) + ":" + formatData(str_M) + "am";
		}
		else {
			returnValue = formatData((Integer.parseInt(str_H) - 12) + "") + ":" + formatData(str_M) + "pm";
		}
		return returnValue;
	}

	public static String formatData(String data) {
		if (data.length() < 2) {
			return ("0" + data);
		}
		else {
			return data;
		}
	}

	/**
	 * 获取当天的时间，单位为秒(也就是今天已经过了多少秒)
	 * 
	 * @param time
	 */
	public static int getDaySeconds() {
		String time = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
		// System.out.println("当前时间为：" + time);
		int totalsec = formatTime2Seconds(time);
		return totalsec;
	}

	/**
	 * 将HH:mm:ss转换成秒数
	 * 
	 * @param time
	 * @return
	 */
	public static int formatTime2Seconds(String time) {
		// String[] my = time.split(" ");
		String[] mytime = time.split(":");
		int hour = Integer.parseInt(mytime[0]);
		int min = Integer.parseInt(mytime[1]);
		int sec = Integer.parseInt(mytime[2]);
		int totalsec = hour * 3600 + min * 60 + sec;
		return totalsec;
	}

	/**
	 * 将YYYY-MM-DD HH:mm:ss转换成秒数
	 * 
	 * @param time
	 * @return
	 */
	public static int formatDayTime2Seconds(String time) {
		String[] my = time.split((" +"));
		String[] mytime = my[1].split(":");
		int hour = Integer.parseInt(mytime[0]);
		int min = Integer.parseInt(mytime[1]);
		int sec = Integer.parseInt(mytime[2]);
		int totalsec = hour * 3600 + min * 60 + sec;
		return totalsec;
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss中的DD
	 * 
	 * @param time
	 * @return
	 */
	public static int getDayOfTime(String time) {
		String[] my = time.split((" +"));
		String[] ymd = my[0].split("-");
		return Integer.parseInt(ymd[2]);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale必须大于等于0!!");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String getDataStr(String str) {
		String b = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != '-' && str.charAt(i) != ':' && str.charAt(i) != ' ') {
				b = b + String.valueOf(str.charAt(i));
			}

		}

		return b;
	}

	/**
	 * 获取时间，格式yyyyMMdd
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateWithHMD() {
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getTimeStampSSS() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(System.currentTimeMillis());
	}

	/**
	 * 王牌栏目中最新更新时间转换为需要格式
	 * 
	 * @author wwei
	 * @param updatetime
	 * @return
	 * @throws ParseException
	 */
	public static String parseUpdatetime(String updatetime) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
		return mFormat.format(mFormat.parse(updatetime));
	}

	/**
	 * 返回客户端包名
	 * 
	 * @author wwei
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		// TODO Auto-generated method stub
		return context.getPackageName();
	}

	/**
	 * 获取输入字符中的中文字符数
	 * 
	 * @author wwei
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static int getNumOfChineseChar(String content) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		int numOfChineseChar = 0;
		for (int i = 0; i < content.length(); i++) {
			if (isChineseChar((int) content.charAt(i))) {
				numOfChineseChar++;
			}
		}
		return numOfChineseChar;
	}

	/**
	 * 判断字符是否为中文（根据Unicode）
	 * 
	 * @author wwei
	 * @param tempChar
	 * @return
	 */
	private static Boolean isChineseChar(int tempChar) {
		// TODO Auto-generated method stub
		return 19968 <= tempChar && tempChar < 40623 ? true : false;
	}

	/** 把byte数字连接成16进制输出 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv).append(" ");
		}
		return stringBuilder.toString();
	}
	

}