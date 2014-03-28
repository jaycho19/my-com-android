package com.next.lottery.fragment.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.dongfang.utils.ULog;
import com.next.lottery.R;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.utils.ComParams;
import com.next.lottery.utils.Keys;
import com.next.lottery.utils.Util;

/**
 * 购物车适配器
 * 
 * @author dongfang
 * 
 */
public class ShoppingCartAllAdapter extends BaseAdapter {

	private List<String>	list;
	private Context			context;
	private Handler			handler;
	private int				isAllSelected	= 0;	// 1表示全选，2 表示全不选
	

	public ShoppingCartAllAdapter(Context context, List<String> list, Handler handler) {
		this.list = list;
		this.context = context;
		this.handler = handler;

	}

	public void setAllSelected(int flag) {
		this.isAllSelected = flag;
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
		Item item = new Item();
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.fragment_shoppingcart_all_adp_item, null);
		}
		else {
			view.getTag();
		}
		item.initView(view,position);
		
//      if (!item.checkBox.isChecked()) {
//    	  item.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable
//    			  .btn_radio_01));
//	}else{
//		item.checkBox.setButtonDrawable(context.getResources().getDrawable(R.drawable
//  			  .btn_radio_02));
//	}
		switch (isAllSelected) {
		case 1:
			item.checkBox.setChecked(true);
			break;
		case 2:
			item.checkBox.setChecked(false);
			break;

		default:
			break;
		}
		return view;
	}

	class Item implements View.OnClickListener, OnCheckedChangeListener {
		CheckBox		checkBox;

		ImageView		imageView;
		TextView		tvTitle;

		LinearLayout	layoutEdit;
		RelativeLayout	layoutShow;
		RelativeLayout	layoutEditSKU;

		TextView		tvSKU1Show, tvSKU1Edit;
		TextView		tvSKU2Show, tvSKU2Edit;
		TextView		tvPriceShow, tvPriceEdit;
		TextView		tvNumberShow, tvNumberEdit;
		TextView		tvEdit, tVSave;

		TextView		tvEditPrice, tvShowPrice;

		ImageView		ivDel;
		EditText		etNumber;
		
		int position;

		private void initView(View view,int position) {
			this.position = position;
			checkBox = (CheckBox) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_radiobtn);
			imageView = (ImageView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_iv);
			tvTitle = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_title);
			layoutEdit = (LinearLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_ll);
			layoutShow = (RelativeLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_rl);
			layoutEditSKU = (RelativeLayout) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_suv_rl);

			tvSKU1Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_sku1);
			tvSKU1Edit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_sku1);
			tvSKU2Show = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_sku2);
			tvSKU2Edit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_sku2);

			tvNumberShow = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_number);
			tvNumberEdit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_number);
			tvEdit = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_edit);
			tVSave = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_save);

			ivDel = (ImageView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_iv_del);
			etNumber = (EditText) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_et_number);

			tvEditPrice = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_edit_price);
			tvShowPrice = (TextView) view.findViewById(R.id.fragment_shoppingcart_all_adp_item_show_price);

			tvEdit.setOnClickListener(this);
			tVSave.setOnClickListener(this);
			// checkBox.setOnClickListener(this);
			ivDel.setOnClickListener(this);
			layoutEditSKU.setOnClickListener(this);
			
			checkBox.setOnCheckedChangeListener(this);
			etNumber.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					ULog.i("onTextChanged-->" + s);
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					ULog.i("beforeTextChanged-->" + s);
				}

				@Override
				public void afterTextChanged(Editable s) {
					ULog.i("afterTextChanged-->" + s);
					int num = 1;
					if (Util.IsNumeric(String.valueOf(s))) {
						try {
							num = Integer.parseInt(String.valueOf(s));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						tvEditPrice.setText("" + String.valueOf(380 * num));
						tvShowPrice.setText("" + String.valueOf(380 * num));

						tvNumberEdit.setText("X" + num);
						tvNumberShow.setText("X" + num);

						if (checkBox.isChecked()) {
							Message msg = new Message();
							msg.what = Keys.MSG_REFRESH_BUY_NUM_ALL_GOODS_PRICE;
							msg.arg1 = 380 * num;
							handler.sendMessage(msg);
						}

					}

				}
			});

		}

		private SKUBean getTestSKUBean() {
			ArrayList<SKUEntity> all = new ArrayList<SKUEntity>();
				SKUEntity skuEntity = new SKUEntity();
				skuEntity.setSkuName("颜色分类");
				ArrayList<String> al = new ArrayList<String>();
				
				al.add("红色");
				al.add("黄色");
				al.add("灰色");
				al.add("绿色");
				skuEntity.setSkuTypesList(al);
				
				SKUEntity skuEntity1 = new SKUEntity();
				skuEntity1.setSkuName("尺码");
				ArrayList<String> al1 = new ArrayList<String>();
				
				for (int j = 0; j < 18; j++)
					al1.add("尺码" + j);
				skuEntity1.setSkuTypesList(al1);
				all.add(skuEntity1);
				all.add(skuEntity);
			SKUBean skuBean = new SKUBean();
			skuBean.setSkuList(all);
			return skuBean;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.fragment_shoppingcart_all_adp_item_show_edit:
				layoutEdit.setVisibility(View.VISIBLE);
				layoutEdit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left));
				layoutShow.setVisibility(View.GONE);
				// layoutEdit.startAnimation(AnimationUtils.loadAnimation(context,
				// R.anim.right_to_gone));
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_save:
				layoutShow.setVisibility(View.VISIBLE);
				layoutShow.startAnimation(AnimationUtils.loadAnimation(context, R.anim.right_to_left));
				layoutEdit.setVisibility(View.GONE);
				
				/**保存值 */
				
				tvNumberShow.setText(tvNumberEdit.getText());
				
				tvSKU1Show.setText(tvSKU1Edit.getText());
				tvSKU2Show.setText(tvSKU2Edit.getText());
				tvShowPrice.setText(tvEditPrice.getText());
				
				// layoutEdit.startAnimation(AnimationUtils.loadAnimation(context,
				// R.anim.right_to_gone));
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_iv_del:
				ULog.i("position-->"+position);
			    notifyDataSetChanged();
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_suv_rl:
				ShoppingSelectSKUDialog.show(context, getTestSKUBean(),onSkuResultListener);
				break;

			default:
				break;
			}
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			ULog.i("isChecked-->"+isChecked);
			
			if (buttonView.getId()!=R.id.fragment_shoppingcart_all_adp_item_radiobtn) 
				return;
			Message msg = new Message();
			if (isChecked) {
				msg.what=Keys.MSG_REFRESH_BUY_NUM_PLUS;
				msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
				handler.sendMessage(msg);
			}else{
				msg.what=Keys.MSG_REFRESH_BUY_NUM_REDUCE;
				msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
				handler.sendMessage(msg);
			}
			
//			notifyDataSetChanged();

		}
		
		OnSkuResultListener onSkuResultListener = new OnSkuResultListener() {
			
			@Override
			public void onSkuResult(SKUBean bean, String num) {
				try {
					tvNumberShow.setText(num);
					tvNumberEdit.setText(num);
					etNumber.setText(num);
					
					String color =  bean.getSkuList().get(1).getSkuName()+":"+bean.getSkuList().get(1).getSkuTypesList().get(0);
					String size =  bean.getSkuList().get(0).getSkuName()+":"+bean.getSkuList().get(0).getSkuTypesList().get(0);
					tvSKU1Edit.setText(color);
					tvSKU2Edit.setText(size);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ULog.e(e.toString());
				}
				
				
			}
		};
	}

}
