package com.bdatsko.tempotimer;

import android.content.res.Configuration;
import android.os.Bundle;

import com.bdatsko.tempotimer.ui.main.settings;
import com.bdatsko.tempotimer.ui.main.tt;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bdatsko.tempotimer.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements settings.SendMessage {

    EditText funny;
    TextView screenSize;
    String getScreenDimensions;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(viewPager);


        funny = findViewById(R.id.funnyEdit);
        screenSize = findViewById(R.id.screenSize);

        /*
        //DETERMINE SCREEN SIZE FOR DEBUGGING PURPOSES.
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            getScreenDimensions = "LargeScreen";
        }

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            getScreenDimensions = "MediumScreen";
        }

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            getScreenDimensions = "SmallScreen";
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        float dpWidth = metrics.widthPixels / density;
        float dpHeight = metrics.heightPixels / density;

        screenSize.setText(getScreenDimensions + "with density of " + metrics.densityDpi + "with a is a multiplier of " + density + "width of " + dpWidth + "dp, height of " + dpHeight);
        */
    }
    @Override
    public void sendData(String message) {
        //String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
        //tt f = (tt) getSupportFragmentManager().findFragmentByTag(tag);
        //f.displayReceivedData(message);
        funny.setText(message);

    }


}



