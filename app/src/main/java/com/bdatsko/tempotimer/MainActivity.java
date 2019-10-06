package com.bdatsko.tempotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {
    TextView display;
    Button start,pause,reset, back;
    long milli, startTime, timeBuff, updateTime = 0L, pauseOffset;
    int seconds, minutes, milliseconds;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        handler = new Handler();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView) findViewById(R.id.display);
        start = (Button) findViewById(R.id.startbtn);
        pause = (Button) findViewById(R.id.pausebtn);
        reset = (Button) findViewById(R.id.resetbtn);
        back = (Button) findViewById(R.id.backButtonSW);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setEnabled(false);
                start.setEnabled(true);
                timeBuff += milli;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
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
                display.setText("00:00:00");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backHome = new Intent(getApplicationContext(), menuMain.class);
                startActivity(backHome);
            }
        });
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            pause.setEnabled(true);
            start.setEnabled(false);
            milli = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milli;
            seconds = (int) (updateTime/1000);
            minutes = seconds/60;
            seconds = seconds % 60;
            milliseconds = (int) (updateTime%1000)/10;
            display.setText(minutes + ":" + seconds + "." + milliseconds);
            if(minutes>=1 && seconds<=10) {
                display.setText(minutes + ":" + "0" + seconds + "." + milliseconds);
            }
            if(minutes<1 && seconds<=10) {
                display.setText("0" + seconds + "." + milliseconds);
            }
            if(minutes<1 && seconds>=10) {
                display.setText(+ seconds + "." + milliseconds);
            }

            handler.postDelayed(this, 0);
         }
     };

    public void onBackPressed() {
        Intent home = new Intent(this,menuMain.class);
        startActivity(home);
    }
}
