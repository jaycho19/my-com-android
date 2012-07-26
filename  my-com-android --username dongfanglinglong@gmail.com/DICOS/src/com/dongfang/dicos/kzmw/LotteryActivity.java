package com.dongfang.dicos.kzmw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dongfang.dicos.R;
import com.dongfang.dicos.lottery.DetailActivity;
import com.dongfang.dicos.lottery.InfoActivity;
import com.dongfang.dicos.lottery.JoinInActivity;
import com.dongfang.dicos.lottery.PrizeInfoActivity;
import com.dongfang.dicos.lottery.PrizeProvideActivity;
import com.dongfang.dicos.util.ULog;

/**
 * 抽奖
 * 
 * @author dongfang
 * */
public class LotteryActivity extends Activity implements OnClickListener {
	public static final String	tag	= "LotteryActivity";

	/** 返回 */
	private Button				bBack;

	/** 参加抽奖 */
	private Button				bJoinIn;
	/** 活动细则 */
	private Button				bDetail;
	/** 抽奖信息 */
	private Button				bInfo;
	/** 中奖信息 */
	private Button				bPrizeInfo;
	/** 奖品发放 */
	private Button				bPrizeProvide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lottery);

		bBack = (Button) findViewById(R.id.button_lottery_back);
		bBack.setOnClickListener(this);

		bJoinIn = (Button) findViewById(R.id.button_lottery_joinin);
		bJoinIn.setOnClickListener(this);

		bDetail = (Button) findViewById(R.id.button_lottery_detail);
		bDetail.setOnClickListener(this);

		bInfo = (Button) findViewById(R.id.button_lottery_info);
		bInfo.setOnClickListener(this);

		bPrizeInfo = (Button) findViewById(R.id.button_lottery_prizeinfo);
		bPrizeInfo.setOnClickListener(this);

		bPrizeProvide = (Button) findViewById(R.id.button_lottery_prizeprovide);
		bPrizeProvide.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		ULog.d(tag, "onClick v.getId() = " + v.getId());
		Intent intent;
		switch (v.getId()) {
		case R.id.button_lottery_joinin:
			intent = new Intent(LotteryActivity.this, JoinInActivity.class);
			startActivity(intent);
			break;
		case R.id.button_lottery_detail:
			intent = new Intent(LotteryActivity.this, DetailActivity.class);
			startActivity(intent);
			break;
		case R.id.button_lottery_info:
			intent = new Intent(LotteryActivity.this, InfoActivity.class);
			startActivity(intent);
			break;
		case R.id.button_lottery_prizeinfo:
			intent = new Intent(LotteryActivity.this, PrizeInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.button_lottery_prizeprovide:
			intent = new Intent(LotteryActivity.this, PrizeProvideActivity.class);
			startActivity(intent);
			break;
		case R.id.button_lottery_back:
			finish();
			break;
		}
	}
}
