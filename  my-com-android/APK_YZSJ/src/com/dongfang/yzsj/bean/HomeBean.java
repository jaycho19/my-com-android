package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.df.util.ULog;

/**
 * 首页数据结构
 * 
 * @author dongfang
 */
public class HomeBean implements Parcelable {
	public static final String TAG = "HomeBean";

	private String marquee; // 公告
	private List<SliderItem> slider; // kv图
	private List<LivesItem> lives; // 直播
	private List<ChannelItem> channelContents; // 频道数据

	public HomeBean() {
		marquee = "";
		slider = new ArrayList<SliderItem>();
		lives = new ArrayList<LivesItem>();
		channelContents = new ArrayList<ChannelItem>();
	}

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public List<SliderItem> getSlider() {
		return slider;
	}

	public void setSlider(List<SliderItem> slider) {
		this.slider = slider;
	}

	public List<LivesItem> getLives() {
		return lives;
	}

	public void setLives(List<LivesItem> lives) {
		this.lives = lives;
	}

	public List<ChannelItem> getChannelContents() {
		return channelContents;
	}

	public void setChannelContents(List<ChannelItem> channelContents) {
		this.channelContents = channelContents;
	}

	/** 输出内容 */
	public void toLog() {
		ULog.d(TAG, "marquee = " + marquee);

		for (int i = 0, length = slider.size(); i < length; i++) {
			ULog.d(TAG, "slider SliderItem = " + i + "\n" + slider.get(i).toString());
		}

		for (int i = 0, length = lives.size(); i < length; i++) {
			ULog.d(TAG, "lives LivesItem = " + i + "\n" + lives.get(i).toString());
		}
		for (int i = 0, length = channelContents.size(); i < length; i++) {
			//ULog.d(TAG, "channelContents ChannelItem = " + i + "\n" + channelContents.get(i).toString());
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(marquee);
		dest.writeList(slider);
		dest.writeList(lives);
		dest.writeList(channelContents);
	}

	public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {

		@Override
		public HomeBean createFromParcel(Parcel in) {
			HomeBean data = new HomeBean();
			data.setMarquee(in.readString());
			in.readList(data.getSlider(), SliderItem.class.getClassLoader());
			in.readList(data.getLives(), LivesItem.class.getClassLoader());
			in.readList(data.getChannelContents(), ChannelItem.class.getClassLoader());
			return data;
		}

		@Override
		public HomeBean[] newArray(int size) {
			return new HomeBean[size];
		}
	};

}
