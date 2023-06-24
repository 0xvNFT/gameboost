package com.loto188.gameboost;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {

    private List<Game> installedGames;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        GridView gridView = rootView.findViewById(R.id.grid_games);

        installedGames = new ArrayList<>();

        fetchInstalledGames();

        GameAdapter adapter = new GameAdapter(requireContext(), installedGames);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Game game = installedGames.get(position);
            openGameDetailFragment(game);
        });

        return rootView;
    }

    private void fetchInstalledGames() {
        PackageManager packageManager = requireActivity().getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : installedApplications) {
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = applicationInfo.loadLabel(packageManager).toString();
                Drawable appIcon = applicationInfo.loadIcon(packageManager);
                String appPackage = applicationInfo.packageName;
                installedGames.add(new Game(appName, appIcon, appPackage));
            }
        }
    }

    private void openGameDetailFragment(Game game) {
        GameDetailFragment gameDetailFragment = GameDetailFragment.newInstance(game.getName(), game.getIcon(), game.getPackageName());
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, gameDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    static class Game {
        private final String name;
        private final Drawable icon;
        private final String packageName;

        Game(String name, Drawable icon, String packageName) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
        }

        String getName() {
            return name;
        }

        Drawable getIcon() {
            return icon;
        }

        String getPackageName() {
            return packageName;
        }
    }

}