package com.zerodevs.stopwatch.ui.stopwatch;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class StopwatchViewModel extends ViewModel {

    private final MutableLiveData<String> start;
    private final MutableLiveData<String> timertxt;
    private final MutableLiveData<String> state;
    public int hours , minutes , sec , secs;

    public StopwatchViewModel() {
        start = new MutableLiveData<>();
        state = new MutableLiveData<>();
        timertxt = new MutableLiveData<>();
        start.setValue("Start");
        timertxt.setValue("00:00:00");
    }

    public LiveData<String> getTime() {
        return timertxt;
    }

    public LiveData<String> getStartBtnValue() {
        return start;
    }

    public LiveData<String> startStopWatch(Boolean start) {


        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask TK = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {

                    public void run() {

                        try {
                            if (start) {

                                secs++;
                                hours = (secs / 3600);
                                minutes = (secs % 3600) / 60;
                                sec = secs % 60;


                                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, sec);

                                timertxt.setValue(time);

                            }
                        } catch (Exception e) {

                        }
                    }

                });
            } };

                return timertxt;

    }

}


