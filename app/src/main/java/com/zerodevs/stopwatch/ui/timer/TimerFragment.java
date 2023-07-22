package com.zerodevs.stopwatch.ui.timer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.zerodevs.stopwatch.databinding.FragmentTimerBinding;

public class TimerFragment extends Fragment {

    private FragmentTimerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimerViewModel timerViewModel =
                new ViewModelProvider(this).get(TimerViewModel.class);

        binding = FragmentTimerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        timerViewModel.timertxt.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (timerViewModel.timertxt.getValue() == null) return;
                binding.timer.setText(timerViewModel.timertxt.getValue());
            }
        });
        timerViewModel.state.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (timerViewModel.state.getValue() == null) return;
                if (timerViewModel.state.getValue() == 0) {
                    // never started
                    binding.inputHour.setVisibility(View.VISIBLE);
                    binding.inputMinute.setVisibility(View.VISIBLE);
                    binding.inputSecond.setVisibility(View.VISIBLE);
                    binding.starttimer.setText("Set timer");
                } else if (timerViewModel.state.getValue() == 1) {
                    //timer is running
                    binding.inputHour.setVisibility(View.INVISIBLE);
                    binding.inputMinute.setVisibility(View.INVISIBLE);
                    binding.inputSecond.setVisibility(View.INVISIBLE);
                    binding.starttimer.setText("Stop");
                } else if (timerViewModel.state.getValue() == 2) {
                    // timer is paused
                    binding.starttimer.setText("Start");
                }
            }
        });

        binding.starttimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long H = 0, M = 0, S = 0;
                if (timerViewModel.state.getValue() == null) return;
                if (timerViewModel.state.getValue() == 0) {

                    if (binding.inputHour.getText() != null) {
                        try {
                            H = Long.parseLong(String.valueOf(binding.inputHour.getText()));
                        } catch (Exception e) {
                        }
                    }
                    if (binding.inputMinute.getText() != null) {
                        try {
                            M = Long.parseLong(String.valueOf(binding.inputMinute.getText()));
                        } catch (Exception e) {
                        }
                    }
                    if (binding.inputHour.getText() != null) {
                        try {
                            S = Long.parseLong(String.valueOf(binding.inputSecond.getText()));
                        } catch (Exception e) {
                        }
                    }
                    timerViewModel.timer(true, H, M, S);
                } else if (timerViewModel.state.getValue() == 1) {
                    timerViewModel.pause();
                }
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