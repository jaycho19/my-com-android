package com.dongfang.apad;

import android.os.Bundle;

public interface OnUpdateDataListener {
	public void onSocketConnectCard(String des, Bundle data);

	public void onGetCardId(String des, Bundle data);

	public void onGetUserInfo(String des, Bundle data);

	public void onSocketConnectTestZKT(String des, Bundle data);

	public void onTestZKTRestarted(String des, Bundle data);

	public void onGetTestZKTResult(String des, Bundle data);
}
