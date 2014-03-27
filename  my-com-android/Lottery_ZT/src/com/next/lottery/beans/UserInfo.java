package com.next.lottery.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {

	private String userId;
	private String merId;
	private String userName;
	private String userToken;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("userId = ").append(userId).append("\n");
		sb.append("merId = ").append(merId).append("\n");
		sb.append("userName = ").append(userName).append("\n");
		sb.append("userToken = ").append(userToken).append("\n");
		return sb.toString();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(merId);
		dest.writeString(userName);
		dest.writeString(userToken);
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

		public UserInfo createFromParcel(Parcel source) {
			UserInfo data = new UserInfo();
			data.userId = source.readString();
			data.merId = source.readString();
			data.userName = source.readString();
			data.userToken = source.readString();
			return data;
		}

		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}
	};

}
