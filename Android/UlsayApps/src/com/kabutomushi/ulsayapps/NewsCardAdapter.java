package com.kabutomushi.ulsayapps;

import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
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
	private Typeface mDescriptionFace;

	public NewsCardAdapter(Context context, int textViewResourceId,
			List<NewsCardData> objects) {
		super(context, textViewResourceId, objects);
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mDescriptionFace = Typeface.createFromAsset(mContext.getAssets(), "descriptionFont.ttf");
		
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
		textView.setTypeface(mDescriptionFace);
		//文字数調整
		if(item.getTitle().length() < 40){
			item.setTitle(item.getTitle() + "　　　　　　　　　　　　　　　　　　　　");
		}
		textView.setText(item.getTitle());
		//データ入れる
		TextView descriptionTextView;
		descriptionTextView = (TextView) convertView.findViewById(R.id.newsDescription);
		//フォント設定
		descriptionTextView.setTypeface(mDescriptionFace);
		descriptionTextView.setText(item.getDescription());

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
