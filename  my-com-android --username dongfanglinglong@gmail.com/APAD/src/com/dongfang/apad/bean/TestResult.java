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

	/** 分类 */
	private String	classify;
	/** 项目描述 */
	private String	itemDes;
	/** 项目名称 */
	private String	item;
	/** 第二个项目名称 */
	private String	item1;
	/** 测试结果 */
	private String	result;
	/** 第二项测试结果 */
	private String	result1;
	/** 测试次数 */
	private String	times;
	/** 测试日期 */
	private String	date;
	/** 测试成绩 */
	private int		resultGray;
	/** 测试结果描述 */
	private String	resultDes;

	/**
	 * @return the resultDes
	 */
	public String getResultDes() {
		return resultDes;
	}

	/**
	 * @param resultDes
	 *            the resultDes to set
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

	
	
	/**
	 * @return the item1
	 */
	public String getItem1() {
		return item1;
	}

	/**
	 * @param item1 the item1 to set
	 */
	public void setItem1(String item1) {
		this.item1 = item1;
	}

	/**
	 * @return the itemDes
	 */
	public String getItemDes() {
		return itemDes;
	}

	/**
	 * @param itemDes the itemDes to set
	 */
	public void setItemDes(String itemDes) {
		this.itemDes = itemDes;
	}

	/**
	 * @return the result1
	 */
	public String getResult1() {
		return result1;
	}

	/**
	 * @param result1 the result1 to set
	 */
	public void setResult1(String result1) {
		this.result1 = result1;
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
		sb.append("itemDes    = ").append(itemDes).append("\n");
		sb.append("item       = ").append(item).append("\n");
		sb.append("item1      = ").append(item1).append("\n");
		sb.append("result     = ").append(result).append("\n");
		sb.append("result1    = ").append(result1).append("\n");
		sb.append("times      = ").append(times).append("\n");
		sb.append("date       = ").append(date).append("\n");
		sb.append("resultGray = ").append(resultGray).append("\n");
		sb.append("resultDes  = ").append(resultDes).append("\n");
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
		dest.writeString(itemDes);
		dest.writeString(item);
		dest.writeString(item1);
		dest.writeString(result);
		dest.writeString(result1);
		dest.writeString(times);
		dest.writeString(date);
		dest.writeInt(resultGray);
		dest.writeString(resultDes);

	}

	public static final Parcelable.Creator<TestResult>	CREATOR	= new Parcelable.Creator<TestResult>() {

		@Override
		public TestResult createFromParcel(Parcel source) {
			TestResult data = new TestResult();
			data.setClassify(source.readString());
			data.setItemDes(source.readString());
			data.setItem(source.readString());
			data.setItem1(source.readString());
			data.setResult(source.readString());
			data.setResult1(source.readString());
			data.setTimes(source.readString());
			data.setDate(source.readString());
			data.setResultGray(source.readInt());
			data.setResultDes(source.readString());
			return data;
		}

		@Override
		public TestResult[] newArray(int size) {

			return new TestResult[size];
		}

	};

}
