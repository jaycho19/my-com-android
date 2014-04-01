package com.next.lottery.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongfang.utils.ULog;
import com.dongfang.v4.app.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.next.lottery.R;
import com.next.lottery.listener.OnClickTypeListener;

/**
 * 查看服务优惠活动，选择颜色分类fragment
 * 
 * @author gfan
 * 
 */
public class GoodsDetailInteractiveAndSelectParamsFragment extends BaseFragment {
	protected static String		TAG	= GoodsDetailInteractiveAndSelectParamsFragment.class.getSimpleName();

	private OnClickTypeListener	onClickTypeListener;
	private List<String>		list;
	@ViewInject(R.id.fragment_goods_detail_interactive_tv)
	private TextView			interactiveMore;
	@ViewInject(R.id.fragment_goods_detail_params_tv)
	private TextView			paramsMore;
	@ViewInject(R.id.fragment_goods_detail_interactive_information_tv)
	private TextView			interactiveDetails;
	@ViewInject(R.id.fragment_goods_detail_item_edit_ll)
	private LinearLayout		detailItemEditLin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goods_detail_interactive_params, container, false);
		ViewUtils.inject(this, view);
		initView(view);
		return view;
	}

	private void initView(View view) {
		try {
			interactiveMore.setOnClickListener(new itemClick());
			paramsMore.setOnClickListener(new itemClick());

		} catch (Exception e) {
			ULog.e("initView exception: " + e.getMessage());
		}
	}

	public void setData(OnClickTypeListener onClickTypeListener) {
		this.onClickTypeListener = onClickTypeListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	class itemClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fragment_goods_detail_interactive_tv:
				if (interactiveDetails.getVisibility() == View.VISIBLE)
					interactiveDetails.setVisibility(View.GONE);
				else
					interactiveDetails.setVisibility(View.VISIBLE);

				break;
			case R.id.fragment_goods_detail_params_tv:
				onClickTypeListener.onClickType(new Bundle());
				// if (detailItemEditLin.getVisibility()==View.VISIBLE)
				// detailItemEditLin.setVisibility(View.GONE);
				// else
				// detailItemEditLin.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}

	}

}