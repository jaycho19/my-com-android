package com.dongfang.apad.bean;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 测试结果
 * 
 * @author dongfang
 * 
 */
public class TestResult implements Parcelable {

	private String	classify;
	private String	item;
	private String	times;
	private String	date;
	private String	result;
	private String	resultDes;
	private int		resultGray;
	

	/**
	 * @return the resultDes
	 */
	public String getResultDes() {
		return resultDes;
	}

	/**
	 * @param resultDes the resultDes to set
	 */
	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	/**
	 * @return the classify
	 */
	public String getClassify() {
		return classify;
	}

	/**
	 * @param classify
	 *            the classify to set
	 */
	public void setClassify(String classify) {
		this.classify = classify;
	}

	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * @return the times
	 */
	public String getTimes() {
		return times;
	}

	/**
	 * @param times
	 *            the times to set
	 */
	public void setTimes(String times) {
		this.times = times;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the resultGray
	 */
	public int getResultGray() {
		return resultGray;
	}

	/**
	 * @param resultGray
	 *            the resultGray to set
	 */
	public void setResultGray(int resultGray) {
		this.resultGray = resultGray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("classify   = ").append(classify).append("\n");
		sb.append("item       = ").append(item).append("\n");
		sb.append("times      = ").append(times).append("\n");
		sb.append("date       = ").append(date).append("\n");
		sb.append("result     = ").append(result).append("\n");
		sb.append("resultGray = ").append(resultGray).append("\n");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(classify);
		dest.writeString(item);
		dest.writeString(times);
		dest.writeString(date);
		dest.writeString(result);
		dest.writeString(resultDes);
		dest.writeInt(resultGray);

	}

	public static final Parcelable.Creator<TestResult>	CREATOR	= new Parcelable.Creator<TestResult>() {

			@Override
			public TestResult createFromParcel(Parcel source) {
				TestResult data = new TestResult();
				data.setClassify(source.readString());
				data.setItem(source.readString());
				data.setTimes(source.readString());
				data.setDate(source.readString());
				data.setResult(source.readString());
				data.setResultDes(source.readString());
				data.setResultGray(source.readInt());
				return data;
			}

			@Override
			public TestResult[] newArray(int size) {

				return new TestResult[size];
			}

		};

}
