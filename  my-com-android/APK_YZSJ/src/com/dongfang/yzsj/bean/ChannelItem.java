package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/** 平道内容 @author dongfang */
public class ChannelItem implements Parcelable {
	public List<Movie> movies;
	public Channel channel;

	public ChannelItem() {
		movies = new ArrayList<Movie>();
		channel = new Channel();
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

	public static final Parcelable.Creator<ChannelItem> CREATOR = new Parcelable.Creator<ChannelItem>() {
		@Override
		public ChannelItem createFromParcel(Parcel in) {
			ChannelItem data = new ChannelItem();
			in.readList(data.movies, Movie.class.getClassLoader());
			data.channel = in.readParcelable(Channel.class.getClassLoader());
			return data;
		}

		@Override
		public ChannelItem[] newArray(int size) {
			return new ChannelItem[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" --  ChannelItem channel = " + channel).append("\n");
		for (Movie m : movies)
			sb.append(" -- ChannelItem movies = \n" + m.toString()).append("\n");
		return sb.toString();
	}

}