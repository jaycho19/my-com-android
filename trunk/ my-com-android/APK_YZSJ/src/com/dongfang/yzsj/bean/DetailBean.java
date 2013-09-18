package com.dongfang.yzsj.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 详情页信息
 * 
 * @author dongfang
 * 
 */
public class DetailBean implements Parcelable {

	public static final String TAG = "DetailBean";

	private boolean success;
	private boolean hasFavoriteIt; // 是否搜藏
	private List<String> error;
	private Channel channel;
	private DetailContent content;
	private List<Movie> relateContents;

	private DetailBean() {
		error = new ArrayList<String>();
		relateContents = new ArrayList<Movie>();
	}

	public boolean isHasFavoriteIt() {
		return hasFavoriteIt;
	}

	public void setHasFavoriteIt(boolean hasFavoriteIt) {
		this.hasFavoriteIt = hasFavoriteIt;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError0() {
		return error.get(0);
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public DetailContent getContent() {
		return content;
	}

	public void setContent(DetailContent content) {
		this.content = content;
	}

	public List<Movie> getRelateContents() {
		return relateContents;
	}

	public void setRelateContents(List<Movie> relateContents) {
		this.relateContents = relateContents;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("\n");
		sb.append("success       = ").append(success).append("\n");
		sb.append("hasFavoriteIt = ").append(hasFavoriteIt).append("\n");
		if (null != error && error.size() > 0)
			sb.append("error        = ").append(error.get(0)).append("\n");
		sb.append("channel      = ").append(channel.toString()).append("\n");
		sb.append("content      = ").append(content.toString()).append("\n");
		for (int i = 0, length = relateContents.size(); i < length; i++) {
			sb.append("relateContents ").append(i).append(" = ").append(relateContents.get(i).toString()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(success ? 1 : 0);
		dest.writeInt(hasFavoriteIt ? 1 : 0);
		dest.writeStringList(error);
		dest.writeParcelable(channel, 0);
		dest.writeParcelable(content, 0);
		dest.writeTypedList(relateContents);
	}

	public static final Parcelable.Creator<DetailBean> CREATOR = new Parcelable.Creator<DetailBean>() {

		@Override
		public DetailBean createFromParcel(Parcel in) {
			DetailBean data = new DetailBean();
			data.success = in.readInt() == 1 ? true : false;
			data.hasFavoriteIt = in.readInt() == 1 ? true : false;
			data.error = in.createStringArrayList();
			data.channel = in.readParcelable(Channel.class.getClassLoader());
			data.content = in.readParcelable(DetailContent.class.getClassLoader());
			in.readTypedList(data.relateContents, Movie.CREATOR);
			return data;
		}

		@Override
		public DetailBean[] newArray(int size) {
			return new DetailBean[size];
		}
	};

}
