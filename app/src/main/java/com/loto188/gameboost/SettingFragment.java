package com.loto188.gameboost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        ImageView imageViewEvaluate = rootView.findViewById(R.id.image_evaluate);
        ImageView imageViewIntroduce = rootView.findViewById(R.id.image_introduce);
        ImageView imageViewPolicy = rootView.findViewById(R.id.image_policy);
        ImageView imageViewSupport = rootView.findViewById(R.id.image_support);


        imageViewEvaluate.setOnClickListener(v -> {
            String websiteUrl = "https://www.google.com";
            openWebsite(websiteUrl);
        });

        imageViewIntroduce.setOnClickListener(v -> {
            String websiteUrl = "https://www.google.com";
            openWebsite(websiteUrl);
        });

        imageViewPolicy.setOnClickListener(v -> {
            String websiteUrl = "https://www.google.com";
            openWebsite(websiteUrl);
        });

        imageViewSupport.setOnClickListener(v -> {
            SupportFragment supportFragment = new SupportFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_container, supportFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return rootView;
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}