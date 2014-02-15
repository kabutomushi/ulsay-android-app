package com.kabutomushi.ulsayapps;

import java.io.ByteArrayOutputStream;
import java.security.PublicKey;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UlsayActivity extends Activity {

	int mCardId;
	private Button mSayButton;
	private FrameLayout mSayBar;
	private String mData;
	private ListView mListView;
	private ArrayList<NewsCardData> mNewsData;
	private TextView mTouchTextView;

	public String getData() {
		return mData;
	}

	public void setData(String data) {
		this.mData = data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ulsay);

		mListView = (ListView) findViewById(R.id.content);
		mSayButton = (Button) findViewById(R.id.sayButton);
		mSayBar = (FrameLayout) findViewById(R.id.sayBar);
		mSayBar.setVisibility(View.INVISIBLE);

		// ニュースデータを取ってきて表示する
		getNewsRSS();

		// 記事タップでSayボタン表示
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ulsay", "num = " + view);
				// 色かえ
				TextView v = (TextView) ((ViewGroup) ((ViewGroup) view)
						.getChildAt(0)).getChildAt(0);
				v.setTextColor(getResources().getColor(R.color.touchedTitle));

				mTouchTextView = v;
				mCardId = position;
				mSayBar.setVisibility(View.VISIBLE);
			}

		});

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				mSayBar.setVisibility(View.INVISIBLE);
				if (mTouchTextView != null) {
					mTouchTextView.setTextColor(getResources().getColor(
							R.color.Title));

				}
			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub

			}

		});

	}

	public void setCards(ArrayList<NewsCardData> newsData) {
		NewsCardAdapter adapter = new NewsCardAdapter(this, 0, newsData);
		mListView.setAdapter(adapter);
		mNewsData = newsData;
	}

	private void getNewsRSS() {
		GetNewsTask getNewsTask = new GetNewsTask(this);
		getNewsTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.ulsay, menu);
		return true;
	}

	public void onClickSayButton(View view) {

		NewsCardData card = mNewsData.get(mCardId);
		Log.d("ulsay", "title:" + card.getTitle());
//		Toast.makeText(this, "SAY!" + card.getTitle(), Toast.LENGTH_LONG)
//				.show();
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);
		mTouchTextView.setTextColor(getResources().getColor(R.color.Title));
		mSayBar.setVisibility(View.INVISIBLE);
		SayTask sayTask = new SayTask(this);
		sayTask.execute(card.getTitle());
	}

	public void completeSay() {
		NewsCardData card = mNewsData.get(mCardId);
//		Toast.makeText(this, "Said:" + card.getTitle(), Toast.LENGTH_LONG)
//				.show();
		mCardId = 0;
	}
	
	public void onClickTitleBar(View view) {

		getNewsRSS();
		Toast.makeText(this, "Reloading...", Toast.LENGTH_LONG)
				.show();
	}


}
