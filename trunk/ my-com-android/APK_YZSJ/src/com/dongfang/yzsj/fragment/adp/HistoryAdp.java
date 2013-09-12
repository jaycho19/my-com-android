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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.df.util.ULog;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 收藏adapter
 * 
 * @author dongfang
 * 
 */
public class HistoryAdp extends BaseAdapter {

	public static final String TAG = HistoryAdp.class.getSimpleName();
	private com.dongfang.view.ProgressDialog progDialog;
	private List<Movie> list;
	private Context context;

	public HistoryAdp(Context context, List<Movie> list) {
		this.list = list;
		this.context = context;
		progDialog = com.dongfang.view.ProgressDialog.show(context);
		progDialog.setCancelable(true);
	}

	public List<Movie> getList() {
		return list;
	}

	public void setList(List<Movie> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_favorite_adp_item, null);
			holder = new ViewHolder();
			holder.iv_placard = (ImageView) convertView.findViewById(R.id.favorite_imageView_placard);
			holder.tv_title = (TextView) convertView.findViewById(R.id.favorite_tv_title);
			holder.tv_actor = (TextView) convertView.findViewById(R.id.favorite_tv_actor);
			holder.tv_des = (TextView) convertView.findViewById(R.id.favorite_tv_des);
			holder.btn_cancel = (TextView) convertView.findViewById(R.id.favorite_tv_cancel);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Movie movie = list.get(position);
		BitmapUtils.create(context).display(holder.iv_placard, movie.getPC_MEDIA_POSTER_BIG());
		// BitmapUtils.create(context).display(holder.iv_placard, movie.getPC_MEDIA_POSTER_BIG(), 105, 137);
		holder.tv_des.setText(movie.getMEDIA_INTRO());
		holder.tv_actor.setText(movie.getMEDIA_ACTORS());
		holder.tv_title.setText(movie.getMEDIA_NAME());
		holder.btn_cancel.setText("删除记录");
		holder.btn_cancel.setOnClickListener(new MyOnClickDelListener(movie.getId(), position));

		// holder.tv_title.setOnClickListener(new MyOnClickDetListener("0", movie.getId()));
		// holder.iv_placard.setOnClickListener(new MyOnClickDetListener("0", movie.getId()));
		convertView.setOnClickListener(new MyOnClickDetListener("0", movie.getId()));

		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		ImageView iv_placard; // 海报，截图
		TextView tv_title;
		TextView tv_actor;
		TextView tv_des;
		TextView btn_cancel;
	}

	private void delElementByPositon(int position) {
		list.remove(position);
		this.notifyDataSetChanged();
	}

	private class MyOnClickDetListener implements OnClickListener {
		private String channelId;
		private String contentId;

		public MyOnClickDetListener(String channelId, String contentId) {
			this.contentId = contentId;
			this.channelId = channelId;
		}

		@Override
		public void onClick(View v) {
			new ToDetailAsyncTask(context, channelId, contentId).execute();
		}

	}

	private class MyOnClickDelListener implements OnClickListener {
		private String contentId;
		private int position;

		public MyOnClickDelListener(String contentId, int position) {
			this.contentId = contentId;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			ULog.d(TAG, v.toString());
			StringBuilder url = new StringBuilder(ComParams.HTTP_HISTORY_DEL);
			url.append("token=").append(User.getToken(context)).append("&");
			url.append("userTelephone=").append(User.getPhone(context));

			ULog.i(TAG, url.toString());

			new HttpUtils().send(HttpRequest.HttpMethod.GET, url.toString(), new RequestCallBack<String>() {
				@Override
				public void onLoading(long total, long current) {
					ULog.d(TAG, "RequestCallBack.onLoading total = " + total + "; current = " + current);
				}

				@Override
				public void onSuccess(String result) {
					ULog.d(TAG, "onSuccess  --" + result);
					progDialog.dismiss();

					try {
						JSONObject json = new JSONObject(result);
						if (json.getBoolean("success")) {
							Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
							delElementByPositon(position);
						}
						else {
							Toast.makeText(context, json.getJSONArray("error").getString(0), Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						Toast.makeText(context, "删除失败~~~~(>_<)~~~~ ", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				}

				@Override
				public void onStart() {
					ULog.i(TAG, "RequestCallBack.onStart");
					progDialog.show();
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					ULog.i(TAG, "RequestCallBack.onFailure");
					progDialog.dismiss();
				}
			});
		}
	}

}
