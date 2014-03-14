package com.next.lottery.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.next.lottery.R;

/**
 * 购物车适配器
 * 
 * @author dongfang
 * 
 */
public class ShoppingCartAllAdapter extends BaseAdapter {

	private List<String> list;
	private Context context;

	public ShoppingCartAllAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Item item;
		if (view == null) {
			item = new Item();
			view = LayoutInflater.from(context).inflate(R.layout.fragment_shoppingcart_all_adp_item, null);
			item.initView(view);
		}
		else {
			view.getTag();
		}

		return view;
	}

	class Item implements View.OnClickListener {
		RadioButton radioBtn;
		ImageView imageView;
		TextView tvTitle;

		LinearLayout layoutEdit;
		RelativeLayout layoutShow;
		RelativeLayout layoutEditSUV;

		TextView tvSUV1Show, tvSUV1Edit;
		TextView tvSUV2Show, tvSUV2Edit;
		TextView tvPriceShow, tvPriceEdit;
		TextView tvNumberShow, tvNumberEdit;
		TextView tvEdit, tVSave;

		ImageView ivDel;
		EditText etNumber;

		private void initView(View view) {
			radioBtn = (RadioButton) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_radiobtn);
			imageView = (ImageView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			tvTitle = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_title);
			layoutEdit = (LinearLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_ll);
			layoutShow = (RelativeLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_rl);
			layoutEditSUV = (RelativeLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_suv_rl);

			tvSUV1Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_suv1);
			tvSUV1Edit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_suv1);
			tvSUV2Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_suv2);
			tvSUV2Edit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_suv2);

			tvNumberShow = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);
			tvNumberEdit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_number);
			tvEdit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_edit);
			tVSave = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_save);

			ivDel = (ImageView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_iv_del);
			etNumber = (EditText) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_et_number);

			tvEdit.setOnClickListener(this);
			tVSave.setOnClickListener(this);
			ivDel.setOnClickListener(this);
			layoutEditSUV.setOnClickListener(this);
			

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fragment_shoppingcart_all_adp_item_show_edit:
				layoutEdit.setVisibility(View.VISIBLE);
				layoutEdit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left));
				layoutShow.setVisibility(View.GONE);
				// layoutEdit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_gone));
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_save:
				layoutShow.setVisibility(View.VISIBLE);
				layoutShow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left));
				layoutEdit.setVisibility(View.GONE);
				// layoutEdit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_gone));
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_iv_del:
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_suv_rl:
				break;

			default:
				break;
			}
		}

	}

}
