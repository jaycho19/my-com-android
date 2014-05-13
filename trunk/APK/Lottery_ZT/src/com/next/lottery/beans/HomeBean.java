package com.next.lottery.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongfang.utils.ULog;

/**
 * 首页数据结构
 * 
 * @author dongfang
 */
public class HomeBean implements Parcelable {
	public static final String TAG = "HomeBean";

	private String marquee; // 公告

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	/** 输出内容 */
	public void toLog() {
		ULog.d("marquee = " + marquee);
	}

	@Override
	public String toString() {
		return marquee;

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(marquee);

	}

	public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {

		@Override
		public HomeBean createFromParcel(Parcel in) {
			HomeBean data = new HomeBean();
			data.setMarquee(in.readString());
			return data;
		}

		@Override
		public HomeBean[] newArray(int size) {
			return new HomeBean[size];
		}
	};

}
