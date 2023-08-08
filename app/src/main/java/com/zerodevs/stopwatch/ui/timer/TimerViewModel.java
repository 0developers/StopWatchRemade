package com.zerodevs.stopwatch.ui.timer;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TimerViewModel extends ViewModel {

    public MutableLiveData<String> timertxt = new MutableLiveData<>("00:00:00");
    public MutableLiveData<Integer> state = new MutableLiveData<>(0);
    public MutableLiveData<Integer> hour = new MutableLiveData<>(0);
    public MutableLiveData<Integer> min = new MutableLiveData<>(0);
    public MutableLiveData<Integer> sec = new MutableLiveData<>(0);
    CountDownTimer count;
    NumberFormat NF;
    private long H;
    private long M;
    private long S;

    public void startTimer(int hours, int minute, int seconds) {

         H = (long)hours;

         M = (long) minute;

         S = (long) seconds;

        Log.d("TimerViewModel", "startTimer method .... ");
        count = new CountDownTimer(convertToMilliseconds(H, M, S), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                state.postValue(1);
                NF = new DecimalFormat("00");
                long hours = (millisUntilFinished / 360000000) % 24;
                hour.postValue((int) hours);
                long minutes = (millisUntilFinished / 60000) % 60;
                min.postValue((int) minutes);
                long seconds = (millisUntilFinished / 1000) % 60;
                sec.postValue((int) seconds);
                timertxt.postValue(NF.format(hours) + ":" + NF.format(minutes) + ":" + NF.format(seconds));
            }

            @Override
            public void onFinish() {
                timertxt.postValue("00:00:00");
                Log.d("TimerViewModel", "Countdowntimer is finished.");
                /*
                what this method at the bottom will do is it just changes the state value to 3
                and then it will change back to 0 after 5 seconds .
                the TimerFragment class will get the state value and plays the alarm sound for 5
                 seconds .
                 */
                postAlarm();
            }
        }.start();
    }

    public long convertToMilliseconds(long Hours, long Minutes, long Seconds) {

        long H = 0;
        long M = 0;
        long S = 0;

        if (!(Hours == 0)) {
            H = Hours * 360000000;
        }


        if (!(Minutes == 0)) {
            M = Minutes * 60000;
        }


        if (!(Seconds == 0)) {
            S = Seconds * 1000;
        }

        return H + M + S;

    }

    public void pause() {
        count.cancel();
        count = null;
        state.postValue(2);
    }

    public void postAlarm() {
        state.postValue(3);
        Timer timer = new Timer();
        final Handler handler = new Handler();
        TimerTask TK = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {

                    public void run() {
                        state.postValue(0);
                    }

                });
            }

        };
        timer.schedule(TK, 5000);

    }




}

