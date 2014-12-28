package com.myviewpaer.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.myviewpager.R;
import com.myviewpaer.activity.TagViewPager.OnGetView;
import com.myviewpaer.imageload.Imageloader_homePager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private List<String> list=new ArrayList<String>();
	private TagViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		list.add("http://www.wanzhuan6.com:80/userFiles/system/news/image/201412011255376019265.jpg");
		list.add("http://www.wanzhuan6.com:80/userFiles/system/news/image/201411201603163982173.jpg");
		list.add("http://www.wanzhuan6.com:80/userFiles/system/news/image/201411122014031400654.jpg");
		viewPager=(TagViewPager) findViewById(R.id.myviewpager);
		viewPager.init(R.drawable.ic_launcher, R.drawable.ic_launcher1, 20, 5, 2, 20);
		viewPager.setAutoNext(true, 500);
		viewPager.setOnGetView(new OnGetView() {
			
			@Override
			public View getView(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				ImageView iv=new ImageView(MainActivity.this);
				Imageloader_homePager.displayImage(list.get(position), iv, new Handler(), null);
				container.addView(iv);
				return iv;
			}
		});
		viewPager.setAdapter(list.size());
		
	}
}
