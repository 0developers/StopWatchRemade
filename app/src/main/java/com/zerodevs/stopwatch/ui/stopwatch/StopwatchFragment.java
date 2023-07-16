package com.zerodevs.stopwatch.ui.stopwatch;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.zerodevs.stopwatch.databinding.FragmentStopwatchBinding;

public class StopwatchFragment extends Fragment {

    private FragmentStopwatchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StopwatchViewModel stopwatchViewModel =
                new ViewModelProvider(this).get(StopwatchViewModel.class);

            binding = FragmentStopwatchBinding.inflate(inflater, container, false);
            View root = binding.getRoot();
            final TextView timer = binding.timer;
            final TextView startbtn = binding.startbtn;
            stopwatchViewModel.getTime().observe(getViewLifecycleOwner(), timer::setText);
            stopwatchViewModel.getStartBtnValue().observe(getViewLifecycleOwner(), startbtn::setText);
            Boolean started = false;

            // on click listener

            startbtn.setOnClickListener(view -> {


                if (started) {
                    startbtn.setBackgroundColor(Color.parseColor("#FF5722"));
                    startbtn.setText("Stop");
                } else {
                    stopwatchViewModel.startStopWatch(false);
                    startbtn.setText("Start");
                    startbtn.setBackgroundColor(Color.parseColor("#00C853"));
                }

            });

            return root;

        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}