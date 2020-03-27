package com.bdatsko.tempotimer.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.os.Handler;
import android.os.SystemClock;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bdatsko.tempotimer.R;

public class tt extends Fragment {




    TextView display, debugOutput, debugOutput2, courseOutput, gColon, gDot, minMarker, secMarker, milliMarker;
    Button start,pause,reset, back, add, remove;
    int prevsum, testforsum, soundMode, coolInt = 0;
    EditText goalMinutes, goalSeconds, goalMilli, splitOffset, splitIn, tempo, secondsInput, milliInput, tempoSeconds, tempoMilliTenths, tempoMilliHundredths;
    public Spinner course, length;
    long milli, startTime, timeBuff, updateTime = 0L, pauseOffset;
    int milliValue, seconds, minutes, milliseconds, fiftyCount, twentyfiveCount = 0, counter = 0, epicSum = 0, arrayListSize, manuCanAddPrevious, manuEpicSum2, manuLastSplit, getManuCanAddPrevious, countToGoal = 0, tempoIntMilli, canAddPrevious, tempoSecVal, canStartTempoLoop = 1, stopTempoLoop =  0, onCreateInt, tempoMilliTenthsVal, tempoMilliHundredthsVal, epicSum2, waitingVar, lastSplit, playTempoTick, stopSplitSound, courseValue, offsetInt, playFinishSound, reloadLists, oneIntCounter = 0, testValueMilli = 0, playSplitSound = 0, raceLength, splitMode = 1, canStart, testTimeValue, secInValIntMilli, secInToMilli, secInValInt, milliInValInt;
    Handler handler;
    CheckBox splitManual, splitAuto, splitAutoAnim, splitManualAnim;
    LinearLayout container;
    ArrayList arrayList, tempList;
    boolean running, listEditable, hasCalculated, hasStarted;
    ArrayAdapter adapter, tempoAdapter, tempListAdapter;
    MediaPlayer startSound, finishSound;
    private Context context;
    RelativeLayout loadingSplash;
    ConstraintLayout tt_new;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;
    private EditText editText;
    TextView txtData;




