package com.kabutomushi.ulsayapps;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class SayTask extends AsyncTask<String, Integer, String> {
	private UlsayActivity mActivity;

	public SayTask(UlsayActivity activity) {
		mActivity = activity;
	}

	@Override
	protected String doInBackground(String... params) {

		String sayText = params[0];

		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder uri = new StringBuilder(
				"http://www5013uo.sakura.ne.jp:3000/sendSay");
		HttpPost request = new HttpPost(uri.toString());
		HttpResponse response = null;

		// クエリ作成
		List<NameValuePair> word = new ArrayList<NameValuePair>();
		word.add(new BasicNameValuePair("word", sayText));
		try {
			request.setEntity(new UrlEncodedFormEntity(word, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {
			response = httpClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			// 接続問題なかったらデータ取り込み
			if (status == HttpStatus.SC_OK) {
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(outputStream);
					String data = outputStream.toString();
					Log.d("ulsay", "result:" + data);

				} catch (Exception e) {
					Log.d("ulsay", "データ取得エラー");
				}
			}
		} catch (Exception e) {
			return "Error:" + e.getMessage();
		}
		return sayText;
	}

	protected void onPostExecute(String result) {
		mActivity.completeSay();
	}
}
