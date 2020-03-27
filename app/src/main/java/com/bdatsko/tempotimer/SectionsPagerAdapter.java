package com.bdatsko.tempotimer.ui.main;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bdatsko.tempotimer.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    @StringRes
    //private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private static final int[] TAB_TITLES = new int[]{
            R.drawable.homepng,
            R.drawable.alarm_24px,
            R.drawable.av_timer_24px,
            R.drawable.settings_24px
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }



    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //
        Fragment fragment  = null;
        switch (position) {
            case 0:
                fragment = new home();

                break;
            case 1:
                fragment = new tt();
                break;
            case 2:
                fragment = new sw();
                break;
            case 3:
                fragment = new settings();

                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}