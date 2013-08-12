package com.dongfang.yzsj.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.yzsj.R;

/**
 * 各种类型的更多页面
 * 
 * @author dongfang
 * 
 */
public class TypeFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = TypeFragment.class.getSimpleName();

    private TextView btnBack; // 返回
    private TextView btnChangeType; // 切换频道
    private TextView tvTitle; // 类型

    private PopupWindow popuWindow; // 频道切换框

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_type, container, false);
	initPopuWindow(inflater);
	initView(view);
	return view;
    }

    private void initPopuWindow(LayoutInflater inflater) {
	View view = inflater.inflate(R.layout.type_popw_change, null);
	popuWindow = new PopupWindow(view, -1, -2);
	// 展开特效
	popuWindow.setAnimationStyle(R.style.Animations_GrowFromTop);
	// when a touch even happens outside of the window
	// make the window go away
	popuWindow.setBackgroundDrawable(new ColorDrawable(0));
	popuWindow.setFocusable(true);
	popuWindow.setOutsideTouchable(true);
	popuWindow.setTouchInterceptor(new OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
		    popuWindow.dismiss();
		    return true;
		}
		return false;
	    }
	});

	int[] type = { R.id.type_tv_change_type_baishitong,
		R.id.type_tv_change_type_dianshiju,
		R.id.type_tv_change_type_dianying,
		R.id.type_tv_change_type_fengshang,
		R.id.type_tv_change_type_huashu,
		R.id.type_tv_change_type_jiaoyu,
		R.id.type_tv_change_type_jishi,
		R.id.type_tv_change_type_minglanmu,
		R.id.type_tv_change_type_tiyu, R.id.type_tv_change_type_xinwen,
		R.id.type_tv_change_type_yinyue,
		R.id.type_tv_change_type_youpeng,
		R.id.type_tv_change_type_yule, R.id.type_tv_change_type_zhuanti };
	for (int i = 0; i < type.length; i++)
	    view.findViewById(type[i]).setOnClickListener(this);

    }

    private void initView(View view) {
	tvTitle = (TextView) view.findViewById(R.id.type_tv_title);
	btnBack = (TextView) view.findViewById(R.id.type_tv_back);
	btnChangeType = (TextView) view.findViewById(R.id.type_tv_change_type);
	btnChangeType.setOnClickListener(this);
	btnBack.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	if (null != popuWindow && popuWindow.isShowing()) {
	    popuWindow.dismiss();
	}
    }

    @Override
    public void onClick(View v) {
	ULog.w("TypeFragment", v.getId() + "");
	switch (v.getId()) {
	case R.id.type_tv_back:
	    this.onDetach();
	    break;
	case R.id.type_tv_change_type:
	    if (null != popuWindow && !popuWindow.isShowing())
		popuWindow.showAsDropDown(v, 0, 5);
	    else if (null != popuWindow) {
		popuWindow.dismiss();
	    }
	    break;
	case R.id.type_tv_change_type_baishitong:
	    tvTitle.setText("百视通VIP");
	    break;
	case R.id.type_tv_change_type_dianshiju:
	    tvTitle.setText("电视剧");
	    break;
	case R.id.type_tv_change_type_dianying:
	    tvTitle.setText("电影");
	    break;
	case R.id.type_tv_change_type_fengshang:
	    tvTitle.setText("风尚");
	    break;
	case R.id.type_tv_change_type_huashu:
	    tvTitle.setText("华数VIP");
	    break;
	case R.id.type_tv_change_type_jiaoyu:
	    tvTitle.setText("教育");
	    break;
	case R.id.type_tv_change_type_jishi:
	    tvTitle.setText("纪实");
	    break;
	case R.id.type_tv_change_type_minglanmu:
	    tvTitle.setText("名栏目");
	    break;
	case R.id.type_tv_change_type_tiyu:
	    tvTitle.setText("体育");
	    break;
	case R.id.type_tv_change_type_xinwen:
	    tvTitle.setText("新闻");
	    break;
	case R.id.type_tv_change_type_yinyue:
	    tvTitle.setText("音乐");
	    break;
	case R.id.type_tv_change_type_youpeng:
	    tvTitle.setText("优朋VIP");
	    break;
	case R.id.type_tv_change_type_yule:
	    tvTitle.setText("娱乐");
	    break;
	case R.id.type_tv_change_type_zhuanti:
	    tvTitle.setText("专题");
	    break;

	default:
	    break;
	}
    }
}
