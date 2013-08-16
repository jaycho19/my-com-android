package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/** 平道内容 @author dongfang */
public class HomeChannelItem implements Parcelable {
	public List<HomeMovie> movies;
	public HomeChannel channel;

	public HomeChannelItem() {
		movies = new ArrayList<HomeMovie>();
		channel = new HomeChannel();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(movies);
		dest.writeParcelable(channel,0);
	}

	public static final Parcelable.Creator<HomeChannelItem> CREATOR = new Parcelable.Creator<HomeChannelItem>() {
		@Override
		public HomeChannelItem createFromParcel(Parcel in) {
			HomeChannelItem data = new HomeChannelItem();
			in.readList(data.movies, HomeMovie.class.getClassLoader());
			data.channel = in.readParcelable(HomeChannel.class.getClassLoader());
			return data;
		}

		@Override
		public HomeChannelItem[] newArray(int size) {
			return new HomeChannelItem[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" --  ChannelItem channel = " + channel).append("\n");
		for (HomeMovie m : movies)
			sb.append(" -- ChannelItem movies = \n" + m.toString()).append("\n");
		return sb.toString();
	}

}