package com.game.gameboost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SupportFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_support, container, false);

        ImageView imageViewFb = rootView.findViewById(R.id.fb);
        ImageView imageViewZalo = rootView.findViewById(R.id.zalo);
        ImageView imageViewTele = rootView.findViewById(R.id.tele);
        ImageView imageViewMsg = rootView.findViewById(R.id.msg);

        imageViewFb.setOnClickListener(v -> openWebsite("https://www.facebook.com"));
        imageViewZalo.setOnClickListener(v -> openWebsite("https://www.zalo.com"));
        imageViewTele.setOnClickListener(v -> openWebsite("https://www.telegram.org"));
        imageViewMsg.setOnClickListener(v -> openWebsite("https://www.messenger.com"));

        return rootView;
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}


