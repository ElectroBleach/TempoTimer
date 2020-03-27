package com.bdatsko.tempotimer.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bdatsko.tempotimer.R;

public class home extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.homeml, container, false);

        Button coolButton = (Button) v.findViewById(R.id.coolButton);

        coolButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://trusting-saha-1efb42.netlify.com/portfolio/tempotimer/tempotimer.html"));
                startActivity(browserIntent);
            }
        });

        return v;

    }
}
