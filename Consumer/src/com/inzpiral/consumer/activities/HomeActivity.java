package com.inzpiral.consumer.activities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inzpiral.consumer.R;
import com.inzpiral.consumer.fragments.FragmentAdapter;
import com.inzpiral.consumer.models.BaseNode;
import com.inzpiral.consumer.models.Evaluation;
import com.inzpiral.consumer.utils.ConsumerDeserializer;
import com.inzpiral.consumer.utils.NetworkUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class HomeActivity extends SlidingFragmentActivity {
	
	// Manejo de tabs
	private FragmentAdapter mAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;
	protected ListFragment mFrag;

	// Manejo de evaluacion
	private Evaluation mEvaluation;
	private String mURL = "http://10.0.1.13/test/consumo_masivo.json";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.loadEvaluation();
		setContentView(R.layout.fragmenthome);
		
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new com.inzpiral.consumer.fragments.SampleListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		this.loadTabs();
	}

	@Override
	public void onBackPressed(){
		moveTaskToBack(true);
	}

	private void loadTabs(){
		mAdapter = new FragmentAdapter(getSupportFragmentManager());

		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);		
	}
	
	public void loadEvaluation() {
//		mMainView.enableAll(false);
		
		InputStream source = NetworkUtils.retrieveStream(mURL);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BaseNode.class, new ConsumerDeserializer());
        
        final Gson gson = builder.create();
        final Reader reader = new InputStreamReader(source);
        
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
				Calendar cal = Calendar.getInstance();
				long init = cal.getTimeInMillis();
				
            	mEvaluation = gson.fromJson(reader, Evaluation.class);
            	
				cal = Calendar.getInstance();
				System.out.println("Parsing time: " + (cal.getTimeInMillis() - init));
				init = cal.getTimeInMillis();
            }
        }, "parsing", 1024 * 1024);
        t.start();
        
        try {
			t.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

//		mMainView.enableAll(true);
	}

	public Evaluation getEvaluation() {
		return mEvaluation;
	}
	
}

