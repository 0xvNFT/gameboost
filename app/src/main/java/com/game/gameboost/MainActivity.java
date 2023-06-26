package com.game.gameboost;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_game) {
                loadFragment(new GameFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_boost) {
                loadFragment(new BoostFragment());
                return true;
            } else if (item.getItemId() == R.id.navigation_setting) {
                loadFragment(new SettingFragment());
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_game);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }
}
