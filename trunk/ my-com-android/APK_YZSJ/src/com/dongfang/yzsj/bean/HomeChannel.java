package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** 频道信息 @author dongfang */
public class HomeChannel implements Parcelable {

	public String channelId;
	// public String code;
	public String name;
	public String poster;
	public String parentId;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(channelId);
		dest.writeString(name);
		dest.writeString(poster);
		dest.writeString(parentId);
	}

	public static final Parcelable.Creator<HomeChannel> CREATOR = new Parcelable.Creator<HomeChannel>() {

		@Override
		public HomeChannel createFromParcel(Parcel in) {
			HomeChannel data = new HomeChannel();
			data.channelId = in.readString();
			data.name = in.readString();
			data.poster = in.readString();
			data.parentId = in.readString();
			return data;
		}

		@Override
		public HomeChannel[] newArray(int size) {
			return new HomeChannel[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- -- channelId = " + channelId).append("\n");
		sb.append(" -- -- name      = " + name).append("\n");
		sb.append(" -- -- poster    = " + poster).append("\n");
		sb.append(" -- -- parentId  = " + parentId).append("\n");
		return sb.toString();
	}

}