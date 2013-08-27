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
public class TypeBean implements Parcelable {
	public static final String TAG = "TypeBean";

	private HomeChannel channel;
	private HomeChannel parentChannel;
	private List<HomeMovie> listData;
	private List<HomeChannel> subChannels;

	private TypeBean() {
		channel = null;
		parentChannel = null;
		listData = new ArrayList<HomeMovie>();
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

	public List<HomeMovie> getListData() {
		return listData;
	}

	public void setListData(List<HomeMovie> listData) {
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
		for (int i = 0, length = subChannels.size(); i < length; i++) {
			sb.append("subChannels ").append(i).append(" = ").append(subChannels.get(i).toString()).append("\n");
		}
		for (int i = 0, length = listData.size(); i < length; i++) {
			sb.append("listData ").append(i).append(" = ").append(listData.get(i).toString()).append("\n");
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
		dest.writeList(subChannels);
		dest.writeList(listData);
	}

	public static final Parcelable.Creator<TypeBean> CREATOR = new Parcelable.Creator<TypeBean>() {

		@Override
		public TypeBean createFromParcel(Parcel in) {
			TypeBean data = new TypeBean();
			data.setChannel((HomeChannel) in.readParcelable(HomeChannel.class.getClassLoader()));
			data.setParentChannel((HomeChannel) in.readParcelable(HomeChannel.class.getClassLoader()));
			in.readList(data.getSubChannels(), HomeChannel.class.getClassLoader());
			in.readList(data.getListData(), HomeMovie.class.getClassLoader());
			return data;
		}

		@Override
		public TypeBean[] newArray(int size) {
			return new TypeBean[size];
		}
	};

}
