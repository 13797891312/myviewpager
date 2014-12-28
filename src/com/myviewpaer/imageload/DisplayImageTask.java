package com.myviewpaer.imageload;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DisplayImageTask implements Runnable {
	private ImageView imageView;
	private Bitmap bitmap;
	private ProgressBar bar;

	public DisplayImageTask(ImageView imageView, Bitmap bitmap, ProgressBar bar) {
		// TODO Auto-generated constructor stub
		this.imageView = imageView;
		this.bitmap = bitmap;
		this.bar = bar;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		imageView.setImageBitmap(bitmap);
		if (bar != null) {
			bar.setVisibility(View.GONE);
		}
	}
}
