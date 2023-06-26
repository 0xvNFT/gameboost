package com.game.gameboost;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BoostFragment extends Fragment {

    private ImageView rocketImageView;
    private ImageView ellipse1ImageView;
    private ImageView ellipse2ImageView;
    private ImageView smokeImageView;
    private ImageView pulseImageView;
    private ImageView waveImageView;

    private View rootView;

    public BoostFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_boost, container, false);

        rocketImageView = rootView.findViewById(R.id.image_rocket);
        ellipse1ImageView = rootView.findViewById(R.id.Ellipse1);
        ellipse2ImageView = rootView.findViewById(R.id.Ellipse2);
        pulseImageView = rootView.findViewById(R.id.pulse_rad);
        smokeImageView = rootView.findViewById(R.id.smoke);
        waveImageView = rootView.findViewById(R.id.wave);
        rootView.findViewById(R.id.boosting);

        rocketImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimations();
            }
        });

        return rootView;
    }

    private void startAnimations() {
        animateRocket();
        animateBlinking();
        animateSmoke();
        animateTextView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                proceedToBoostAllFragment();
            }
        }, 2000);
    }

    private void animateTextView() {
        TextView boostingTextView = rootView.findViewById(R.id.boosting);
        boostingTextView.setText(R.string.boosting);
        fadeOutView(boostingTextView, 1500); // Fade out the TextView with a duration of 1500 milliseconds
    }

    private void animateRocket() {
        ObjectAnimator rocketAnimator = ObjectAnimator.ofFloat(rocketImageView, "translationY", 0f, -1000f);
        rocketAnimator.setDuration(700);
        rocketAnimator.setInterpolator(new LinearInterpolator());
        rocketAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rocketImageView.setVisibility(View.INVISIBLE);
            }
        });
        rocketAnimator.start();
    }

    private void fadeOutView(final View view, long duration) {
        Animation fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setDuration(duration);
        fadeOutAnimation.setFillAfter(true);

        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        view.startAnimation(fadeOutAnimation);
    }


    private void proceedToBoostAllFragment() {
        if (isAdded()) {
            BoostAllFragment boostAllFragment = new BoostAllFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.main_container, boostAllFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void animateBlinking() {
        Animation fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setDuration(1000);

        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ellipse1ImageView.setVisibility(View.INVISIBLE);
                ellipse2ImageView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        ellipse1ImageView.startAnimation(fadeOutAnimation);
        ellipse2ImageView.startAnimation(fadeOutAnimation);
    }

    private void animateSmoke() {
        Animation fadeOutAnimation = new AlphaAnimation(1, 0);
        fadeOutAnimation.setDuration(1000);
        fadeOutAnimation.setFillAfter(true);

        smokeImageView.startAnimation(fadeOutAnimation);
        fadeOutView(waveImageView, 1000);
        fadeOutView(pulseImageView, 1000);
    }

}
