package com.loto188.gameboost;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

        installedGames.add(new Game("Nổ Hũ\n" +
                "Popular", ContextCompat.getDrawable(requireContext(), R.drawable.custom_item), null, "https://www.google.com/"));
        installedGames.add(new Game("Truy Cập Ngay\n" +
                "Popular", ContextCompat.getDrawable(requireContext(), R.drawable.custom_item), null, "https://www.google.com/"));
        installedGames.add(new Game("Slot\n" +
                "Popular", ContextCompat.getDrawable(requireContext(), R.drawable.custom_item), null, "https://www.google.com/"));
        installedGames.add(new Game("Xổ Số\n" +
                "Popular", ContextCompat.getDrawable(requireContext(), R.drawable.custom_item), null, "https://www.google.com/"));

        fetchInstalledGames();

        GameAdapter adapter = new GameAdapter(requireContext(), installedGames);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Game game = installedGames.get(position);
            if (game.isSpecificItem()) {
                openWebsiteForSpecificItem(game);
            } else {
                openGameDetailFragment(game);
            }
        });

        return rootView;
    }

    private void fetchInstalledGames() {
        PackageManager packageManager = requireActivity().getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<Game> installedGames = new ArrayList<>();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = applicationInfo.loadLabel(packageManager).toString();
                Drawable appIcon = applicationInfo.loadIcon(packageManager);
                String appPackage = applicationInfo.packageName;
                installedGames.add(new Game(appName, appIcon, appPackage));
            }
        }

        this.installedGames.addAll(installedGames);
    }


    private void openGameDetailFragment(Game game) {
        GameDetailFragment gameDetailFragment = GameDetailFragment.newInstance(game.getName(), game.getIcon(), game.getPackageName());
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, gameDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openWebsiteForSpecificItem(Game game) {
        String websiteUrl = game.getWebsiteUrl();
        openWebsite(websiteUrl);
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    static class Game {
        private final String name;
        private final Drawable icon;
        private final String packageName;

        private final boolean specificItem;
        private final String websiteUrl;

        Game(String name, Drawable icon, String packageName) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
            this.specificItem = false;
            this.websiteUrl = null;
        }

        Game(String name, Drawable icon, String packageName, String websiteUrl) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
            this.specificItem = true;
            this.websiteUrl = websiteUrl;
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

        boolean isSpecificItem() {
            return specificItem;
        }

        String getWebsiteUrl() {
            return websiteUrl;
        }
    }


}