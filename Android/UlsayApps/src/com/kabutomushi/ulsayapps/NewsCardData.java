package com.kabutomushi.ulsayapps;

import android.graphics.Bitmap;

public class NewsCardData {
	private String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	private String description;
	
	private Bitmap img;	

}
