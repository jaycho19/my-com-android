package com.dongfang.yzsj.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/** 频道信息 @author dongfang */
public class HomeChannel implements Parcelable {

	private String id; // 列表页中，子频道属性
	private String channelId;
	// public String code;
	private String name;
	private String poster;
	private String parentId;

	public String getChannelId() {
		return TextUtils.isEmpty(channelId) ? id : channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(channelId);
		dest.writeString(name);
		dest.writeString(poster);
		dest.writeString(parentId);
	}

	public static final Parcelable.Creator<HomeChannel> CREATOR = new Parcelable.Creator<HomeChannel>() {

		@Override
		public HomeChannel createFromParcel(Parcel in) {
			HomeChannel data = new HomeChannel();
			data.id = in.readString();
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
		sb.append("\n -- -- id        = " + id).append("\n");
		sb.append(" -- -- channelId = " + channelId).append("\n");
		sb.append(" -- -- name      = " + name).append("\n");
		sb.append(" -- -- poster    = " + poster).append("\n");
		sb.append(" -- -- parentId  = " + parentId).append("\n");
		return sb.toString();
	}

}