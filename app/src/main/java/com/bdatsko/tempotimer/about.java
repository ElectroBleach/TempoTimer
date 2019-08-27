package com.bdatsko.tempotimer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class about extends Activity {

    Button backHomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView reportbugs = (TextView) findViewById(R.id.reportbugs);
        reportbugs.setMovementMethod(LinkMovementMethod.getInstance());

        backHomeButton = (Button) findViewById(R.id.backBtnAbout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backHome = new Intent(getApplicationContext(), mainMenu.class);
                startActivity(backHome);
            }
        });
    }


    public void onBackPressed() {
        Intent home = new Intent(this,mainMenu.class);
        startActivity(home);
    }
}
