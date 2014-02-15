package com.kabutomushi.ulsayapps;

import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class NewsCardAdapter extends ArrayAdapter<NewsCardData> {
	private LayoutInflater layoutInflater;
	private int mLastAnimationPosition = 0;
	private Context mContext;
	private Typeface mFace;

	public NewsCardAdapter(Context context, int textViewResourceId,
			List<NewsCardData> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mFace = Typeface.createFromAsset(mContext.getAssets(), "titleFont.ttf");
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsCardData item = (NewsCardData) getItem(position);

		if (null == convertView) {
			convertView = layoutInflater.inflate(R.layout.news_card, null);
		}

		//データ入れる
		TextView textView;
		textView = (TextView) convertView.findViewById(R.id.newsTitle);
		//フォント設定
		textView.setTypeface(mFace);
		textView.setText(item.getTitle());
		//データ入れる
		TextView descriptionTextView;
		descriptionTextView = (TextView) convertView.findViewById(R.id.newsDescription);
		//フォント設定
		descriptionTextView.setTypeface(mFace);
		descriptionTextView.setText(item.getTitle());

		// アニメーションを設定
		if (mLastAnimationPosition < position) {
			Animation animation = AnimationUtils.loadAnimation(mContext,
					R.anim.motion);
			convertView.startAnimation(animation);
			mLastAnimationPosition = position;
		}

		return convertView;
	}
}
