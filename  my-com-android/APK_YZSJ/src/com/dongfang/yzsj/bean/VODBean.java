package com.dongfang.yzsj.bean;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 点播数据 @author dongfang
 * 
 * @deprecated
 * 
 * */
public class VODBean implements Parcelable {
	public static final String TAG = "LiveBean";

	private List<VODItem> vod;

	public List<VODItem> getVod() {
		return vod;
	}

	public void setVod(List<VODItem> vod) {
		this.vod = vod;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, length = vod.size(); i < length; i++)
			sb.append("vod ").append(i).append(" --> ").append(vod.get(i).toString());
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {}

	public static final Parcelable.Creator<VODBean> CREATOR = new Parcelable.Creator<VODBean>() {

		@Override
		public VODBean createFromParcel(Parcel in) {
			VODBean data = new VODBean();
			// data.setMarquee(in.readString());
			// in.readList(data.getSlider(), HomeSliderItem.class.getClassLoader());
			// in.readList(data.getLives(), HomeLivesItem.class.getClassLoader());
			// in.readList(data.getChannelContents(), HomeChannelItem.class.getClassLoader());
			return data;
		}

		@Override
		public VODBean[] newArray(int size) {
			return new VODBean[size];
		}
	};
}
