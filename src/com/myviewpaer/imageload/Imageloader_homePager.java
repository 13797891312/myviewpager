package com.myviewpaer.imageload;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Imageloader_homePager {
	private static ImageCache imageCache = new ImageCache(8 * 1024 * 1024);

	public static void displayImage(final String url, ImageView imageView, Handler hd,
			ProgressBar bar) {
		Bitmap bitmap = imageCache.get(url);
		if (bitmap != null && !bitmap.isRecycled()) {
			imageView.setImageBitmap(bitmap);
			if (bar != null) {
				bar.setVisibility(View.GONE);
			}
			return;
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			imageView.setImageBitmap(bitmap);
			imageCache.put("url", bitmap);
			if (bar != null) {
				bar.setVisibility(View.GONE);
			}
			return;
		}
		urlToBitmap(url, imageView, hd, bar);
	}

	public static void urlToBitmap(final String url, final ImageView imageView,
			final Handler hd, final ProgressBar bar) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpURLConnection connection = (HttpURLConnection) new URL(
							url).openConnection();
					connection.setConnectTimeout(10000);
					connection.setReadTimeout(10000);
					InputStream is = connection.getInputStream();
					// 获取创建Bitmap的选项对象
					Options options = new Options();
					// 设置图片大小为原图片的1/2
					options.inJustDecodeBounds = false;
					if (Build.MODEL.equals("HUAWEI U9510E")) {
						options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					} else {
						options.inPreferredConfig = Bitmap.Config.RGB_565;
					}
					options.inPurgeable = true;
					options.inInputShareable = true;
					Bitmap bitmap = BitmapFactory.decodeStream(is, null,
							options);
					if (bitmap != null) {
						imageCache.put(url, bitmap);
						hd.post(new DisplayImageTask(imageView,
								bitmap, bar));
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/*** 清空缓存 ****/
	public void clearCache() {
		Map<String, Bitmap> m = imageCache.snapshot();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String key;
			key = (String) it.next();
			m.get(key).recycle();
			imageCache.remove(key);
		}
		imageCache.evictAll();
	}
}
