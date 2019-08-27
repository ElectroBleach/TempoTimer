package com.bdatsko.tempotimer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class tt extends AppCompatActivity {
    TextView display, debugOutput, debugOutput2, courseOutput;
    Button start,pause,reset, back, add, remove;
    EditText goalMinutes, goalSeconds, goalMilli, splitOffset, splitIn, tempo, secondsInput, milliInput;
    public Spinner course, length, tempoSeconds, tempoMilliTenths, tempoMilliHundredths;
    long milli, startTime, timeBuff, updateTime = 0L, pauseOffset;
    int milliValue, seconds, minutes, milliseconds, fiftyCount, counter = 0, epicSum = 0, arrayListSize, manuCanAddPrevious, manuEpicSum2, manuLastSplit, getManuCanAddPrevious, countToGoal = 0, tempoIntMilli, canAddPrevious, tempoSecVal, canStartTempoLoop = 1, stopTempoLoop =  0, onCreateInt, tempoMilliTenthsVal, tempoMilliHundredthsVal, epicSum2, waitingVar, lastSplit, playTempoTick, stopSplitSound, courseValue, offsetInt, playFinishSound, reloadLists, oneIntCounter = 0, testValueMilli = 0, playSplitSound = 0, raceLength, splitMode = 1, canStart, testTimeValue, secInValIntMilli, secInToMilli, secInValInt, milliInValInt;
    Handler handler;
    CheckBox splitManual, splitAuto, splitAutoAnim, splitManualAnim;
    LinearLayout container;
    ArrayList arrayList, tempList;
    boolean running, listEditable, hasCalculated, hasStarted;
    ArrayAdapter adapter, tempoAdapter, tempListAdapter;
    MediaPlayer startSound, finishSound;
    private Context context;
    RelativeLayout loadingSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tt);
        handler = new Handler();
        display = (TextView) findViewById(R.id.display);
        start = (Button) findViewById(R.id.startbtn);
        pause = (Button) findViewById(R.id.pausebtn);
        reset = (Button) findViewById(R.id.resetbtn);
        back = (Button) findViewById(R.id.backHomebtn);
        add = (Button) findViewById(R.id.add);
        remove = (Button) findViewById(R.id.remove);
        goalMinutes = (EditText) findViewById(R.id.goalMinutes);
        goalSeconds = (EditText) findViewById(R.id.goalSeconds);
        splitOffset = (EditText) findViewById(R.id.offset);
        goalMilli = (EditText) findViewById(R.id.goalMilli);
        secondsInput = (EditText) findViewById(R.id.secInput);
        milliInput = (EditText) findViewById(R.id.milliInput);
        secondsInput.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        milliInput.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalMinutes.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalSeconds.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalMilli.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        splitOffset.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        course = (Spinner) findViewById(R.id.course);
        length = (Spinner) findViewById(R.id.length);
        debugOutput = (TextView) findViewById(R.id.debugOutput);
        debugOutput2 = (TextView) findViewById(R.id.debugOutput2);
        courseOutput = (TextView) findViewById(R.id.courseOutput);
        splitManual = (CheckBox) findViewById(R.id.splitManual);
        splitAuto = (CheckBox) findViewById(R.id.splitAuto);
        splitManualAnim = (CheckBox) findViewById(R.id.splitManualAnim);
        splitAutoAnim = (CheckBox) findViewById(R.id.splitAutoAnim);
        container = (LinearLayout) findViewById(R.id.container);
        ListView listView = (ListView) findViewById(R.id.splitList);
        ListView debugListView = (ListView) findViewById(R.id.debugList);
        final MediaPlayer startSound = MediaPlayer.create(this, R.raw.start);
        final MediaPlayer finishSound = MediaPlayer.create(this, R.raw.finish);
        final MediaPlayer turnSound = MediaPlayer.create(this, R.raw.turn);
        final MediaPlayer tempoTick = MediaPlayer.create(this, R.raw.tempo);
        final Spinner tempoSeconds = (Spinner) findViewById(R.id.tempoSeconds);
        final Spinner tempoMilliTenths = (Spinner) findViewById(R.id.tempoMilliTens);
        final Spinner tempoMilliHundredths = (Spinner) findViewById(R.id.tempoMilliHundreds);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        //On-create element definition
        canStart = 1;
        splitMode = 1;
        splitAuto.setChecked(false);
        splitOffset.setVisibility(View.GONE);
        splitOffset.setEnabled(false);
        secondsInput.setVisibility(View.VISIBLE);
        secondsInput.setEnabled(true);
        milliInput.setVisibility(View.VISIBLE);
        milliInput.setEnabled(true);
        add.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
        add.setEnabled(true);
        remove.setEnabled(true);
        splitAutoAnim.setEnabled(false);
        splitAutoAnim.setVisibility(View.INVISIBLE);
        splitManualAnim.setVisibility(View.INVISIBLE);
        splitManualAnim.setEnabled(false);
        listEditable = true;
        hasStarted = false;

        final Spinner course = (Spinner) findViewById(R.id.course);
        final Spinner length = (Spinner) findViewById(R.id.length);

        //race course adapter
        final ArrayAdapter<String> courseAdapt = new ArrayAdapter<>(tt.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.course));
        courseAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(courseAdapt);


        splitManual.setChecked(true);
        splitMode = 1;

        splitManualAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(splitManualAnim.isChecked()) {
                    splitMode = 1;
                    splitManualAnim.setChecked(true);
                    splitAutoAnim.setChecked(false);
                    splitOffset.setVisibility(View.GONE);
                    splitOffset.setEnabled(true);
                    secondsInput.setVisibility(View.VISIBLE);
                    secondsInput.setEnabled(true);
                    milliInput.setVisibility(View.VISIBLE);
                    milliInput.setEnabled(true);
                    add.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.VISIBLE);
                    scaleUp();
                    add.setEnabled(true);
                    remove.setEnabled(true);
                    arrayList.clear();
                    adapter.notifyDataSetChanged();

                    checkAnimDown();


                    final Handler animDown = new Handler();
                    animDown.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            splitManualAnim.clearAnimation();
                            splitAutoAnim.clearAnimation();

                            splitManualAnim.setVisibility(View.INVISIBLE);
                            splitAutoAnim.setVisibility(View.INVISIBLE);
                            splitManualAnim.setEnabled(false);
                            splitAutoAnim.setEnabled(false);

                            splitManual.setVisibility(View.VISIBLE);
                            splitAuto.setVisibility(View.VISIBLE);
                            splitManual.setEnabled(true);
                            splitAuto.setEnabled(true);

                            splitManual.setChecked(true);
                            splitAuto.setChecked(false);
                        }
                    }, 500);

                }
            }
        });

        splitAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(splitAuto.isChecked()) {
                    splitMode = 2;
                    splitAutoAnim.setChecked(true);
                    splitManualAnim.setChecked(false);
                    splitOffset.setVisibility(View.VISIBLE);
                    splitOffset.setEnabled(true);
                    secondsInput.setVisibility(View.INVISIBLE);
                    secondsInput.setEnabled(false);
                    milliInput.setVisibility(View.INVISIBLE);
                    milliInput.setEnabled(false);
                    splitOffset.setHint("50 Split Offset (sec.)");
                    scaleDown();
                    add.setVisibility(View.INVISIBLE);
                    add.setEnabled(false);
                    remove.setVisibility(View.INVISIBLE);
                    remove.setEnabled(false);
                    arrayList.clear();
                    adapter.notifyDataSetChanged();

                    checkAnimUp();

                    final Handler animUp = new Handler();
                    animUp.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            splitManual.clearAnimation();
                            splitAuto.clearAnimation();

                            splitManual.setVisibility(View.INVISIBLE);
                            splitAuto.setVisibility(View.INVISIBLE);
                            splitManual.setEnabled(false);
                            splitAuto.setEnabled(false);

                            splitManualAnim.setVisibility(View.VISIBLE);
                            splitAutoAnim.setVisibility(View.VISIBLE);
                            splitManualAnim.setEnabled(true);
                            splitAutoAnim.setEnabled(true);


                        }
                    }, 500);

                }
            }
        });

        //ArrayAdapter adapter = new ArrayAdapter()

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(course.getSelectedItem().toString().trim().equals("Course↓")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<>(tt.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.courseDefault));
                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                }
                if(course.getSelectedItem().toString().trim().equals("SCY")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<>(tt.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthY));
                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                    courseValue = 1;
                }
                if(course.getSelectedItem().toString().trim().equals("SCM")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<>(tt.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthY));
                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                    courseValue = 2;
                }
                if(course.getSelectedItem().toString().trim().equals("LCM")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<>(tt.this,
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthM));
                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                    courseValue = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //tempo adapter and information

        final ArrayAdapter<String> tempoAdapter = new ArrayAdapter<>(tt.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.count));
        tempoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempoSeconds.setAdapter(tempoAdapter);
        tempoMilliTenths.setAdapter(tempoAdapter);
        tempoMilliHundredths.setAdapter(tempoAdapter);


        tempoSeconds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(tempoSeconds.getSelectedItem().toString().trim().equals("0")) {
                    tempoSecVal = 0;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("1")) {
                    tempoSecVal = 1;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("2")) {
                    tempoSecVal = 2;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("3")) {
                    tempoSecVal = 3;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("4")) {
                    tempoSecVal = 4;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("5")) {
                    tempoSecVal = 5;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("6")) {
                    tempoSecVal = 6;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("7")) {
                    tempoSecVal = 7;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("8")) {
                    tempoSecVal = 8;
                }
                if(tempoSeconds.getSelectedItem().toString().trim().equals("9")) {
                    tempoSecVal = 9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tempoMilliTenths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("0")) {
                    tempoMilliTenthsVal = 0;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("1")) {
                    tempoMilliTenthsVal = 1;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("2")) {
                    tempoMilliTenthsVal = 2;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("3")) {
                    tempoMilliTenthsVal = 3;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("4")) {
                    tempoMilliTenthsVal = 4;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("5")) {
                    tempoMilliTenthsVal = 5;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("6")) {
                    tempoMilliTenthsVal = 6;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("7")) {
                    tempoMilliTenthsVal = 7;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("8")) {
                    tempoMilliTenthsVal = 8;
                }
                if(tempoMilliTenths.getSelectedItem().toString().trim().equals("9")) {
                    tempoMilliTenthsVal = 9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tempoMilliHundredths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("0")) {
                    tempoMilliHundredthsVal = 0;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("1")) {
                    tempoMilliHundredthsVal = 1;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("2")) {
                    tempoMilliHundredthsVal = 2;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("3")) {
                    tempoMilliHundredthsVal = 3;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("4")) {
                    tempoMilliHundredthsVal = 4;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("5")) {
                    tempoMilliHundredthsVal = 5;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("6")) {
                    tempoMilliHundredthsVal = 6;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("7")) {
                    tempoMilliHundredthsVal = 7;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("8")) {
                    tempoMilliHundredthsVal = 8;
                }
                if(tempoMilliHundredths.getSelectedItem().toString().trim().equals("9")) {
                    tempoMilliHundredthsVal = 9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //length reference
        length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = length.getSelectedItemPosition();
                    //SHARED RACES
                if(length.getSelectedItem().toString().trim().equals("50")) {
                    raceLength = 50;
                }
                if(length.getSelectedItem().toString().trim().equals("100")) {
                    raceLength = 100;
                }
                if(length.getSelectedItem().toString().trim().equals("200")) {
                    raceLength = 200;
                }
                if(length.getSelectedItem().toString().trim().equals("500")) {
                    raceLength = 500;
                }
                if(length.getSelectedItem().toString().trim().equals("1000")) {
                    raceLength = 1000;
                }
                if(length.getSelectedItem().toString().trim().equals("1650")) {
                    raceLength = 1650;
                }
                //LC SPECIFIC
                if(length.getSelectedItem().toString().trim().equals("400")) {
                    raceLength = 400;
                }
                if(length.getSelectedItem().toString().trim().equals("800")) {
                    raceLength = 800;
                }
                if(length.getSelectedItem().toString().trim().equals("1500")) {
                    raceLength = 1500;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backHome = new Intent(getApplicationContext(), mainMenu.class);

                startActivity(backHome);
            }
        });

        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(tt.this, android.R.layout.simple_list_item_1, arrayList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
                tv.setTextColor(Color.BLACK);
                //tv.setTextSize(15);
                return view;
            }
        };
        listView.setAdapter(adapter);

        tempList = new ArrayList<Integer>();
        tempListAdapter = new ArrayAdapter<Integer>(tt.this, android.R.layout.simple_list_item_1, tempList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        debugListView.setAdapter(tempListAdapter);




        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTempoLoop = 0;
                startTempoLoop();
                hasStarted = true;
                //arrayList.clear();
                running = true;
                startSound.start();
                display.setTextColor(getResources().getColor(R.color.black));

                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);

                String gMin = goalMinutes.getText().toString();
                int gMinInt = Integer.parseInt(gMin);
                int gMinToMilli = (int) (gMinInt * 60000);

                String gSec = goalSeconds.getText().toString();
                int gSecInt = Integer.parseInt(gSec);
                int gSecToMilli = (int) (gSecInt * 1000);

                String gMilli = goalMilli.getText().toString();
                int gMilliInt = Integer.parseInt(gMilli);

                milliValue = gMinToMilli + gSecToMilli + gMilliInt;

                String milliVToString = String.valueOf(milliValue);
                //debugOutput.setText(milliVToString);


                if (milliValue == 0) {
                    display.setTextColor(getResources().getColor(R.color.soft_red));
                    display.setText("Invalid goal time.");
                }
                if (splitAuto.isChecked() == false && splitManual.isChecked() == false) {
                    display.setTextColor(getResources().getColor(R.color.soft_red));
                    display.setText("Select a split mode.");
                    canStart = 0;
                }

                //Split generation
                fiftyCount = raceLength / 50;
                String splitOffsetStr = splitOffset.getText().toString();
                if (splitOffsetStr.matches("")) {
                    offsetInt = 0;
                } else if (!splitOffsetStr.matches("")) {
                    offsetInt = Integer.parseInt(splitOffsetStr);
                }
                int offsetIntMilli = (offsetInt * 1000);
                int offsetSumStore = 0, splitGenSequence = 0, offsetSumSplit = 0;


                    if (offsetInt >= 0 && splitAutoAnim.isChecked() || splitAuto.isChecked()) {
                        for (int countLoop = 1; countLoop <= fiftyCount; countLoop++) {
                            int offsetLoopGen = offsetIntMilli * countLoop;
                            offsetSumStore = offsetSumStore + offsetLoopGen;
                            if (countLoop == fiftyCount) {
                                splitGenSequence = 1;
                            }

                            if (splitGenSequence == 1) {
                                int offsetSum = milliValue - offsetSumStore;
                                offsetSumSplit = offsetSum / fiftyCount;
                                splitGenSequence = 2;
                            }

                            if (splitGenSequence == 2) {
                                for (int countLoop2 = 1; countLoop2 <= fiftyCount; countLoop2++) {
                                    int offsetIndivSplit = offsetSumSplit + (offsetIntMilli * countLoop2);
                                    String listOffsetStr = Integer.toString(offsetIndivSplit);
                                    listOffsetStr = new StringBuffer(listOffsetStr.trim()).insert(listOffsetStr.length() - 3, ".").toString();
                                    String listOffsetStrPV = listOffsetStr.substring(0, listOffsetStr.length() - 1);
                                    arrayList.add(listOffsetStrPV);
                                    adapter.notifyDataSetChanged();
                                    if (countLoop == fiftyCount) {
                                        splitGenSequence = 3;
                                    }
                                }
                            }
                        }
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
                display.setText("00:00:00");
                start.setEnabled(true);
                display.setTextColor(getResources().getColor(R.color.black));
                arrayList.clear();
                adapter.notifyDataSetChanged();
                running = false;
                listEditable = true;
                oneIntCounter = 0;
                hasStarted = false;
                counter = 0;
                onCreateInt = 0;
                stopTempoLoop = 1;
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String secInVal = secondsInput.getText().toString();
                    String millInVal = milliInput.getText().toString();
                    if(secInVal.matches("")) {
                    secInVal.contentEquals("00");
                }
                if(millInVal.matches("")) {
                    millInVal.contentEquals("00");
                }
                if(!secInVal.matches("") && !millInVal.matches("")) {
                    arrayList.add(secInVal + "." + millInVal);
                    adapter.notifyDataSetChanged();
                    arrayListSize = arrayList.size();

                    int secInValInt = Integer.parseInt(secInVal);
                    int secInValIntMilli = (int) (secInValInt * 1000);
                    int milliInValInt = Integer.parseInt(millInVal);
                    int milliInValIntMilli = (int) (milliInValInt * 10);

                    testTimeValue = secInValIntMilli + milliInValIntMilli;

                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.size() > 0) {
                    arrayList.remove(arrayList.size() - 1);
                    adapter.notifyDataSetChanged();
                }
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
                running = false;
                stopTempoLoop = 1;
            }
        });


    }

    private void playSplitSound() {
        final MediaPlayer turnSound = MediaPlayer.create(this, R.raw.turn);
        turnSound.start();
    }

    private void stopSplitSound() {
        final MediaPlayer turnSound = MediaPlayer.create(this, R.raw.turn);
        turnSound.stop();
    }

    private void playFinishSound() {
        final MediaPlayer finishSound = MediaPlayer.create(this, R.raw.finish);
        finishSound.start();
        stopSplitSound();
    }

    private void playTempoTick() {
        final MediaPlayer tempoTick = MediaPlayer.create(this, R.raw.tempo);
        tempoTick.start();
    }

    private void startTempoLoop() {
        int tempoSecValToMilli = tempoSecVal * 1000;
        int tempoMilliHundredthsValToMilli = tempoMilliHundredthsVal * 10;
        int tempoMilliTenthsValToMilli = tempoMilliTenthsVal * 100;
        tempoIntMilli = tempoSecValToMilli + tempoMilliHundredthsValToMilli + tempoMilliTenthsValToMilli;

        if(tempoIntMilli > 1) {
            Runnable test = new Runnable() {
                public void run() {
                    playTempoTick();
                }
            };
            final ScheduledExecutorService tempoLoop = Executors
                    .newSingleThreadScheduledExecutor();
            tempoLoop.scheduleAtFixedRate(test, 3000, tempoIntMilli, TimeUnit.MILLISECONDS);


        Runnable stopLoop = new Runnable() {
            public void run() {
                if(stopTempoLoop == 1) {
                    tempoLoop.shutdown();
                    tempoIntMilli = 0;
                    stopTempoLoop = 0;
                }
            }
        };
        final ScheduledExecutorService stopLoopES = Executors
                .newSingleThreadScheduledExecutor();
        tempoLoop.scheduleAtFixedRate(stopLoop, 0, 100, TimeUnit.MILLISECONDS);
        }
    }


    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }



    public Runnable runnable = new Runnable() {
        public void run() {
            if (milliValue > 0 && canStart == 1) {
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

                if (updateTime >= milliValue) {
                    start.setEnabled(false);
                    updateTime = milliValue;
                    pause.setEnabled(false);
                    timeBuff += milli;
                    handler.removeCallbacks(runnable);
                    reset.setEnabled(true);
                    stopTempoLoop = 1;
                    playFinishSound();
                    lastSplit = 0;
                    epicSum = 0;
                    epicSum2 = 0;
                    stopSplitSound = 1;
                }

                    if (counter < fiftyCount) { //for performing only the first split generation
                        if (counter == 0) {

                            //before 0
                            String waitingVarPre = arrayList.get(counter).toString();
                            //String waitingVarPre = Integer.toString(arrayListSize);
                            StringBuilder sbWaitingVarPre = new StringBuilder(waitingVarPre).deleteCharAt(waitingVarPre.length() - 3);
                            String waitingVarPreStr = sbWaitingVarPre.toString();

                            waitingVar = Integer.parseInt(waitingVarPreStr) * 10;

                            if ((updateTime > waitingVar - 10 && updateTime < waitingVar + 10)) { //for 0 only
                                counter = 1;
                                lastSplit = waitingVar;
                                waitingVar = 0;
                                //playSplitSound = 1;
                                if(counter != fiftyCount) {
                                    playSplitSound();
                                }
                            }

                        }

                        //counter == 1
                        if (counter == 1) { //for performing first + next after that indexes 0+1
                            //before 0
                            String waitingVarPre = arrayList.get(0).toString();
                            StringBuilder sbWaitingVarPre = new StringBuilder(waitingVarPre).deleteCharAt(waitingVarPre.length() - 3);
                            String waitingVarPreStr = sbWaitingVarPre.toString();
                            waitingVar = Integer.parseInt(waitingVarPreStr) * 10;

                            String waitingVarPre2 = arrayList.get(1).toString();
                            StringBuilder sbWaitingVarPre2 = new StringBuilder(waitingVarPre2).deleteCharAt(waitingVarPre2.length() - 3);
                            String waitingVarPreStr2 = sbWaitingVarPre2.toString();
                            int firstWaitingVal = Integer.parseInt(waitingVarPreStr2) * 10;

                            int epicSum = waitingVar + firstWaitingVal;


                            if ((updateTime > epicSum - 10 && updateTime < epicSum + 10)) { //for 0 only
                                counter = 2;
                                lastSplit = epicSum;
                                epicSum = 0;
                                playSplitSound = 1;
                                if(counter != fiftyCount) {
                                    playSplitSound();
                                }
                            }

                        }

                        //everything after 1 but is less than fiftyCount -1
                        if (counter > 1 && counter <= fiftyCount - 1) {
                            //before 0
                            canAddPrevious = 0;
                            String waitingVarPre = arrayList.get(counter - 1).toString();
                            StringBuilder sbWaitingVarPre = new StringBuilder(waitingVarPre).deleteCharAt(waitingVarPre.length() - 3);
                            String waitingVarPreStr = sbWaitingVarPre.toString();
                            int firstWaitingVal = Integer.parseInt(waitingVarPreStr) * 10;

                            String multiWaitValPre = arrayList.get(counter).toString();
                            StringBuilder sbMultiWaitValPre = new StringBuilder(multiWaitValPre).deleteCharAt(multiWaitValPre.length() - 3);
                            String multiWaitValStr = sbMultiWaitValPre.toString();
                            int secondWaitVal = Integer.parseInt(multiWaitValStr) * 10;
                            canAddPrevious = 1;


                            //prevents spam
                            if (canAddPrevious == 1) {
                                epicSum2 = lastSplit + secondWaitVal;
                                canAddPrevious = 0;
                            }

                            if ((updateTime > epicSum2 - 10 && updateTime < epicSum2 + 10)) { //for 0 only
                                lastSplit = epicSum2;
                                counter = counter + 1;
                                playSplitSound = 1;
                                if(counter != fiftyCount) {
                                    playSplitSound();
                                }
                            }

                        }
                        //debugOutput2.setText(Integer.toString());
                        debugOutput2.setText(Integer.toString(lastSplit) + " " + Integer.toString(epicSum2) + " " + Integer.toString(counter) + " " + Integer.toString(fiftyCount));
                    }
                }

                //String superEpic = arrayList.get(0).toString();
                courseOutput.setText(Integer.toString(arrayList.size()) + " arrayList size ");

            }

    };

    public void onBackPressed() {
        Intent home = new Intent(this,mainMenu.class);
        startActivity(home);
    }

    private void animUpCheckboxes() {
        splitManualAnim.setVisibility(View.VISIBLE);
        splitAutoAnim.setVisibility(View.VISIBLE);
        splitManualAnim.setEnabled(true);
        splitAutoAnim.setEnabled(true);
    }

    private void animDownCheckboxes() {
        splitManual.setVisibility(View.VISIBLE);
        splitAuto.setVisibility(View.VISIBLE);
        splitManual.setEnabled(true);
        splitAuto.setEnabled(true);
    }


    private void checkAnimDown() {
        Animation checkboxAnimation = AnimationUtils.loadAnimation(this, R.anim.checkbox_down);
        splitManualAnim.startAnimation(checkboxAnimation);
        splitAutoAnim.startAnimation(checkboxAnimation);
    }
    private void checkAnimUp() {
        Animation checkboxAnimation = AnimationUtils.loadAnimation(this, R.anim.checkbox_up);
        splitManual.startAnimation(checkboxAnimation);
        splitAuto.startAnimation(checkboxAnimation);
    }
    private void scaleUp() {
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        add.startAnimation(scaleUp);
        remove.startAnimation(scaleUp);
    }
    private void scaleDown() {
        Animation scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        add.startAnimation(scaleDown);
        remove.startAnimation(scaleDown);
    }
}