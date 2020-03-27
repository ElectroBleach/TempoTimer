package com.bdatsko.tempotimer.ui.main;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bdatsko.tempotimer.R;

import org.w3c.dom.Text;

public class settings extends Fragment {
    SendMessage SM;
    TextView debugOutput;
    EditText inData;
    int soundMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.setl, container, false);


//Starter checkbox definition\
        final CheckBox coloradoChecked = (CheckBox) rootView.findViewById(R.id.colorado);
        final CheckBox daktronicsChecked = (CheckBox) rootView.findViewById(R.id.daktronics);
        final CheckBox omegaChecked = (CheckBox) rootView.findViewById(R.id.omega);

        final ImageView coloradoRender = (ImageView) rootView.findViewById(R.id.soundchecked1);
        final ImageView daktronicsRender = (ImageView) rootView.findViewById(R.id.soundchecked2);
        final ImageView omegaRender = (ImageView) rootView.findViewById(R.id.soundchecked3);
        final ImageView coloradoRenderu = (ImageView) rootView.findViewById(R.id.soundchecked1u);
        ImageView daktronicsRenderu = (ImageView) rootView.findViewById(R.id.soundchecked2u);
        ImageView omegaRenderu = (ImageView) rootView.findViewById(R.id.soundchecked3u);
        final TextView debugOutput = (TextView) rootView.findViewById(R.id.debugOutput);

        //Theme checkbox definition
        final CheckBox lightChecked = (CheckBox) rootView.findViewById(R.id.light);
        final CheckBox darkChecked = (CheckBox) rootView.findViewById(R.id.dark);

        final ImageView lightRender = (ImageView) rootView.findViewById(R.id.themechecked1);
        final ImageView darkRender  = (ImageView) rootView.findViewById(R.id.themechecked2);
        final ImageView lightRenderu = (ImageView) rootView.findViewById(R.id.themechecked1u);
        final ImageView darkRenderu = (ImageView) rootView.findViewById(R.id.themechecked2u);







        //Starter checkbox drawable and animation definition
        final AnimatedVectorDrawable drawableFillC = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable drawableUnfillC = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);
        final AnimatedVectorDrawable drawableFillD = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable drawableUnfillD = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);
        final AnimatedVectorDrawable drawableFillO = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable drawableUnfillO = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);

        coloradoRender.setImageDrawable(drawableFillC);
        coloradoRenderu.setImageDrawable(drawableUnfillC);
        daktronicsRender.setImageDrawable(drawableFillD);
        daktronicsRenderu.setImageDrawable(drawableUnfillD);
        omegaRender.setImageDrawable(drawableFillO);
        omegaRenderu.setImageDrawable(drawableUnfillO);


        //Theme checkbox drawable and animation definition
        final AnimatedVectorDrawable drawableFillL = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable drawableUnfillL = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);
        final AnimatedVectorDrawable drawableFillDa = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxfill);
        final AnimatedVectorDrawable drawableUnfillDa = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_boxunfill);


        lightRender.setImageDrawable(drawableFillL);
        lightRenderu.setImageDrawable(drawableUnfillL);
        darkRender.setImageDrawable(drawableFillDa);
        darkRenderu.setImageDrawable(drawableUnfillDa);

        //setup
        //Starter Checkbox
        coloradoChecked.setChecked(true);
        daktronicsChecked.setChecked(false);
        omegaChecked.setChecked(false);


        //Uncheck checkboxes 2 and 3
            daktronicsRender.setVisibility(View.GONE);
            Drawable drawable = drawableUnfillD.getCurrent();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }


            omegaRender.setVisibility(View.GONE);
            Drawable drawable45 = drawableUnfillO.getCurrent();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable45).start();
            }


        //Theme Checkbox
        lightChecked.setChecked(true);
        darkChecked.setChecked(false);

        if(darkChecked.isChecked() == false) {
            darkRender.setVisibility(View.GONE);
            Drawable drawable789 = drawableUnfillDa.getCurrent();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable789).start();
            }
        }



        //Starter sound checkbox functionality
        //Checkbox 1
        coloradoChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(coloradoChecked.isChecked()) {
                    coloradoRender.setVisibility(View.VISIBLE);
                    coloradoChecked.setEnabled(false);
                    omegaChecked.setEnabled(true);
                    daktronicsChecked.setEnabled(true);

                    daktronicsChecked.setChecked(false);
                    omegaChecked.setChecked(false);


                    inData.setText("1");
                        SM.sendData(inData.getText().toString().trim());
                        debugOutput.setText(inData.getText().toString());
                        //debugOutput.setText(soundMode);




                    Drawable drawable = drawableFillC.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }

                else if(coloradoChecked.isChecked() == false) {
                    coloradoRender.setVisibility(View.GONE);
                    Drawable drawable = drawableUnfillC.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }

                }

            }
        });


        //checkbox 2
        daktronicsChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(daktronicsChecked.isChecked()) {
                    daktronicsRender.setVisibility(View.VISIBLE);
                    daktronicsChecked.setEnabled(false);
                    omegaChecked.setEnabled(true);
                    coloradoChecked.setEnabled(true);

                    omegaChecked.setChecked(false);
                    coloradoChecked.setChecked(false);

                    inData.setText("2");
                    SM.sendData(inData.getText().toString().trim());
                    debugOutput.setText(inData.getText().toString());
                    //editText.setText(soundMode);
                    //debugOutput.setText(soundMode);




                    Drawable drawable = drawableFillD.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }
                else if(daktronicsChecked.isChecked() == false) {
                    daktronicsRender.setVisibility(View.GONE);

                    Drawable drawable = drawableUnfillD.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }

                }
            }
        });


        //checkbox 3
        omegaChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(omegaChecked.isChecked()) {
                    omegaRender.setVisibility(View.VISIBLE);
                    omegaChecked.setEnabled(false);
                    daktronicsChecked.setEnabled(true);
                    daktronicsChecked.setChecked(false);
                    coloradoChecked.setChecked(false);
                    coloradoChecked.setEnabled(true);

                    //soundMode = 3;
                    inData.setText("3");
                    SM.sendData(inData.getText().toString().trim());
                    debugOutput.setText(inData.getText().toString());
                    //editText.setText(soundMode);
                    //debugOutput.setText(soundMode);



                    Drawable drawable = drawableFillO.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }

                else if(omegaChecked.isChecked() == false) {
                    omegaRender.setVisibility(View.GONE);

                    Drawable drawable = drawableUnfillO.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }

                }

            }
        });


        //Theme Checkbox (function)

        lightChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(lightChecked.isChecked()) {
                    lightRender.setVisibility(View.VISIBLE);
                    lightChecked.setEnabled(false);
                    darkChecked.setEnabled(true);

                    darkChecked.setChecked(false);

                    Drawable drawable = drawableFillL.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }

                else if(lightChecked.isChecked() == false) {
                    lightRender.setVisibility(View.GONE);
                    Drawable drawable = drawableUnfillL.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }
            }
        });

        darkChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(darkChecked.isChecked()) {
                    darkRender.setVisibility(View.VISIBLE);
                    darkChecked.setEnabled(false);
                    lightChecked.setEnabled(true);

                    lightChecked.setChecked(false);

                    Drawable drawable = drawableFillDa.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }

                else if(darkChecked.isChecked() == false) {
                    darkRender.setVisibility(View.GONE);
                    Drawable drawable = drawableUnfillDa.getCurrent();

                    if (drawable instanceof Animatable) {
                        ((Animatable) drawable).start();
                    }
                }
            }
        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnPassData = (Button) view.findViewById(R.id.btnPassData);
        inData = (EditText) view.findViewById(R.id.inMessage);
        debugOutput = (TextView) view.findViewById(R.id.debugOutput);

        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SM.sendData(inData.getText().toString().trim());
                debugOutput.setText(inData.getText().toString());

            }
        });


    }

    public interface SendMessage {
        void sendData(String message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM = (SendMessage) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}




