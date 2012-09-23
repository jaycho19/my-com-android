package com.dongfang.dicos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.dongfang.dicos.adapter.ImgAdapter;
import com.dongfang.dicos.util.ComParams;

/**
 * µ±¼¾»î¶¯
 * 
 * @author dongfang
 * 
 */
public class CurrentSeason extends Activity {

	public ListView		lvCurrentSeasonImg;
	public ImgAdapter	imgAdater;
	public String[]		aImgUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.currentseason);

		if (null != getIntent() && getIntent().hasExtra(ComParams.CURRENT_SEASON_IMG_ARRARY)) {
			aImgUrl = getIntent().getStringArrayExtra(ComParams.CURRENT_SEASON_IMG_ARRARY);
		} else {
			aImgUrl = new String[] {};
		}

		// imgList.add("http://www.dicos.com.cn/images/app/action/9_1348134247.jpg");

		lvCurrentSeasonImg = (ListView) findViewById(R.id.lv_current_season_list_img);
		imgAdater = new ImgAdapter(this, aImgUrl);
		lvCurrentSeasonImg.setAdapter(imgAdater);

	}
}
