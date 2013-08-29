package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 频道列表结构
 * 
 * @author dongfang
 */
public class SeachBean implements Parcelable {
	public static final String TAG = "TypeBean";

	private HomeChannel channel;
	private HomeChannel parentChannel;
	private SeachListData listData;

	// private List<Movie> listData;
	private List<HomeChannel> subChannels;

	private SeachBean() {
		channel = null;
		parentChannel = null;
		listData = new SeachListData();
		subChannels = new ArrayList<HomeChannel>();
	}

	public HomeChannel getChannel() {
		return channel;
	}

	public void setChannel(HomeChannel channel) {
		this.channel = channel;
	}

	public HomeChannel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(HomeChannel parentChannel) {
		this.parentChannel = parentChannel;
	}

	public SeachListData getListData() {
		return listData;
	}

	public void setListData(SeachListData listData) {
		this.listData = listData;
	}

	public List<HomeChannel> getSubChannels() {
		return subChannels;
	}

	public void setSubChannels(List<HomeChannel> subChannels) {
		this.subChannels = subChannels;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("channel       = ").append(channel.toString()).append("\n");
		sb.append("parentChannel = ").append(parentChannel.toString()).append("\n");
		sb.append("listData      = ").append(listData.toString()).append("\n");
		for (int i = 0, length = subChannels.size(); i < length; i++) {
			sb.append("subChannels ").append(i).append(" = ").append(subChannels.get(i).toString()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(channel, 0);
		dest.writeParcelable(parentChannel, 1);
		dest.writeParcelable(listData, 0);
		dest.writeList(subChannels);
	}

	public static final Parcelable.Creator<SeachBean> CREATOR = new Parcelable.Creator<SeachBean>() {

		@Override
		public SeachBean createFromParcel(Parcel in) {
			SeachBean data = new SeachBean();
			data.setChannel((HomeChannel) in.readParcelable(HomeChannel.class.getClassLoader()));
			data.setParentChannel((HomeChannel) in.readParcelable(HomeChannel.class.getClassLoader()));
			data.setListData((SeachListData) in.readParcelable(SeachListData.class.getClassLoader()));
			in.readList(data.getSubChannels(), HomeChannel.class.getClassLoader());
			return data;
		}

		@Override
		public SeachBean[] newArray(int size) {
			return new SeachBean[size];
		}
	};

}
