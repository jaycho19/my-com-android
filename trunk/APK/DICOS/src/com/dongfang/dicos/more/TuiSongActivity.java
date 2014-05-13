package com.dongfang.dicos.more;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dongfang.dicos.R;
import com.dongfang.dicos.util.ULog;
import com.dongfang.dicos.util.Util;
import com.dongfang.dicos.view.SlipButton;
import com.dongfang.dicos.view.SlipButton.OnChangedListener;

/**
 * 推送设置
 * 
 * @author dongfang
 */
public class TuiSongActivity extends Activity implements OnClickListener {

	public static final String	tag	= "TuiSongActivity";

	/** 返回按钮 */
	private Button				bBack;

	/** 卡滋美味 */
	SlipButton					slipButton_KZMW;
	/** 推送设置 */
	SlipButton					slipButton_TuiSong;

	private Context				context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.setting_tuisong);

		bBack = (Button) findViewById(R.id.button_setting_tuisong_back);
		bBack.setOnClickListener(this);

		slipButton_KZMW = (SlipButton) findViewById(R.id.slipButton_setting_tuisong_kzmw);
		slipButton_KZMW.setCheck(Util.getPushKZMW(context));
		slipButton_TuiSong = (SlipButton) findViewById(R.id.slipButton_setting_tuisong_tuisong);
		slipButton_TuiSong.setCheck(Util.getPushDJHD(context));

		slipButton_KZMW.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				Util.savePushKZMW(context, CheckState);
			}
		});

		slipButton_TuiSong.SetOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				Util.savePushDJHD(context, CheckState);
			}
		});
	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		switch (v.getId()) {
		case R.id.button_setting_tuisong_back:
			finish();
			break;
		}
	}
}
