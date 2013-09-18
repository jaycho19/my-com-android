package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.dongfang.utils.ULog;

/**
 * 首页数据结构
 * 
 * @author dongfang
 */
public class HomeBean implements Parcelable {
	public static final String TAG = "HomeBean";

	private String marquee; // 公告
	private List<HomeSliderItem> slider; // kv图
	private List<HomeLivesItem> lives; // 直播
	private List<HomeChannelItem> channelContents; // 频道数据

	private HomeBean() {
		marquee = "";
		slider = new ArrayList<HomeSliderItem>();
		lives = new ArrayList<HomeLivesItem>();
		channelContents = new ArrayList<HomeChannelItem>();
	}

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public List<HomeSliderItem> getSlider() {
		return slider;
	}

	public void setSlider(List<HomeSliderItem> slider) {
		this.slider = slider;
	}

	public List<HomeLivesItem> getLives() {
		return lives;
	}

	public void setLives(List<HomeLivesItem> lives) {
		this.lives = lives;
	}

	public List<HomeChannelItem> getChannelContents() {
		return channelContents;
	}

	public void setChannelContents(List<HomeChannelItem> channelContents) {
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
			// ULog.d(TAG, "channelContents ChannelItem = " + i + "\n" + channelContents.get(i).toString());
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
			in.readList(data.getSlider(), HomeSliderItem.class.getClassLoader());
			in.readList(data.getLives(), HomeLivesItem.class.getClassLoader());
			in.readList(data.getChannelContents(), HomeChannelItem.class.getClassLoader());
			return data;
		}

		@Override
		public HomeBean[] newArray(int size) {
			return new HomeBean[size];
		}
	};

}
