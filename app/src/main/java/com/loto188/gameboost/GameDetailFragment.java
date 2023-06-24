package com.loto188.gameboost;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class GameDetailFragment extends Fragment {

    private static final String ARG_GAME_NAME = "arg_game_name";
    private static final String ARG_GAME_ICON = "arg_game_icon";
    private static final String ARG_GAME_PACKAGE_NAME = "arg_game_package_name";


    private String gameName;
    private Bitmap gameIcon;

    public GameDetailFragment() {
    }

    public static GameDetailFragment newInstance(String gameName, Drawable gameIcon, String packageName) {
        GameDetailFragment fragment = new GameDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GAME_NAME, gameName);
        args.putParcelable(ARG_GAME_ICON, drawableToBitmap(gameIcon));
        args.putString(ARG_GAME_PACKAGE_NAME, packageName);
        fragment.setArguments(args);
        return fragment;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gameName = getArguments().getString(ARG_GAME_NAME);
            gameIcon = getArguments().getParcelable(ARG_GAME_ICON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_detail_fragment, container, false);

        ImageView gameIconImageView = rootView.findViewById(R.id.game_icon);
        TextView gameNameTextView = rootView.findViewById(R.id.game_name);
        Button boostButton = rootView.findViewById(R.id.boost_button);
        Button launchButton = rootView.findViewById(R.id.launch_button);
        gameIconImageView.setImageBitmap(gameIcon);
        gameNameTextView.setText(gameName);

        boostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBoostFragment();
            }
        });
        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchApp();
            }
        });

        return rootView;
    }

    private void openBoostFragment() {
        BoostSingleFragment boostSingleFragment = new BoostSingleFragment();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_up, R.anim.slide_out_down);
        transaction.replace(R.id.main_container, boostSingleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void launchApp() {
        if (getArguments() != null) {
            String packageName = getArguments().getString(ARG_GAME_PACKAGE_NAME);
            if (packageName != null) {
                PackageManager packageManager = requireActivity().getPackageManager();
                Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView gameNameTextView = view.findViewById(R.id.game_name);

        gameNameTextView.setText(gameName);
    }
}


