package com.bdatsko.tempotimer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

public class mainMenu extends AppCompatActivity {
    Button tempoTimer, stopWatch, about;
    RelativeLayout loadingSplash;
    int setViewWhite;
    Window window = this.getWindow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tempoTimer = (Button) findViewById(R.id.ttbtn);
        stopWatch = (Button) findViewById(R.id.swbtn);
        about = (Button) findViewById(R.id.aboutbtn);
        loadingSplash = (RelativeLayout) findViewById(R.id.progressBackground);
        loadingSplash.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        stopWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSplash.setVisibility(View.VISIBLE);
                runFadeInAnimation();
                Intent swintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(swintent);
                finish();
            }
        });

        tempoTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSplash.setVisibility(View.VISIBLE);
                runFadeInAnimation();

                Intent ttintent = new Intent(getApplicationContext(), tt.class);
                startActivity(ttintent);
                finish();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSplash.setVisibility(View.VISIBLE);
                runFadeInAnimation();

                Intent aboutintent = new Intent(getApplicationContext(), about.class);
                startActivity(aboutintent);
                finish();
            }
        });
    }

    private void runFadeInAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        a.reset();
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.progressBackground);
        ll.clearAnimation();
        ll.startAnimation(a);
    }

}


