package com.bdatsko.tempotimer.ui.main;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bdatsko.tempotimer.R;

public class sw extends Fragment {
    TextView display;
    Button start, pause, reset, back;
    long milli, startTime, timeBuff, updateTime = 0L, pauseOffset;
    int seconds, minutes, milliseconds;
    Handler handler;
    ImageView startBtnDummy;
    int paused, running = 0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout of this fragment
        final View v = inflater.inflate(R.layout.swl, container, false);

        handler = new Handler();
        super.onCreate(savedInstanceState);
        display = (TextView) v.findViewById(R.id.display);
        start = (Button) v.findViewById(R.id.startbtn);
        pause = (Button) v.findViewById(R.id.pausebtn);
        reset = (Button) v.findViewById(R.id.resetbtn);
        startBtnDummy = (ImageView) v.findViewById(R.id.startBtnDummy);

        final AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.playpause);
        final AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.pauseplay);

        pause.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);

        startBtnDummy.setImageDrawable(animatedVectorDrawable2);

        Drawable drawableu = animatedVectorDrawable2.getCurrent();

        if (drawableu instanceof Animatable) {
            ((Animatable) drawableu).start();
        }



        final Runnable runnable = new Runnable() {
            public void run() {
                pause.setEnabled(true);
                start.setEnabled(false);
                milli = SystemClock.uptimeMillis() - startTime;
                updateTime = timeBuff + milli;
                seconds = (int) (updateTime / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                milliseconds = (int) (updateTime % 1000) / 10;
                display.setText(minutes + ":" + seconds + "." + milliseconds);
                if (minutes >= 1 && seconds <= 10) {
                    display.setText(minutes + ":" + "0" + seconds + "." + milliseconds);
                }
                if (minutes < 1 && seconds <= 10) {
                    display.setText("0" + seconds + "." + milliseconds);
                }
                if (minutes < 1 && seconds >= 10) {
                    display.setText(+seconds + "." + milliseconds);
                }

                handler.postDelayed(this, 0);
            }
        };





        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                reset.setVisibility(View.GONE);

                        startBtnDummy.setImageDrawable(animatedVectorDrawable);

                        Drawable drawableu = animatedVectorDrawable.getCurrent();

                        if (drawableu instanceof Animatable) {
                            ((Animatable) drawableu).start();
                        }

            }
        });



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                pause.setEnabled(false);
                start.setEnabled(true);
                timeBuff += milli;
                handler.removeCallbacks(runnable);
                reset.setVisibility(View.VISIBLE);
                reset.setEnabled(true);


                        startBtnDummy.setImageDrawable(animatedVectorDrawable2);

                        Drawable drawableu = animatedVectorDrawable2.getCurrent();

                        if (drawableu instanceof Animatable) {
                            ((Animatable) drawableu).start();
                        }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milli = 0L;
                startTime = 0;
                timeBuff = 0;
                updateTime = 0;
                seconds = 0;
                minutes = 0;
                milliseconds = 0;
                display.setText("00:00");
            }
        });

        return v;
    }




}
