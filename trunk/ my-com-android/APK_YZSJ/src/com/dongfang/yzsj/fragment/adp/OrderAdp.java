package com.dongfang.yzsj.fragment.adp;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dongfang.net.HttpUtils;
import com.dongfang.net.http.RequestCallBack;
import com.dongfang.net.http.ResponseInfo;
import com.dongfang.net.http.client.HttpRequest;
import com.dongfang.utils.HttpException;
import com.dongfang.utils.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.bean.OrderProduct;
import com.dongfang.yzsj.dialog.OrderDialog;
import com.dongfang.yzsj.dialog.OrderDialog.OnOrderDialogBtnListener;
import com.dongfang.yzsj.interf.OrderResult;
import com.dongfang.yzsj.params.ComParams;

/**
 * 收藏adapter
 * 
 * @author dongfang
 * 
 */
public class OrderAdp extends BaseAdapter {
	public static final String TAG = OrderAdp.class.getSimpleName();

	private com.dongfang.view.ProgressDialog progDialog;
	private List<OrderProduct> list;
	private Context context;

	private OrderResult orderResult;

	public OrderResult getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(OrderResult orderResult) {
		this.orderResult = orderResult;
	}

	public OrderAdp(Context context, List<OrderProduct> list) {
		this.list = list;
		this.context = context;
		progDialog = com.dongfang.view.ProgressDialog.show(context);
		progDialog.setCancelable(true);
	}

	public List<OrderProduct> getList() {
		return list;
	}

	public void setList(List<OrderProduct> list) {
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_order_adp_item, null);
			holder = new ViewHolder();
			holder.productName = (TextView) convertView.findViewById(R.id.order_tv_product_name);
			holder.productPrice = (TextView) convertView.findViewById(R.id.order_tv_product_price);
			holder.productType = (TextView) convertView.findViewById(R.id.order_tv_product_type);
			holder.productDesc = (TextView) convertView.findViewById(R.id.order_tv_product_desc);
			holder.btn_order = (TextView) convertView.findViewById(R.id.order_tv_sub);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderProduct product = list.get(position);
		holder.productName.setText(product.getServiceProductName());
		if (2 == product.getCostType()) {
			holder.productPrice.setText(product.getPrice() + " M");
		}
		else {
			holder.productPrice.setText(product.getPrice() + " 元");
		}

		holder.productType.setText(product.getType());
		holder.productDesc.setText(product.getDescription());

		holder.btn_order.setVisibility(View.VISIBLE);

		if (product.isHasBuyThis()) {
			holder.btn_order.setText("退订");
			holder.btn_order.setOnClickListener(new MyOnClickListener(position, product.getPayProductNo(), product
					.getCspId(), 2));
		}
		else {
			holder.btn_order.setText("订购");

			if (product.getStatus() == 1) {
				holder.btn_order.setOnClickListener(new MyOnClickListener(position, product.getPayProductNo(), product
						.getCspId(), 1));
			}
			else if (12 == product.getStatus()) { // 12、只显示，不能购买也不能退
				holder.btn_order.setVisibility(View.INVISIBLE);
			}
			else if (11 == product.getStatus()) {// 11、只能购买，不能退
				if (product.isHasBuyThis())
					holder.btn_order.setVisibility(View.INVISIBLE);
				holder.btn_order.setOnClickListener(new MyOnClickListener(position, product.getPayProductNo(), product
						.getCspId(), 1));
			}
			else if (10 == product.getStatus()) {// 10、只能退订，不能购买
				if (product.isHasBuyThis()) {
					holder.btn_order.setText("退订");
					holder.btn_order.setOnClickListener(new MyOnClickListener(position, product.getPayProductNo(),
							product.getCspId(), 2));
				}
				else {
					holder.btn_order.setVisibility(View.INVISIBLE);
				}
			}
		}

		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		TextView productName; // 产品名称
		TextView productPrice; // 价格
		TextView productType; // 类型
		TextView productDesc; // 描述
		TextView btn_order; // 订购按钮
	}

	private class MyOnClickListener implements OnClickListener {
		private String productId;
		private String cspId;
		private int operationType;
		private int position;

		public MyOnClickListener(int position, String productId, String cspId, int operationType) {
			this.productId = productId;
			this.cspId = cspId;
			this.operationType = operationType;
			this.position = position;
		}

		@Override
		public void onClick(final View v) {
			ULog.d(v.toString());

			OrderDialog.show(context, new OnOrderDialogBtnListener() {

				@Override
				public void ok(String phone, String authCode) {
					StringBuilder url = new StringBuilder(ComParams.HTTP_SUBSCRIPTION_PRODUCT);
					url.append("userId=").append(phone);
					url.append("&").append("productId=").append(productId);
					url.append("&").append("verifyCode=").append(authCode);
					url.append("&").append("cspId=").append(cspId);
					url.append("&").append("operationType=").append(operationType);

					ULog.i(url.toString());

					new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							ULog.d("onSuccess  --" + responseInfo.result);
							progDialog.dismiss();

							try {
								JSONObject json = new JSONObject(responseInfo.result);
								if (json.getBoolean("success")) {
									if (1 == operationType) {
										Toast.makeText(context, "订购成功", Toast.LENGTH_LONG).show();
										((TextView) v).setText("退订");
										list.get(position).setHasBuyThis(true);
									}
									else {
										Toast.makeText(context, "退订成功", Toast.LENGTH_LONG).show();
										list.get(position).setHasBuyThis(false);
										((TextView) v).setText("订购");

									}
									if (null != orderResult) {
										orderResult.successed();
									}

								}
								else {
									Toast.makeText(context, json.getJSONArray("error").getString(0), Toast.LENGTH_LONG)
											.show();
									if (null != orderResult) {
										orderResult.failed();
									}

								}
							} catch (JSONException e) {
								if (null != orderResult) {
									orderResult.failed();
								}

								if (1 == operationType) {
									Toast.makeText(context, "订购失败~~~~(>_<)~~~~", Toast.LENGTH_LONG).show();
								}
								else {
									Toast.makeText(context, "退订失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
								}
								e.printStackTrace();
							}
						}

						@Override
						public void onStart() {
							ULog.i("RequestCallBack.onStart");
							progDialog.show();
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ULog.i("RequestCallBack.onFailure");
							progDialog.dismiss();
						}
					});
				}

				@Override
				public void cancel() {

				}
			}).show();

		}
	}

}
