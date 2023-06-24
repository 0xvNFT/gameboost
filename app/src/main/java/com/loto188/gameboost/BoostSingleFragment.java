package com.loto188.gameboost;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class BoostSingleFragment extends Fragment {

    private static final long DELAY_MS = 2000;

    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_boost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView toastTextView = new TextView(requireContext());
        toastTextView.setText(R.string.boost_successful);
        toastTextView.setTextColor(getResources().getColor(android.R.color.white));
        toastTextView.setTextSize(18);
        toastTextView.setTypeface(null, Typeface.BOLD);
        toastTextView.setGravity(Gravity.CENTER);
        toastTextView.setPadding(30, 20, 30, 20);
        toastTextView.setBackgroundResource(R.drawable.grid_background);

        Toast toast = new Toast(requireContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastTextView);

        toast.setGravity(Gravity.TOP, 0, 300);

        toast.show();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        }, DELAY_MS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
