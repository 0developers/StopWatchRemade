package com.zerodevs.stopwatch.ui.stopwatch;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zerodevs.stopwatch.databinding.FragmentStopwatchBinding;

public class StopwatchFragment extends Fragment {

    public Boolean started = false;
    private FragmentStopwatchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StopwatchViewModel stopwatchViewModel =
                new ViewModelProvider(this).get(StopwatchViewModel.class);
        binding = FragmentStopwatchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.d("StopwatchFragment", "Fragment started");
        final Button startbtn = binding.startbtn;

        try {
            stopwatchViewModel.timertxt.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    // update the timer text view
                    if (stopwatchViewModel.timertxt.getValue() == null) return;
                    binding.timer.setText(stopwatchViewModel.timertxt.getValue());
                }
            });

            stopwatchViewModel.state.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    //update the views ...
                    if (stopwatchViewModel.state.getValue() == null) return;
                    if (stopwatchViewModel.state.getValue() == 0) {

                        binding.startbtn.setText("Start");
                        binding.startbtn.setBackgroundColor(Color.parseColor("#00C853"));
                        binding.resetbtn.setVisibility(View.GONE);
                        started = false;
                    } else if (stopwatchViewModel.state.getValue() == 1) {

                        started = true;
                        binding.startbtn.setBackgroundColor(Color.parseColor("#FF5722"));
                        binding.startbtn.setText("Stop");
                        binding.resetbtn.setVisibility(View.GONE);
                    }
                    if (stopwatchViewModel.state.getValue() == 2) {
                        binding.startbtn.setText("Start");
                        binding.startbtn.setBackgroundColor(Color.parseColor("#00C853"));
                        binding.resetbtn.setVisibility(View.VISIBLE);
                        started = false;
                    }
                }
            });
        } catch (Exception e) {
            Log.e("StopwatchFragment", e.toString());
        }
        // on click listener's
        //start button
        startbtn.setOnClickListener(view -> {
            if (!started) {
                try {
                    stopwatchViewModel.startStopWatch(true);
                    Log.d("StopwatchFragment", "Starting timer...");
                } catch (Exception e) {

                    Log.e("StopwatchFragment", "error while starting the timer : " + e);
                }
            } else {
                try {
                    stopwatchViewModel.startStopWatch(false);
                    Log.d("StopwatchFragment", "timer stopped");
                } catch (Exception e) {

                    Log.e("StopwatchFragment", "error while stopping the timer : " + e);
                }
            }
        });
        binding.resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopwatchViewModel.resetTimer();
            }
        });


        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("StopwatchFragment", "Fragment Destroyed");
        binding = null;
    }
}