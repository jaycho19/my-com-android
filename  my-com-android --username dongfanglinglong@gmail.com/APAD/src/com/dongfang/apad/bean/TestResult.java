package com.dongfang.apad.bean;

import com.dongfang.apad.util.ULog;
import com.dongfang.apad.util.Util;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 测试结果
 * 
 * @author dongfang
 * 
 */
public class TestResult implements Parcelable {
	public static final String	TAG			= TestResult.class.getSimpleName();

	/** 测试类型 */
	private byte				id			= 0x00;
	/** 测试是否结束 ，00表示未结束， 01 表示结束 */
	private byte				isFinish	= 0x00;

	/** 分类 */
	private String				classify;
	/** 项目描述 */
	private String				itemDes;
	/** 项目名称 */
	private String				item;
	/** 第二个项目名称 */
	private String				item1;
	/** 测试结果 */
	private double				result;
	/** 第二项测试结果 */
	private double				result1;
	/** @deprecated 测试次数 */
	private int					times;
	/** @deprecated 测试日期 */
	private String				date;
	/** 测试成绩 */
	private int					resultGray;
	/** 测试结果描述 */
	private String				resultDes;

	public TestResult() {
		init();

	}

	private void init() {
		id = (byte) 0xFF;
		isFinish = 0x00;
		classify = "";
		itemDes = "";
		item = "";
		item1 = "";
		result = 0.0;
		result1 = 0.0;
		times = 0;
		date = Util.getDate();
		resultGray = 0;
		resultDes = "";
	}

	public void setValue(byte[] input) {
		ULog.d(TAG, "setValue = " + Util.bytesToHexString(input));
		ULog.d(TAG, "id = " + id);
		switch (id) {
		case 0x02:
			classify = "素质";
			item = "握力（千克）";

			isFinish = input[3];

			int kg = (input[5] & 0xFF);
			kg |= (input[4] << 8) & 0xFF00;
			result = Double.valueOf(kg + "." + ((int) input[6] & 0xFF));
			if (0x01 == input[4])
				times++;
			date = Util.getDate();
			double g = Double.valueOf(result);
			if (g < 32.6) {
				resultGray = 1;
			}
			else if (g < 38.4) {
				resultGray = 2;
			}
			else if (g < 44.9) {
				resultGray = 4;
			}
			else if (g < 50.5) {
				resultGray = 5;
			}
			else {
				resultGray = 5;
			}
			resultDes = "";
			break;
		default:
			break;
		}

	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		init();
		this.id = id;
	}

	public byte getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(byte isFinish) {
		this.isFinish = isFinish;
	}

	public String getResultDes() {
		return resultDes;
	}

	public void setResultDes(String resultDes) {
		this.resultDes = resultDes;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public int getResultGray() {
		return resultGray;
	}

	public void setResultGray(int resultGray) {
		this.resultGray = resultGray;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItemDes() {
		return itemDes;
	}

	public void setItemDes(String itemDes) {
		this.itemDes = itemDes;
	}

	public double getResult1() {
		return result1;
	}

	public void setResult1(double result1) {
		this.result1 = result1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id         = ").append(id).append("\n");
		sb.append("isFinish   = ").append(isFinish).append("\n");
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
		dest.writeByte(id);
		dest.writeByte(isFinish);
		dest.writeString(classify);
		dest.writeString(itemDes);
		dest.writeString(item);
		dest.writeString(item1);
		dest.writeDouble(result);
		dest.writeDouble(result1);
		dest.writeInt(times);
		dest.writeString(date);
		dest.writeInt(resultGray);
		dest.writeString(resultDes);

	}

	public static final Parcelable.Creator<TestResult>	CREATOR	= new Parcelable.Creator<TestResult>() {

																	@Override
																	public TestResult createFromParcel(Parcel source) {
																		TestResult data = new TestResult();
																		data.setId(source.readByte());
																		data.setIsFinish(source.readByte());
																		data.setClassify(source.readString());
																		data.setItemDes(source.readString());
																		data.setItem(source.readString());
																		data.setItem1(source.readString());
																		data.setResult(source.readDouble());
																		data.setResult1(source.readDouble());
																		data.setTimes(source.readInt());
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
