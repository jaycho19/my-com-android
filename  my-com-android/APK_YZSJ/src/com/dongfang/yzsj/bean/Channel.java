package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** 频道信息 @author dongfang */
public class Channel implements Parcelable {

	public String channelId;
	// public String code;
	public String name;
	public String poster;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(channelId);
		dest.writeString(name);
		dest.writeString(poster);
	}

	public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {

		@Override
		public Channel createFromParcel(Parcel in) {
			Channel data = new Channel();
			data.channelId = in.readString();
			data.name = in.readString();
			data.poster = in.readString();
			return data;
		}

		@Override
		public Channel[] newArray(int size) {
			return new Channel[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- -- channelId = " + channelId).append("\n");
		sb.append(" -- -- name = " + name).append("\n");
		sb.append(" -- -- poster = " + poster).append("\n");
		return sb.toString();
	}

}