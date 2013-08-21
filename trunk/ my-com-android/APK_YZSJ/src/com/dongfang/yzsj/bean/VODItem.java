package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/** 点播数据 @author dongfang */
public class VODItem implements Parcelable {
	public static final String TAG = "VODItem";

	private String channelId;
	private String code;
	private String name;
	private String poster;
	private boolean vip;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" -- channelId = ").append(channelId).append("\n");
		sb.append(" -- code =      ").append(code).append("\n");
		sb.append(" -- name =      ").append(name).append("\n");
		sb.append(" -- poster =    ").append(poster).append("\n");
		sb.append(" -- vip =       ").append(vip).append("\n");
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(channelId);
		dest.writeString(code);
		dest.writeString(name);
		dest.writeString(poster);
		dest.writeInt(vip ? 1 : 0);
	}

	public static final Parcelable.Creator<VODItem> CREATOR = new Parcelable.Creator<VODItem>() {

		@Override
		public VODItem createFromParcel(Parcel in) {
			VODItem data = new VODItem();
			data.setChannelId(in.readString());
			data.setCode(in.readString());
			data.setName(in.readString());
			data.setPoster(in.readString());
			data.setVip(in.readInt() == 1 ? true : false);
			return data;
		}

		@Override
		public VODItem[] newArray(int size) {
			return new VODItem[size];
		}
	};
}
