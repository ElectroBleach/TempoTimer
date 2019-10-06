package com.bdatsko.tempotimer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

public class sliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public sliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public int[] slide_images={
            R.drawable.maintt,
            R.drawable.coolswimmer,
            R.drawable.clock

    };


    public String[] slide_headings = {
        "Welcome!",
        "TempoTimer",
        "Stopwatch"
    };

    public String[] slide_descs = {
            "Prepare for your next swim with help from our nifty race preparation tool. Swipe to select your mode!",
                    "Enter your time, tempo, etc., and we'll generate your splits and play your tempo back to you. Perfect for pre-race visualization!",
                    "Your standard “run-of-the-mill” stopwatch for less arduous tasks."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slidelayout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideImageView);
        TextView slideTitle = (TextView) view.findViewById(R.id.slideTitle);
        TextView slideDesc = (TextView) view.findViewById(R.id.slideDesc);
        TextView slidelayout = (TextView) view.findViewById(R.id.setbackground);

        slideImageView.setImageResource(slide_images[position]);
        slideTitle.setText(slide_headings[position]);
        slideDesc.setText(slide_descs[position]);
        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
