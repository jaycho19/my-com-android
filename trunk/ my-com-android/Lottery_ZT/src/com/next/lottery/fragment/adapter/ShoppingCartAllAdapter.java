package com.next.lottery.fragment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.utils.ULog;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.next.lottery.R;
import com.next.lottery.beans.BaseGateWayInterfaceEntity;
import com.next.lottery.beans.SKUBean;
import com.next.lottery.beans.SKUEntity;
import com.next.lottery.beans.SKUItem;
import com.next.lottery.beans.ShopCartsInfo;
import com.next.lottery.beans.SkuList;
import com.next.lottery.dialog.ShoppingSelectSKUDialog;
import com.next.lottery.listener.OnSkuResultListener;
import com.next.lottery.nets.HttpActions;
import com.next.lottery.utils.Keys;
import com.next.lottery.utils.Util;

/**
 * 购物车适配器
 * 
 * @author dongfang
 * 
 */
public class ShoppingCartAllAdapter extends BaseAdapter {

	private ArrayList<ShopCartsInfo>	list;
	private Context						context;
	private Handler						handler;
	private boolean						isAllSelected;	// 1表示全选，2 表示全不选

	public ShoppingCartAllAdapter(Context context, ArrayList<ShopCartsInfo> shopCartslist, Handler handler) {
		this.list = shopCartslist;
		this.context = context;
		this.handler = handler;

	}

	public void setAllSelected(boolean flag) {
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
		item.initView(view, position);

		if (list.get(position).getIsSelected() == 1) {
			item.checkBox.setChecked(true);
		}
		else {
			item.checkBox.setChecked(false);
		}

		// switch (isAllSelected) {
		// case 1:
		// item.checkBox.setChecked(true);
		// break;
		// case 2:
		// item.checkBox.setChecked(false);
		// break;
		//
		// default:
		// break;
		// }
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

		int				position;

		private void initView(View view, final int position) {
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

			tvShowPrice.setText(list.get(position).getPrice());
			tvEditPrice.setText(list.get(position).getPrice());

			// checkBox.setOnCheckedChangeListener(this);

			checkBox.setOnClickListener(this);
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
					String goodsNum =String.valueOf(s);
					if (Util.IsNumeric(goodsNum)) {
						try {
							num = Integer.parseInt(goodsNum);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}
						tvEditPrice.setText(String.valueOf( Float.parseFloat(list.get(position).getPrice())* num));
						tvNumberEdit.setText("X" + num);

						if (checkBox.isChecked()) {
							Message msg = new Message();
							msg.what = Keys.MSG_REFRESH_BUY_NUM_ALL_GOODS_PRICE;
							msg.arg1 = (int) (Float.parseFloat(list.get(position).getPrice()) * num);
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
			ArrayList<SKUItem> al = new ArrayList<SKUItem>();

			al.add(new SKUItem("红色"));
			al.add(new SKUItem("黄色"));
			al.add(new SKUItem("灰色"));
			al.add(new SKUItem("绿色"));
			skuEntity.setSkuTypesList(al);

			SKUEntity skuEntity1 = new SKUEntity();
			skuEntity1.setSkuName("尺码");
			ArrayList<SKUItem> al1 = new ArrayList<SKUItem>();

			for (int j = 0; j < 18; j++)
				al1.add(new SKUItem("尺码" + j));
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

				/** 保存值 */

				tvNumberShow.setText(tvNumberEdit.getText());

				tvSKU1Show.setText(tvSKU1Edit.getText());
				tvSKU2Show.setText(tvSKU2Edit.getText());
				tvShowPrice.setText(tvEditPrice.getText());

				// layoutEdit.startAnimation(AnimationUtils.loadAnimation(context,
				// R.anim.right_to_gone));
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_iv_del:
				ULog.i("position-->" + position);
				delShopCart();
				list.remove(position);
				notifyDataSetChanged();
				break;
			case R.id.fragment_shoppingcart_all_adp_item_edit_suv_rl:
//				ShoppingSelectSKUDialog.show1(context, getTestSKUBean(), onSkuResultListener);
				break;
				
			case R.id.fragment_shoppingcart_all_adp_item_radiobtn:
				ULog.i("isChecked-->" + ((CheckBox)v).isChecked());
				boolean isCheck = ((CheckBox)v).isChecked();
				Message msg = new Message();
				if (isCheck) {
					msg.what = Keys.MSG_REFRESH_BUY_NUM_PLUS;
					msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
					handler.sendMessage(msg);
					list.get(position).setIsSelected(1);
				}else{
					list.get(position).setIsSelected(0);
					msg.what = Keys.MSG_REFRESH_BUY_NUM_REDUCE;
					msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
					handler.sendMessage(msg);
				}
				
				break;

			default:
				break;
			}
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			ULog.i("isChecked-->" + isChecked);

			if (buttonView.getId() != R.id.fragment_shoppingcart_all_adp_item_radiobtn)
				return;
			Message msg = new Message();
			if (isChecked && !isAllSelected) {
				msg.what = Keys.MSG_REFRESH_BUY_NUM_PLUS;
				msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
				handler.sendMessage(msg);
				list.get(position).setIsSelected(1);
			}
			else if (!isAllSelected) {
				list.get(position).setIsSelected(0);
				msg.what = Keys.MSG_REFRESH_BUY_NUM_REDUCE;
				msg.arg1 = Integer.parseInt((String) tvShowPrice.getText());
				handler.sendMessage(msg);
			}

			// notifyDataSetChanged();

		}

		OnSkuResultListener	onSkuResultListener	= new OnSkuResultListener() {

													@Override
													public void onSkuResult(ArrayList<SkuList> beanResult) {/*
														try {
															tvNumberEdit.setText("x"+bean.getNum());
															etNumber.setText(String.valueOf(bean.getNum()));

															String color = bean.getSkuList().get(1).getSkuName() + ":"
																	+ bean.getSkuList().get(1).getSkuTypesList().get(0).getName();
															String size = bean.getSkuList().get(0).getSkuName() + ":"
																	+ bean.getSkuList().get(0).getSkuTypesList().get(0).getName();
															tvSKU1Edit.setText(color);
															tvSKU2Edit.setText(size);
														} catch (Exception e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
															ULog.e(e.toString());
														}

													*/}
												};
	}

	public void delShopCart() {
		String url = HttpActions.DelShopCarts();
		ULog.d("DelShopCarts url = " + url);
		new HttpUtils().send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				// progDialog.show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// progDialog.dismiss();
				ULog.d(responseInfo.result);

				BaseGateWayInterfaceEntity bean = new Gson().fromJson(responseInfo.result,
						BaseGateWayInterfaceEntity.class);
				if (null != bean && bean.getCode() == 0) {
					Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(context, bean.getMsg(), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				ULog.e(error.toString() + "\n" + msg);
			}
		});

	}

}
