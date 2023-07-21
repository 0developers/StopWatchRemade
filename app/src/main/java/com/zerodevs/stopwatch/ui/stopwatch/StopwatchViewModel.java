package com.zerodevs.stopwatch.ui.stopwatch;

import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class StopwatchViewModel extends ViewModel {

    public MutableLiveData<String> timertxt = new MutableLiveData<>("00:00:00");
    public int hours, minutes, sec, secs;
    private Boolean start;
    Boolean hasStarted = false;
    public MutableLiveData<Integer> state = new MutableLiveData<>(0);
    /*
    state :
    0 => never started the stopwatch
    1 => stopwatch is running
    2 => stopwatch is paused
    these states are for updating the views in StopwatchFragment.
    By Zero Developer's
     */

    public void startStopWatch(Boolean started) {
        start = started;
        // if the boolean was false , set state to 2 (paused)
        // note : the stopwatch set started state code is inside of the timer
        if (!started) state.postValue(2);
        if (!hasStarted) {
            startTheTimer();
            // this Boolean will prevent starting the timer over and over again
            hasStarted = true;
        }

    }

    public void resetTimer() {
        // this function will reset the timer
        start = false;
        secs = 0;
        state.postValue(0);
        timertxt.postValue("00:00:00");

    }

    public void startTheTimer() {

        Log.d("StopwatchViewModel", "timer started : " + start);

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

                                timertxt.postValue(time);
                                // set the stopwatch is running
                                state.postValue(1);
                            }
                        } catch (Exception e) {
                            Log.d("StopwatchViewModel", "timer error : " + e.toString());
                        }
                    }

                });
            }

        };
        timer.schedule(TK, 1000, 1000);

    }
}




