package com.zerodevs.stopwatch.ui.timer;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TimerViewModel extends ViewModel {

    public MutableLiveData<String> timertxt = new MutableLiveData<>("00:00:00");
    public MutableLiveData<Integer> state = new MutableLiveData<>(0);
    private long H , M ,S , MS;
    CountDownTimer count;
    NumberFormat NF;

    public void timer(Boolean start, long hours, long minute, long seconds) {


        String sH = String.valueOf(hours);
        String sM = String.valueOf(minute);
        String sS = String.valueOf(seconds);

        if (!sH.isEmpty()) H = Long.parseLong(sH);

        if (!sM.isEmpty()) M = Long.parseLong(sM);

        if (!sS.isEmpty()) S = Long.parseLong(sS);


        count = new CountDownTimer(convertToMilliseconds(H, M, S), 1000) {
            @Override
            public void onTick(long  millisUntilFinished) {
                state.postValue(1);
                NumberFormat NF = new DecimalFormat("00");
                long hours = ( millisUntilFinished / 360000000) % 24;
                long minutes = ( millisUntilFinished / 60000) % 60;
                long seconds = ( millisUntilFinished / 1000) % 60;
                MS = millisUntilFinished;
                timertxt.postValue(NF.format(hours) + ":" + NF.format(minutes) + ":" + NF.format(seconds));
            }

            @Override
            public void onFinish() {
                timertxt.postValue("00:00:00");
                state.postValue(0);
            }


        }.start();
    }

        public long convertToMilliseconds ( long Hours, long Minutes, long Seconds){

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
}



}

