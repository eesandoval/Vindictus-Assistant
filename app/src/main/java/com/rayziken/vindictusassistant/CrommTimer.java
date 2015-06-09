package com.rayziken.vindictusassistant;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class CrommTimer extends Fragment {
    View rootview;
    TextView raidTimeTextView;
    TextView statueTimeTextView;
    Button startTimerButton;
    Button stopTimerButton;
    Button newTimeButton;
    CheckBox underSixBarsCheckBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.cromm_timer_title));
        rootview = inflater.inflate(R.layout.cromm_timer, container, false);

        startTimerButton = (Button)rootview.findViewById(R.id.startTimer);
        stopTimerButton = (Button)rootview.findViewById(R.id.stopTimer);
        raidTimeTextView = (TextView)rootview.findViewById(R.id.raidTime);
        raidTimeTextView.setText("60:00");

        newTimeButton = (Button)rootview.findViewById(R.id.newTime);
        statueTimeTextView = (TextView)rootview.findViewById(R.id.statueTime);
        underSixBarsCheckBox = (CheckBox)rootview.findViewById(R.id.underSix);

        final UpdateRaidTime raidTimer = new UpdateRaidTime(3600000, 1000);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raidTimer.start();
            }
        });
        stopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raidTimer.cancel();
            }
        });

        newTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long nextStatueTime = raidTimer.millisUntilDone;
                if (!underSixBarsCheckBox.isChecked()) {
                    nextStatueTime -= 30000L;
                }
                nextStatueTime -= 120000L;
                statueTimeTextView.setText((nextStatueTime / 60000L) + ":");
                long secondsLeft = ((nextStatueTime / 1000L) % 60L);
                if (secondsLeft < 10L)
                    statueTimeTextView.append("0");
                statueTimeTextView.append(secondsLeft + "");
            }
        });
        return rootview;
    }

    public class UpdateRaidTime extends CountDownTimer {
        long millisUntilDone = 3600000L;
        public UpdateRaidTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            raidTimeTextView.setText("60:00");
            millisUntilDone = 3600000L;
        }

        public void onTick(long millisUntilFinished) {
            raidTimeTextView.setText((millisUntilFinished / 60000L) + ":");
            long secondsLeft = ((millisUntilFinished)/1000L) % 60L;
            if (secondsLeft < 10L)
                raidTimeTextView.append("0");
            raidTimeTextView.append(secondsLeft + "");
        }
    }
}