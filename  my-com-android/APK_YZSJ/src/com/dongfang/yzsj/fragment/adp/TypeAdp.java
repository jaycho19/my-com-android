package com.dongfang.yzsj.fragment.adp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.df.util.ULog;
import com.dongfang.view.MyImageView;
import com.dongfang.yzsj.LoginActivity;
import com.dongfang.yzsj.R;
import com.dongfang.yzsj.asynctasks.ToDetailAsyncTask;
import com.dongfang.yzsj.bean.Movie;
import com.dongfang.yzsj.params.ComParams;
import com.dongfang.yzsj.utils.User;
import com.dongfang.yzsj.utils.UtilOfTime;
import com.lidroid.xutils.BitmapUtils;

/**
 * 频道列表adp
 * 
 * @author dongfang
 */
public class TypeAdp extends BaseAdapter {

	public static final String TAG = TypeAdp.class.getSimpleName();

	private List<Movie> list = null;
	private Context context;
	private String channelId;

	public TypeAdp(Context context, String channelId) {
		this.context = context;
		this.channelId = channelId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public List<Movie> getList() {
		return list;
	}

	public void setList(List<Movie> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return null == list ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return null == list ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.fragment_type_adp_item, null);
			holder = new ViewHolder();
			holder.iv_placard = (MyImageView) convertView.findViewById(R.id.favorite_imageView_placard);
			holder.tv_title = (TextView) convertView.findViewById(R.id.favorite_tv_title);
			holder.tv_actor = (TextView) convertView.findViewById(R.id.favorite_tv_actor);
			holder.tv_length = (TextView) convertView.findViewById(R.id.favorite_tv_length);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Movie movie = list.get(position);
		holder.iv_placard.setImage(movie.getPC_MEDIA_POSTER_BIG());
//		BitmapUtils.create(context).display(holder.iv_placard, movie.getPC_MEDIA_POSTER_BIG(), 105, 137);
		holder.tv_title.setText(movie.getMEDIA_NAME());
		holder.tv_actor.setText(movie.getMEDIA_ACTORS());
		String length = UtilOfTime.formatSeconds2Date(Long.valueOf(TextUtils.isEmpty(movie.getMEDIA_LENGTH()) ? "0"
				: movie.getMEDIA_LENGTH()));
		holder.tv_length.setText(length);

		convertView.setOnClickListener(new MyOnClickListener(channelId, movie.getId()));

		return convertView;
	}

	/** 列表item内容 */
	private class ViewHolder {
		MyImageView iv_placard; // 海报，截图
		TextView tv_title;
		TextView tv_actor;
		TextView tv_length;
	}

	private class MyOnClickListener implements OnClickListener {
		private String contentId;
		private String channelId;

		public MyOnClickListener(String channelId, String contentId) {
			this.contentId = contentId;
			this.channelId = channelId;
		}

		@Override
		public void onClick(View v) {
			ULog.d(TAG, v.toString());
			if (User.isLogined(context)) {
				new ToDetailAsyncTask(context, channelId, contentId).execute();
			}
			else {
				toLogin(channelId, contentId);
			}
		}
	}

	/** 跳转到登陆页面 */
	private void toLogin(String channelId, String contentId) {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra(ComParams.INTENT_TODO, ToDetailAsyncTask.TAG);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CHANNELID, channelId);
		intent.putExtra(ComParams.INTENT_MOVIEDETAIL_CONNENTID, contentId);
		((Activity) context).startActivityForResult(intent, 10);
	}
}