    public void startTempoLoop() {

        if (tempoSeconds.getText().toString().matches("")) {
            tempoSecVal = 0;
        } else if (!tempoSeconds.getText().toString().matches("")) {
            tempoSecVal = Integer.parseInt(tempoSeconds.getText().toString());
        }

        //TEMPO MILLI-.1
        if (tempoMilliTenths.getText().toString().matches("")) {
            tempoMilliTenthsVal = 0;
        } else if (!tempoSeconds.getText().toString().matches("")) {
            tempoMilliTenthsVal = Integer.parseInt(tempoMilliTenths.getText().toString());
        }

        //TEMPO MILLI-.01
        if (tempoMilliHundredths.getText().toString().matches("")) {
            tempoMilliHundredthsVal = 0;
        } else if (!tempoSeconds.getText().toString().matches("")) {
            tempoMilliHundredthsVal = Integer.parseInt(tempoMilliHundredths.getText().toString());
        }

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


    TextView Txt;
    EditText Msg;
    Button bn;




    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflate the layout of this fragment
        final View v = inflater.inflate(R.layout.ttl, container, false);





        handler = new Handler();
        debugOutput = v.findViewById(R.id.debugOutput2);
        display = (TextView) v.findViewById(R.id.display);
        start = (Button) v.findViewById(R.id.startbtn);
        pause = (Button) v.findViewById(R.id.pausebtn);
        reset = (Button) v.findViewById(R.id.resetbtn);
        add = (Button) v.findViewById(R.id.add);
        remove = (Button) v.findViewById(R.id.remove);
        goalMinutes = (EditText) v.findViewById(R.id.goalMinutes);
        goalSeconds = (EditText) v.findViewById(R.id.goalSeconds);
        splitOffset = (EditText) v.findViewById(R.id.offset);
        goalMilli = (EditText) v.findViewById(R.id.goalMilli);
        secondsInput = (EditText) v.findViewById(R.id.secInput);
        milliInput = (EditText) v.findViewById(R.id.milliInput);
        course = (Spinner) v.findViewById(R.id.course);
        length = (Spinner) v.findViewById(R.id.length);
        splitManual = (CheckBox) v.findViewById(R.id.splitManual);
        splitAuto = (CheckBox) v.findViewById(R.id.splitAuto);
        container = (LinearLayout) v.findViewById(R.id.container);
        ListView listView = (ListView) v.findViewById(R.id.splitList);
        final MediaPlayer startSound = MediaPlayer.create(getContext(), R.raw.start);
        final MediaPlayer finishSound = MediaPlayer.create(getContext(), R.raw.finish);
        final MediaPlayer turnSound = MediaPlayer.create(getContext(), R.raw.turn);
        final MediaPlayer tempoTick = MediaPlayer.create(getContext(), R.raw.tempo);
        tempoSeconds = (EditText) v.findViewById(R.id.tempoSeconds);
        tempoMilliTenths = (EditText) v.findViewById(R.id.tempoMilliTens);
        tempoMilliHundredths = (EditText) v.findViewById(R.id.tempoMilliHundreds);
        secondsInput.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        milliInput.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalMinutes.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalSeconds.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        goalMilli.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        splitOffset.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        tempoSeconds.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        tempoMilliTenths.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        tempoMilliHundredths.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        gColon = (TextView) v.findViewById(R.id.goal_colon2);
        gDot = (TextView) v.findViewById(R.id.goal_dot);
        final ImageView checked1 = v.findViewById(R.id.checked1);
        final ImageView checked2 = v.findViewById(R.id.checked2);
        final ImageView checked1u = v.findViewById(R.id.checked1u);
        final ImageView checked2u = v.findViewById(R.id.checked2u);
        minMarker = (TextView) v.findViewById(R.id.minmarker);
        secMarker = (TextView) v.findViewById(R.id.secmarker);
        milliMarker = (TextView) v.findViewById(R.id.millimarker);
        //editText = v.findViewById(R.id.edit_text);



        //On-create element definition
        canStart = 1;
        splitMode = 1;

        checked1.setVisibility(View.INVISIBLE);
        checked1u.setVisibility(View.INVISIBLE);
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
        //splitAutoAnim.setEnabled(false);
        //splitAutoAnim.setVisibility(View.INVISIBLE);
        //splitManualAnim.setVisibility(View.INVISIBLE);
        //splitManualAnim.setEnabled(false);
        listEditable = true;
        hasStarted = false;

        final Spinner course = (Spinner) v.findViewById(R.id.course);
        final Spinner length = (Spinner) v.findViewById(R.id.length);

        goalMinutes.setVisibility(View.VISIBLE);
        goalMilli.setVisibility(View.VISIBLE);
        goalSeconds.setVisibility(View.VISIBLE);
        display.setVisibility(View.INVISIBLE);
        gColon.setVisibility(View.VISIBLE);
        gDot.setVisibility(View.VISIBLE);



        //race course adapter



        /*
        final ArrayAdapter<String> courseAdapt = new ArrayAdapter<>(tt.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.course));
        courseAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(courseAdapt);
        */


//race course adapter
        ArrayAdapter<String> courseAdapt = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.course)) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.BLACK);
                v.setBackgroundColor(getResources().getColor(R.color.white));

                return v;
            }
        };


        courseAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(courseAdapt);


        final AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable animatedVectorDrawable2 = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable animatedVectorDrawableu = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);
        final AnimatedVectorDrawable animatedVectorDrawable2u = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);

        checked1.setImageDrawable(animatedVectorDrawable);
        checked2.setImageDrawable(animatedVectorDrawable2);

        checked1u.setImageDrawable(animatedVectorDrawableu);
        checked2u.setImageDrawable(animatedVectorDrawable2u);

        checked1.setSelected(false);

        splitManual.setChecked(true);
        splitMode = 1;

        /*
        checked1.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                 if(checked1.isSelected()) {
                     checked1.setSelected(false);
                     checked1.setImageDrawable(animatedVectorDrawable);

                 }
             }
        });
        */


        splitManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splitAuto.setChecked(false);
                splitManual.setEnabled(false);
                splitAuto.setEnabled(true);
                if (splitManual.isChecked() && !splitAuto.isChecked()) {
                    checked2.setVisibility(View.VISIBLE);

                    Drawable drawable2 = animatedVectorDrawable2.getCurrent();

                    if (drawable2 instanceof Animatable) {
                        ((Animatable) drawable2).start();
                    }


                    checked1.setVisibility(View.GONE);
                    checked1u.setVisibility(View.VISIBLE);
                    Drawable drawableu = animatedVectorDrawableu.getCurrent();

                    if (drawableu instanceof Animatable) {
                        ((Animatable) drawableu).start();
                    }

                    if (splitManual.isChecked()) {
                        splitMode = 1;
                        splitAuto.setChecked(false);
                        secondsInput.setVisibility(View.VISIBLE);
                        milliInput.setVisibility(View.VISIBLE);
                        add.setVisibility(View.VISIBLE);
                        remove.setVisibility(View.VISIBLE);
                        splitOffset.setVisibility(View.GONE);
                        splitOffset.setEnabled(false);
                        //tt_new.setBackgroundResource(R.drawable.bg_tt_manual);

                    }
                }
            }
        });


        splitAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                splitManual.setChecked(false);
                splitManual.setEnabled(true);
                splitAuto.setEnabled(false);
                if (splitAuto.isChecked() && !splitManual.isChecked()) {

                    checked1.setVisibility(View.VISIBLE);
                    Drawable drawable = animatedVectorDrawable.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }

                    checked2.setVisibility(View.GONE);


                    Drawable drawable2u = animatedVectorDrawable2u.getCurrent();

                    if (drawable2u instanceof Animatable) {
                        ((Animatable) drawable2u).start();
                    }

                    if (splitAuto.isChecked()) {
                        splitMode = 2;
                        splitManual.setChecked(false);
                        splitOffset.setVisibility(View.VISIBLE);
                        secondsInput.setVisibility(View.GONE);
                        milliInput.setVisibility(View.GONE);
                        add.setVisibility(View.GONE);
                        remove.setVisibility(View.GONE);
                        splitOffset.setEnabled(true);
                        //tt_new.setBackgroundResource(R.drawable.bg_tt_auto);


                    }
                }
            }
        });


        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (course.getSelectedItem().toString().trim().equals("Course↓")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.courseDefault)) {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTextColor(Color.BLACK);
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            v.setBackgroundColor(getResources().getColor(R.color.white));

                            return v;
                        }
                    };

                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                }
                if (course.getSelectedItem().toString().trim().equals("SCY")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthY)) {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            v.setBackgroundColor(getResources().getColor(R.color.white));

                            return v;
                        }
                    };
                    raceAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    length.setAdapter(raceAdapt);
                    courseValue = 1;
                }
                if (course.getSelectedItem().toString().trim().equals("SCM")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthY)) {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            v.setBackgroundColor(getResources().getColor(R.color.white));

                            return v;
                        }
                    };
                    length.setAdapter(raceAdapt);
                    courseValue = 2;
                }
                if (course.getSelectedItem().toString().trim().equals("LCM")) {
                    ArrayAdapter<String> raceAdapt = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.raceLengthM)) {

                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            ((TextView) v).setGravity(Gravity.CENTER);

                            return v;
                        }

                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            View v = super.getDropDownView(position, convertView, parent);

                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            ((TextView) v).setTypeface(externalFont);
                            ((TextView) v).setTextColor(Color.BLACK);
                            v.setBackgroundColor(getResources().getColor(R.color.white));

                            return v;
                        }
                    };
                    length.setAdapter(raceAdapt);
                    courseValue = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //tempo adapter and information


        ArrayAdapter<String> tempoAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.count)) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.BLACK);
                ((TextView) v).setGravity(Gravity.CENTER);

                return v;
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.BLACK);
                v.setBackgroundColor(getResources().getColor(R.color.white));

                return v;
            }
        };

        //final ArrayAdapter<String> tempoAdapter = new ArrayAdapter<>(tt.this,
        //        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.count));


        //Assign assign values to variables; come from tempo Edit Texts; CONVERTED FROM SPINNER DURING 1.0 TO 1.1 UPDATE!



        //TEMPO SECONDS



                    //length reference
                    length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            int index = length.getSelectedItemPosition();
                            //SHARED RACES
                            if (length.getSelectedItem().toString().trim().equals("50")) {
                                raceLength = 50;
                            }
                            if (length.getSelectedItem().toString().trim().equals("100")) {
                                raceLength = 100;
                            }
                            if (length.getSelectedItem().toString().trim().equals("200")) {
                                raceLength = 200;
                            }
                            if (length.getSelectedItem().toString().trim().equals("500")) {
                                raceLength = 500;
                            }
                            if (length.getSelectedItem().toString().trim().equals("1000")) {
                                raceLength = 1000;
                            }
                            if (length.getSelectedItem().toString().trim().equals("1650")) {
                                raceLength = 1650;
                            }
                            //LC SPECIFIC
                            if (length.getSelectedItem().toString().trim().equals("400")) {
                                raceLength = 400;
                            }
                            if (length.getSelectedItem().toString().trim().equals("800")) {
                                raceLength = 800;
                            }
                            if (length.getSelectedItem().toString().trim().equals("1500")) {
                                raceLength = 1500;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    listView.setOverScrollMode(View.OVER_SCROLL_NEVER);

                    arrayList = new ArrayList<String>();

                    adapter = new ArrayAdapter<String>(getContext(), R.layout.splitlist, arrayList) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            tv.setTextColor(Color.BLACK);
                            //tv.setTextSize(15);
                            return view;
                        }
                    };
                    listView.setAdapter(adapter);

                    tempList = new ArrayList<Integer>();
                    tempListAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, tempList) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);
                            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            Typeface externalFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");
                            tv.setTextColor(Color.BLACK);
                            return view;
                        }
                    };


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String secInVal = secondsInput.getText().toString();
                String millInVal = milliInput.getText().toString();
                if (secInVal.matches("")) {
                    secInVal.contentEquals("00");
                }
                if (millInVal.matches("")) {
                    millInVal.contentEquals("00");
                }
                if (!secInVal.matches("") && !millInVal.matches("")) {
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
                if (arrayList.size() > 0) {
                    arrayList.remove(arrayList.size() - 1);
                    adapter.notifyDataSetChanged();
                }
            }
        });

            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (milliValue > 0 && canStart == 1) {

                        pause.setEnabled(true);
                        reset.setEnabled(true);
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
                            handler.removeCallbacks(this);
                            reset.setEnabled(true);
                            stopTempoLoop = 1;
                            playFinishSound();
                            lastSplit = 0;
                            epicSum = 0;
                            epicSum2 = 0;
                            stopSplitSound = 1;
                        }

                        if(goalMinutes.getVisibility() == View.VISIBLE && updateTime > 0) {


                            pause.performClick();

                            try {
                                //set time in mili
                                Thread.sleep(100);

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            reset.performClick();
                        }


                        if (((offsetInt >= 0 && splitAuto.isChecked()) && course.getSelectedItem().toString().trim().equals("LCM"))
                                || ((splitManual.isChecked()) && course.getSelectedItem().toString().trim().equals("LCM"))) {
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
                                        if (counter != fiftyCount) {
                                            playSplitSound();
                                        }
                                    }

                                }

                                //counter == 1
                                if (counter == 1) { //for performing first + next after that indexes 0+1
                                    //before 0
                                    String waitingVarPre = arrayList.get(0).toString().replace(".", "");
                                    //StringBuilder sbWaitingVarPre = new StringBuilder(waitingVarPre).deleteCharAt(waitingVarPre.length() - 3);
                                    //String waitingVarPreStr = sbWaitingVarPre.toString();
                                    waitingVar = Integer.parseInt(waitingVarPre) * 10;

                                    String waitingVarPre2 = arrayList.get(1).toString().replace(".", "");
                                    //StringBuilder sbWaitingVarPre2 = new StringBuilder(waitingVarPre2).deleteCharAt(waitingVarPre2.length() - 3);
                                    //String waitingVarPreStr2 = sbWaitingVarPre2.toString();
                                    int firstWaitingVal = Integer.parseInt(waitingVarPre2) * 10;

                                    int epicSum = waitingVar + firstWaitingVal;


                                    if ((updateTime > epicSum - 10 && updateTime < epicSum + 10)) { //for 0 only
                                        counter = 2;
                                        lastSplit = epicSum;
                                        epicSum = 0;
                                        playSplitSound = 1;
                                        if (counter != fiftyCount) {
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
                                        if (counter != fiftyCount) {
                                            playSplitSound();
                                        }
                                    }

                                }
                                //debugOutput2.setText(Integer.toString());
                                //debugOutput2.setText(Integer.toString(lastSplit) + " " + Integer.toString(epicSum2) + " " + Integer.toString(counter) + " " + Integer.toString(fiftyCount));
                            }

                            //debugOutput.setText("counter: " + counter);
                        } //end conditional 1


                        //25

                        if (((offsetInt >= 0 && splitAuto.isChecked()) && (course.getSelectedItem().toString().trim().equals("SCY") || course.getSelectedItem().toString().trim().equals("SCM")))
                                || ((splitManual.isChecked()) && course.getSelectedItem().toString().trim().equals("SCY") || course.getSelectedItem().toString().trim().equals("SCM"))) {

                            if (counter < twentyfiveCount) { //for performing only the first split generation
                                if (counter == 0 && updateTime == 0) {

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
                                        if (counter != twentyfiveCount) {
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
                                        if (counter != twentyfiveCount) {
                                            playSplitSound();
                                        }
                                    }

                                }

                                //everything after 1 but is less than twentyfiveCount -1
                                if (counter > 1 && counter <= twentyfiveCount - 1) {
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
                                        if (counter != twentyfiveCount) {
                                            playSplitSound();
                                        }
                                    }

                                }
                            } //end conditional 2



                            debugOutput.setText(Long.toString(updateTime));







                                    /*
                                    if(counter == 0) {
                                        if((updateTime > ((Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10) - 10) && updateTime < ((Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10)) + 10)) {
                                            playSplitSound();
                                            prevsum = (Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10);
                                            counter++;
                                            testforsum = prevsum + (Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10);
                                        }
                                        }

                                        if(counter >= 1) {
                                            if(updateTime > ( testforsum - 10) && updateTime < (testforsum + 10)) {
                                                playSplitSound();
                                                testforsum = prevsum + (Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10);
                                                counter++;
                                                prevsum = (Integer.parseInt(arrayList.get(counter).toString().replace(".", "")) * 10);

                                            }


                                        }
                                    debugOutput.setText(updateTime + " " + prevsum + " " + testforsum + " " + counter );
                                    */

                        }

                    }
                }

            };
            runnable.run();






                    reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            display.setText("00:00.00");
                            milli = 0L;
                            startTime = 0;
                            timeBuff = 0;
                            updateTime = 0;
                            seconds = 0;
                            minutes = 0;
                            milliseconds = 0;
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

                            goalMinutes.setVisibility(View.VISIBLE);
                            goalMilli.setVisibility(View.VISIBLE);
                            goalSeconds.setVisibility(View.VISIBLE);
                            display.setVisibility(View.INVISIBLE);
                            gColon.setVisibility(View.VISIBLE);
                            gDot.setVisibility(View.VISIBLE);

                            minMarker.setVisibility(View.VISIBLE);
                            secMarker.setVisibility(View.VISIBLE);
                            milliMarker.setVisibility(View.VISIBLE);


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


        //THIS IS THE COOL STUFF THAT MAKES EVERYTHING WORK!!!!!!! ACTIVITY TO FRAGMENT.
        if(updateTime == 0) {
            String setTo;
            Msg = (EditText) getActivity().findViewById(R.id.funnyEdit);
            Txt = (TextView) v.findViewById(R.id.txtData);
            bn = (Button) v.findViewById(R.id.add);


            String MSG = Msg.getText().toString();
            Txt.setText(MSG);
            //END COOL STUFF
        }



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reset.setEnabled(false);


                Typeface prod_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/productnormal.ttf");


                display.setTextColor(getResources().getColor(R.color.starting));
                display.setTypeface(prod_bold);
                display.setText("Starting...");

                goalMinutes.setVisibility(View.INVISIBLE);
                goalMilli.setVisibility(View.INVISIBLE);
                goalSeconds.setVisibility(View.INVISIBLE);
                minMarker.setVisibility(View.INVISIBLE);
                secMarker.setVisibility(View.INVISIBLE);
                milliMarker.setVisibility(View.INVISIBLE);
                gColon.setVisibility(View.INVISIBLE);
                gDot.setVisibility(View.INVISIBLE);
                display.setVisibility(View.VISIBLE);

                if(updateTime==0) {
                    //startSound.start();
                    if(Msg.getText().toString().matches("1") || Msg.getText().toString().matches("")) {
                        display.setText("Starting...");
                        playColoradoStart();
                    }

                    if(Msg.getText().toString().matches("2")) {
                        display.setText("Starting...");
                        playDaktronicsStart();
                    }

                    if(Msg.getText().toString().matches("3")) {
                        display.setText("Starting...");
                        playOmegaStart();
                    }


                }


                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {



                        Typeface normal = Typeface.createFromAsset(getActivity().getAssets(),
                                "fonts/freesansbold.ttf");

                        display.setTypeface(normal);



                        if (tempoSeconds.getText().toString().matches("")) {
                            tempoSeconds.setText("0");
                        } else if (!tempoSeconds.getText().toString().matches("")) {
                            tempoSecVal = Integer.parseInt(tempoSeconds.getText().toString());
                        }

                        //TEMPO MILLI-.1
                        if (tempoMilliTenths.getText().toString().matches("")) {
                            tempoMilliTenths.setText("0");
                        } else if (!tempoSeconds.getText().toString().matches("")) {
                            tempoMilliTenthsVal = Integer.parseInt(tempoMilliTenths.getText().toString());
                        }

                        //TEMPO MILLI-.01
                        if (tempoMilliHundredths.getText().toString().matches("")) {
                            tempoMilliHundredths.setText("0");
                        } else if (!tempoSeconds.getText().toString().matches("")) {
                            tempoMilliHundredthsVal = Integer.parseInt(tempoMilliHundredths.getText().toString());
                        }

                        stopTempoLoop = 0;
                        startTempoLoop();
                        hasStarted = true;
                        display.setTextSize(45);
                        //arrayList.clear();
                        running = true;

                        display.setTextColor(getResources().getColor(R.color.black));

                        startTime = SystemClock.uptimeMillis();
                        handler.postDelayed(runnable, 0);
                        reset.setEnabled(false);
                        pause.setEnabled(true);

                        if (goalMinutes.getText().toString().length() > 0 && goalSeconds.getText().toString().length() > 0 && goalMilli.getText().toString().length() > 0) {


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

                        }

                        if (milliValue == 0) {
                            display.setTextColor(getResources().getColor(R.color.soft_red));
                            display.setText("Invalid goal time.");
                            display.setTextSize(32);
                            reset.setEnabled(true);

                        }
                        if (splitAuto.isChecked() == false && splitManual.isChecked() == false) {
                            display.setTextColor(getResources().getColor(R.color.soft_red));
                            display.setText("Select a split mode.");
                            display.setTextSize(32);
                            canStart = 0;
                            reset.setEnabled(true);

                        }

                        //Split generation
                        fiftyCount = raceLength / 50;
                        twentyfiveCount = raceLength / 25;
                        String splitOffsetStr = splitOffset.getText().toString();
                        if (splitOffsetStr.matches("")) {
                            offsetInt = 0;
                        } else if (!splitOffsetStr.matches("")) {
                            offsetInt = Integer.parseInt(splitOffsetStr);
                        }
                        int offsetIntMilli = (offsetInt * 1000);
                        int offsetSumStore = 0, splitGenSequence = 0, offsetSumSplit = 0;


                        //25s
                        if (offsetInt >= 0 && splitAuto.isChecked() && course.getSelectedItem().toString().trim().equals("SCY") || course.getSelectedItem().toString().trim().equals("SCM")) {
                            for (int countLoop = 1; countLoop <= twentyfiveCount; countLoop++) {
                                int offsetLoopGen = offsetIntMilli * countLoop;
                                offsetSumStore = offsetSumStore + offsetLoopGen;
                                if (countLoop == twentyfiveCount) {
                                    splitGenSequence = 1;
                                }

                                if (splitGenSequence == 1) {
                                    int offsetSum = milliValue - offsetSumStore;
                                    offsetSumSplit = offsetSum / twentyfiveCount;
                                    splitGenSequence = 2;
                                }

                                if (splitGenSequence == 2) {
                                    for (int countLoop2 = 1; countLoop2 <= twentyfiveCount; countLoop2++) {
                                        int offsetIndivSplit = offsetSumSplit + (offsetIntMilli * countLoop2);
                                        String listOffsetStr = Integer.toString(offsetIndivSplit);
                                        listOffsetStr = new StringBuffer(listOffsetStr.trim()).insert(listOffsetStr.length() - 3, ".").toString();
                                        String listOffsetStrPV = listOffsetStr.substring(0, listOffsetStr.length() - 1);
                                        arrayList.add(listOffsetStrPV);
                                        adapter.notifyDataSetChanged();
                                        if (countLoop == twentyfiveCount) {
                                            splitGenSequence = 3;
                                        }
                                    }
                                }
                            }
                        }

                        //50s
                        if (offsetInt >= 0 && splitAuto.isChecked() && course.getSelectedItem().toString().trim().equals("LCM")) {
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

                }, 10307);


            }

        });


        return v;

    }





    private void playSplitSound() {
            final MediaPlayer turnSound = MediaPlayer.create(getContext(), R.raw.turn);
            turnSound.start();
        }

        private void stopSplitSound() {
            final MediaPlayer turnSound = MediaPlayer.create(getContext(), R.raw.turn);
            turnSound.stop();
        }

        private void playFinishSound() {
            final MediaPlayer finishSound = MediaPlayer.create(getContext(), R.raw.finish);
            finishSound.start();
            stopSplitSound();
        }

        private void playTempoTick() {
            final MediaPlayer tempoTick = MediaPlayer.create(getContext(), R.raw.tempo);
            tempoTick.start();
        }

    private void playColoradoStart() {
        final MediaPlayer coloradoStarter = MediaPlayer.create(getContext(), R.raw.coloradotart);
        coloradoStarter.start();
    }

    private void playDaktronicsStart() {
        final MediaPlayer daktronicsStarter = MediaPlayer.create(getContext(), R.raw.daktronicsstarter);
        daktronicsStarter.start();
    }

    private void playOmegaStart() {
        final MediaPlayer omegaStarter = MediaPlayer.create(getContext(), R.raw.omegastarter);
        omegaStarter.start();
    }




        private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
            @Override
            public CharSequence getTransformation(CharSequence source, View view) {
                return source;
            }
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
            Animation checkboxAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.checkbox_down);
            splitManualAnim.startAnimation(checkboxAnimation);
            splitAutoAnim.startAnimation(checkboxAnimation);
        }
        private void checkAnimUp() {
            Animation checkboxAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.checkbox_up);
            splitManual.startAnimation(checkboxAnimation);
            splitAuto.startAnimation(checkboxAnimation);
        }
        private void scaleUp() {
            Animation scaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
            add.startAnimation(scaleUp);
            remove.startAnimation(scaleUp);
        }
        private void scaleDown() {
            Animation scaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);
            add.startAnimation(scaleDown);
            remove.startAnimation(scaleDown);
        }
}






