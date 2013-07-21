package com.dongfang.apad;

import android.os.Bundle;

/**
 * 状态广播
 * 
 * @author dongfang
 * 
 */
public interface OnUpdateDataListener {
	/**
	 * 读卡器连接状况
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onSocketConnectCard(boolean isConnect, Bundle data);

	// /**@deprecated */
	// public void onGetCardId(boolean isConnect, Bundle data);
	/**
	 * 读取用户信息
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onGetUserInfo(boolean isConnect, Bundle data);

	/**
	 * 中控连接情况
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onSocketConnectTestZKT(boolean isConnect, Bundle data);

	/**
	 * 开始测试，初始化状态
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onTestZKTRestarted(boolean isConnect, Bundle data);

	/**
	 * 获取中控数据
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onGetTestZKTResult(boolean isConnect, Bundle data);

	/**
	 * 异常
	 * 
	 * @param isConnect
	 * @param data
	 */
	public void onError(boolean isConnect, Bundle data);
}
