package com.dongfang.yzsj.bean;

import com.df.util.ULog;

/**
 * 首页数据结构
 * 
 * @author dongfang
 */
public class HomeBean {
	public static final String TAG = "HomeBean";

	private String marquee; // 公告
	private SliderItem[] slider; // kv图
	private LivesItem[] lives; // 直播
	private ChannelItem[] channelContents; // 频道数据

	public String getMarquee() {
		return marquee;
	}

	public void setMarquee(String marquee) {
		this.marquee = marquee;
	}

	public SliderItem[] getSlider() {
		return slider;
	}

	public void setSlider(SliderItem[] slider) {
		this.slider = slider;
	}

	public LivesItem[] getLives() {
		return lives;
	}

	public void setLives(LivesItem[] lives) {
		this.lives = lives;
	}

	public ChannelItem[] getChannelContents() {
		return channelContents;
	}

	public void setChannelContents(ChannelItem[] channelContents) {
		this.channelContents = channelContents;
	}

	/** 输出内容 */
	public void toLog() {
		ULog.d(TAG, "marquee = " + marquee);

		for (int i = 0; i < slider.length; i++) {
			ULog.d(TAG, "slider SliderItem = " + i + "\n" + slider[i].toString());
		}

		for (int i = 0; i < lives.length; i++) {
			ULog.d(TAG, "lives LivesItem = " + i + "\n" + lives[i].toString());
		}
		for (int i = 0; i < channelContents.length; i++) {
			ULog.d(TAG, "channelContents ChannelItem = " + i + "\n" + channelContents[i].toString());
		}
	}

	/** kv图 */
	private class SliderItem {
		public String MEDIA_ACTORS;
		public String MEDIA_LENGTH;
		public String MEDIA_NAME;
		public String MEDIA_PIC_RECOM2;
		// public String PAD_MEDIA_POSTER_BIG;
		// public String PC_MEDIA_POSTER_BIG;
		public String id;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" -- MEDIA_ACTORS = " + MEDIA_ACTORS).append("\n");
			sb.append(" -- MEDIA_LENGTH = " + MEDIA_LENGTH).append("\n");
			sb.append(" -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
			sb.append(" -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
			// sb.append(" -- PAD_MEDIA_POSTER_BIG = " + PAD_MEDIA_POSTER_BIG).append("\n");
			// sb.append(" -- PC_MEDIA_POSTER_BIG = " + PC_MEDIA_POSTER_BIG).append("\n");
			sb.append(" -- id = " + id).append("\n");
			return sb.toString();
		}

	}

	/** 直播 */
	private class LivesItem {
		public String MEDIA_NAME;
		// public String PC_MEDIA_POSTER_HORIZONTAL_BIG;
		public String PHONE_MEDIA_POSTER_SMALL;
		public String id;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
			sb.append(" -- PHONE_MEDIA_POSTER_SMALL = " + PHONE_MEDIA_POSTER_SMALL).append("\n");
			sb.append(" -- id = " + id).append("\n");
			return sb.toString();
		}
	}

	/** 平道内容 */
	private class ChannelItem {
		public Movie[] movies;
		public Channel channel;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" --  ChannelItem channel = " + channel).append("\n");
			for (Movie m : movies)
				sb.append(" -- ChannelItem movies = \n" + m.toString()).append("\n");
			return sb.toString();
		}

	}

	private class Movie {
		public String MEDIA_ACTORS; // 演员
		public String MEDIA_LENGTH; // 视频长度
		public String MEDIA_NAME; // 视频名称
		public String MEDIA_PIC_RECOM2;
		// public String PAD_MEDIA_POSTER_BIG;
		public String PC_MEDIA_POSTER_BIG;
		// public String PC_MEDIA_POSTER_HORIZONTAL_BIG;
		public String id;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" -- -- MEDIA_ACTORS = " + MEDIA_ACTORS).append("\n");
			sb.append(" -- -- MEDIA_LENGTH = " + MEDIA_LENGTH).append("\n");
			sb.append(" -- -- MEDIA_NAME = " + MEDIA_NAME).append("\n");
			sb.append(" -- -- MEDIA_PIC_RECOM2 = " + MEDIA_PIC_RECOM2).append("\n");
			sb.append(" -- -- PC_MEDIA_POSTER_BIG = " + PC_MEDIA_POSTER_BIG).append("\n");
			sb.append(" -- -- id = " + id).append("\n");
			return sb.toString();
		}

	}

	private class Channel {
		public String channelId;
		// public String code;
		public String name;
		public String poster;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" -- -- channelId = " + channelId).append("\n");
			sb.append(" -- -- name = " + name).append("\n");
			sb.append(" -- -- poster = " + poster).append("\n");
			return sb.toString();
		}

	}

}
